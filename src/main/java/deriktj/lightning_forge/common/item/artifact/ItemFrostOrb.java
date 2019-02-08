package deriktj.lightning_forge.common.item.artifact;

import deriktj.lightning_forge.common.core.ModLightningForge;
import deriktj.lightning_forge.common.core.NBTHelper;
import deriktj.lightning_forge.common.item.ItemBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.ArrayList;

public class ItemFrostOrb extends ItemBase {

    private static final String TAG_CHARGE = "charge";
    private static final int MAX_CHARGE = 4000;
    private static final int CHARGE_ON_USE = 20;

    public ItemFrostOrb() {
        super("frost_orb", CreativeTabs.MISC);
        setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!((EntityPlayer) entityIn).isHandActive() || !isSelected) {
            float temp = worldIn.getBiome(entityIn.getPosition()).getTemperature(entityIn.getPosition());
            ModLightningForge.logger.info("temp: " + temp);
            int change = 1;
            if(temp > 1.0f) {
                change = 0;
            }
            else if (temp < 0.4f) {
                change = 2;
            }

            int curCharge = NBTHelper.getInteger(stack,TAG_CHARGE,0);
            int newCharge = Math.min(curCharge + change,MAX_CHARGE);
            NBTHelper.setInteger(stack,TAG_CHARGE,newCharge);
        }
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack == newStack;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        NBTHelper.setInteger(stack,TAG_CHARGE,MAX_CHARGE);
        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        player.setActiveHand(hand);
        ItemStack stack = player.getHeldItem(hand);
        return ActionResult.newResult(EnumActionResult.PASS, stack);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format(getUnlocalizedName() + ".charge.tooltip") + ": " + String.valueOf(getCharge(stack)));
    }

    private int getCharge(ItemStack stack) {
        return NBTHelper.getInteger(stack,TAG_CHARGE,0);
    }

    private boolean useCharge(ItemStack stack) {
        int curCharge = NBTHelper.getInteger(stack,TAG_CHARGE,0);
        int newCharge = curCharge - CHARGE_ON_USE;
        if(newCharge >= 0) {
            NBTHelper.setInteger(stack,TAG_CHARGE,newCharge);
            return true;
        }
        return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 60; //ticks - 3 seconds
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.PASS;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return newStack.getItem() != this;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        activate(stack,entityLiving,getMaxItemUseDuration(stack)-timeLeft);
        super.onPlayerStoppedUsing(stack,worldIn,entityLiving,timeLeft);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        activate(stack,entityLiving,getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
        if(!living.world.isRemote) {
            if(getCharge(stack) < CHARGE_ON_USE && getMaxItemUseDuration(stack) - count > 20) {
                living.stopActiveHand();
            }
        }
    }

    private void activate(ItemStack stack, EntityLivingBase entity, int power) {
        World world = entity.world;

        if(!world.isRemote) {
            if(power < 10 || !useCharge(stack)) {
                return;
            }
            ModLightningForge.logger.info("power: " + power);
            int range = 2 + (6 * power)/getMaxItemUseDuration(stack);
            boolean anythingExtinguished = putOutFires(world,stack,entity,range);
            anythingExtinguished |= extinguishEntities(world,stack,entity,range);
            if(anythingExtinguished) {
                world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1f, 5f);
            }

            world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, SoundCategory.PLAYERS, power / getMaxItemUseDuration(stack) + 0.15f, 5f);
            spawnParticles(world,entity);
        }
    }

    private boolean extinguishEntities(World world, ItemStack stack, EntityLivingBase entity, int range) {
        BlockPos pos = entity.getPosition();
        List<EntityLiving> burningEntities = world.getEntitiesWithinAABB(EntityLiving.class,new AxisAlignedBB(pos.add(-range,-range,-range),pos.add(range,range,range)), e -> e.isBurning());
        boolean anythingExtinguished = false;
        for(EntityLiving burningEntity : burningEntities) {
            if(useCharge(stack)) {
                anythingExtinguished = true;
                burningEntity.extinguish();
                burningEntity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE));
            }
        }
        return anythingExtinguished;
    }

    private boolean putOutFires(World world,ItemStack stack,EntityLivingBase entity, int range) {
        BlockPos pos = entity.getPosition();
        ArrayList<BlockPos> fires = new ArrayList();
        boolean anythingExtinguished = false;
        for(BlockPos check : BlockPos.getAllInBox(pos.add(-range,-range,-range),pos.add(range,range,range))) {
            if(world.getBlockState(check).getBlock().equals(Blocks.FIRE)) {
                fires.add(check);
            }
        }
        for(BlockPos fire : fires) {
            if(useCharge(stack)) {
                anythingExtinguished = true;
                world.setBlockToAir(fire);
            }
        }
        return anythingExtinguished;
    }

    private void spawnParticles(World world, EntityLivingBase entity) {
        double step = Math.PI / 12.0;
        double scale = 4;
        for(double t = 0; t < 2 * Math.PI; t += step) {
            double vx = Math.cos(t) * scale;
            double vy = 5.0;
            double vz = Math.sin(t) * scale;
            ((WorldServer) world).spawnParticle(EnumParticleTypes.CRIT_MAGIC, entity.posX, entity.posY, entity.posZ,5, vx, vy, vz,0.0f);
        }
    }


}
