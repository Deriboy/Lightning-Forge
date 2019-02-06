package deriktj.lightning_forge.common.tile;

import deriktj.lightning_forge.api.LightningForgeAPI;
import deriktj.lightning_forge.api.recipe.RecipeLightningForge;
import deriktj.lightning_forge.common.ModLightningForge;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.LightningStrikeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class TileLightningForge extends TileEntity {

    // static stuff begins


    public static final int SIZE = 4;

    public static void init() {
        LightningForgeAPI.registerValidRodBlock(Blocks.IRON_BARS,5);

        LightningForgeAPI.registerValidRodBaseBlock(Blocks.IRON_BARS,5);
        LightningForgeAPI.registerValidRodBaseBlock(Blocks.END_ROD,45);
    }

    // static stuff ends

    private RecipeLightningForge currentRecipe;

    private ItemStackHandler inputsItemHandler = new ItemStackHandler(SIZE) {

        @Override
        protected void onContentsChanged(int slot) {

            currentRecipe = LightningForgeAPI.getMatchingRecipe(inputsItemHandler);

            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            sendUpdates();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }


    };

    public TileLightningForge(World world){
        super();
        setWorld(world);
        currentRecipe = LightningForgeAPI.getMatchingRecipe(inputsItemHandler);
    }
    public TileLightningForge(){
        super();
        currentRecipe = LightningForgeAPI.getMatchingRecipe(inputsItemHandler);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("item")) {
            inputsItemHandler.deserializeNBT((NBTTagCompound) compound.getTag("item"));
        }
        currentRecipe = LightningForgeAPI.getMatchingRecipe(inputsItemHandler);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("item", inputsItemHandler.serializeNBT());
        return compound;
    }


    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputsItemHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 0, this.writeToNBT(new NBTTagCompound()));
    }


    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.setPos(pkt.getPos());
        this.readFromNBT(pkt.getNbtCompound());
    }

    public IItemHandler getInventory(EnumFacing facing) {
        IItemHandler itemHandler = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,facing);
        return itemHandler;
    }
    private void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(this.pos), world.getBlockState(this.pos), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }

    public RecipeLightningForge getCurrentRecipe() {
        return currentRecipe;
    }

    public void lightningStrikeEventHandler(LightningStrikeEvent event) {
        if(event.getEntity().getEntityWorld().isRemote) {
            return;
        }
        ModLightningForge.logger.info("STRIKE " + event.getEntity() + " at " + event.getEntity().getPosition());
        ModLightningForge.logger.info("STRIKE triggered by " + this.getPos());

        BlockPos strikePos = event.getEntity().getPosition();
        if(strikePos.equals(getPos().add(0,getRodHeight()+1,0))) {
            if(currentRecipe != null && currentRecipe.validRecipe(getInventory(null)) && currentRecipe.validRod(getRodQuality()) && event.getEntity().getEntityWorld().isThundering()) {
                craft();
            }
        }
    }

    public void lightningSpawnEventHandler(EntityJoinWorldEvent event) {
        if(event.getWorld().isRemote) {
            return;
        }
        Entity entity = event.getEntity();
        if(!(entity instanceof EntityLightningBolt)){
            return;
        }
        getRodQuality();
        int rodHeight = getRodHeight();
        if(rodHeight == 0) {
            return;
        }

        BlockPos myPos = getPos().add(0,rodHeight+1,0);
        EntityLightningBolt bolt = (EntityLightningBolt) entity;
        NBTTagCompound data = bolt.getEntityData();
        if(!getWorld().canBlockSeeSky(myPos)) {
            return;
        }

        NBTTagCompound lftag;

        if(data.hasKey("lf")) {
            lftag = (NBTTagCompound) data.getTag("lf");
        }
        else {
            lftag = new NBTTagCompound();
            lftag.setInteger("origx",bolt.getPosition().getX());
            lftag.setInteger("origy",bolt.getPosition().getY()); // TODO: Fix this so it doesn't always steal natural spawns
            lftag.setInteger("origz",bolt.getPosition().getZ());

            data.setTag("lf",lftag);
            float max_horiz_dist = 100.0f;
            if(getPos().getDistance(bolt.getPosition().getX(),getPos().getY(),bolt.getPosition().getZ()) <= max_horiz_dist) {
                bolt.setPosition(myPos.getX(),myPos.getY(),myPos.getZ());
            }
            return;
        }

        if(lftag.hasKey("canredirect") && !lftag.getBoolean("canredirect")) {
            return;
        }

        BlockPos origPos = new BlockPos(lftag.getInteger("origx"),lftag.getInteger("origy"),lftag.getInteger("origz"));
        BlockPos boltPos = bolt.getPosition();


        double cur_score = score(origPos,boltPos);
        double prop_score = score(origPos,myPos);

        if(prop_score < cur_score) {
            bolt.setPosition(myPos.getX(),myPos.getY(),myPos.getZ());
        }
    }

    /**
     *
     * @return the height of the rod above the lightning forge
     */
    private int getRodHeight() {
        int y = 0;
        while(true) {
            BlockPos toCheck = getPos().add(0,y + 1,0);
            if(toCheck.getY() > 255) {
                break;
            }
            Block block = getWorld().getBlockState(toCheck).getBlock();
            if(y == 0 && LightningForgeAPI.validRodBaseBlock(block)) {
                y++;
                continue;
            }
            else if(LightningForgeAPI.validRodBlock(block)) {
                y++;
                continue;
            }
            break;
        }
        ModLightningForge.logger.info("Rod Height: " + y);
        return y;
    }

    /**
     *
     * @return
     */
    private float getRodQuality() {
        float rodHeight = getRodHeight();
        float sum = 0;
        for(int y = 0; y < rodHeight; y++) {
            BlockPos toCheck = getPos().add(0,y+1,0);
            Block block = getWorld().getBlockState(toCheck).getBlock();
            if(y == 0) {
                sum += LightningForgeAPI.baseRodQualityOf(block);
            }
            else {
                sum += LightningForgeAPI.rodQualityOf(block) * (1 - Math.tanh(y/3 - 2))/2;
            }
        }
        ModLightningForge.logger.info("Rod Quality: " + sum);
        return sum;
    }

    /**
     * This version of scoring simply determines the distance from the 'origin' point of the lightning
     *   (arbitrarily chosen to be 512 blocks above the point it strikes)
     *    and another point to be considered
     * @param boltPos original position of lightning to be scored
     * @return the score of the lightning bolt (lower scores rank better)
     */
    private static float score(BlockPos boltPos,BlockPos candidatePos) {
        return (float) candidatePos.getDistance(boltPos.getX(),512,boltPos.getZ());
    }

    private void craft() {
        ItemStack result = currentRecipe.getResult();
        for(int i = 0; i < SIZE; i++) {
            getInventory(null).extractItem(i,1,false);
        }
        Random random = getWorld().rand;
        getInventory(null).insertItem(random.nextInt(4),result,false);
    }
}
