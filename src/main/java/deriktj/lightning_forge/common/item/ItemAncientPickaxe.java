package deriktj.lightning_forge.common.item;

import deriktj.lightning_forge.api.LightningForgeAPI;
import deriktj.lightning_forge.common.ModLightningForge;
import deriktj.lightning_forge.common.core.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class ItemAncientPickaxe extends ItemPickaxe {

    private String name = "ancient_pickaxe";

    private int cooldown;
    private final int MAX_COOLDOWN = 100; //60 seconds in ticks - 1200 TODO: CONFIG!!!!!

    public ItemAncientPickaxe() {
        super(LightningForgeAPI.adamantineToolMaterial);
        setRegistryName(name);        // The unique name (within your mod) that identifies this item
        setUnlocalizedName(ModLightningForge.MODID + "." + name);     // Used for localization (en_US.lang)
        cooldown = 0;
        addPropertyOverride(new ResourceLocation(ModLightningForge.MODID, "cooldown"), (itemStack, world, entityLivingBase) -> cooldown > 0 ? 1 : 0);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {

        if (isInCreativeTab(tab))
        {
            items.add(getItemStack());
        }

    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(this);
        item.addEnchantment(Enchantments.EFFICIENCY,5);
        item.addEnchantment(Enchantments.FORTUNE,4);
        return item;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        if(player.world.isRemote || player.world.rand.nextInt(10) > 10 || cooldown > 0) { //TODO: CONFIG!!!
            return false;
        }
        Block hereBlock = player.world.getBlockState(pos).getBlock();
        if(hereBlock == Blocks.DIAMOND_ORE) { // Don't use effect if already mining an ore TODO: check for ore generally (CONFIG!!)
            return false;
        }
        List<BlockPos> list = new ArrayList<>();
        for(BlockPos checkPos : BlockPos.getAllInBox(pos.add(2,2,2),pos.add(-2,-2,-2))) { //TODO: CONFIG!!!!!!!!
            if(!checkPos.equals(pos)) {
                if(player.world.isBlockLoaded(checkPos));
                Block block = player.world.getBlockState(checkPos).getBlock();
                if(block == Blocks.DIAMOND_ORE) { //TODO: check for ore generally (CONFIG!!)
                    list.add(checkPos);
                }
            }
        }
        for(int x = -2; x <= 2; x++) { //TODO: CONFIGGGGGGGGGGGGGGGGGG
            for(int y = -2; y <= 2; y++) {
                for(int z = -2; z <= 2; z++) {

                }
            }
        }
        if(list.size() > 0) {
            BlockPos orePos = list.get(player.world.rand.nextInt(list.size()));
            IBlockState oreBlockState = player.world.getBlockState(orePos);
            IBlockState hereBlockState = player.world.getBlockState(pos);

            player.world.setBlockState(orePos,hereBlockState);
            player.world.setBlockToAir(pos);

            oreBlockState.getBlock().harvestBlock(player.world,player,pos,oreBlockState,player.world.getTileEntity(pos),itemstack);
            ModLightningForge.logger.info("cooldown activated");
            NBTHelper.setBoolean(itemstack, "cooldown", true);
            player.world.playSound(null, player.posX,player.posY,player.posZ, SoundEvents.BLOCK_NOTE_XYLOPHONE, SoundCategory.PLAYERS,0.8f,0.8f + player.world.rand.nextFloat()/2);
            cooldown = MAX_COOLDOWN;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if(!worldIn.isRemote && cooldown > 0) {
            ModLightningForge.logger.info("cooldown active " + cooldown);
            cooldown--;
            if(cooldown == 0) {
                ModLightningForge.logger.info("cooldown not active");
                NBTHelper.setBoolean(stack,"cooldown",false);
            }
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return newStack.getItem() != this;
    }
}
