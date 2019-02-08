package deriktj.lightning_forge.common.block;

import deriktj.lightning_forge.common.core.ModLightningForge;
import deriktj.lightning_forge.common.tile.TileLightningForge;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;


public class BlockLightningForge extends FacingBlock implements ITileEntityProvider {

    private String name = "lightning_forge";

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileLightningForge tile = (TileLightningForge) worldIn.getTileEntity(pos);
        IItemHandler tileItems = tile.getInventory(null);

        for(int i = 0; i < tileItems.getSlots(); i++) {
            ItemStack itemStack = tileItems.getStackInSlot(i);
            if(!itemStack.isEmpty()) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            }
        }

        worldIn.updateComparatorOutputLevel(pos, state.getBlock());
    }

    public BlockLightningForge() {
        super(Material.ROCK);
        setRegistryName(name);
        setUnlocalizedName(ModLightningForge.MODID + "." + name);
        setHardness(1);
        setLightLevel(3 / 16f);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean onBlockActivated(World worldIn,
                                    BlockPos pos,
                                    IBlockState state,
                                    EntityPlayer playerIn,
                                    EnumHand hand,
                                    EnumFacing facing,
                                    float hitX,
                                    float hitY,
                                    float hitZ) {
        super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        if(worldIn.isRemote) { return true; }
        if(facing != EnumFacing.UP) { return false; }
        int corner = getCorner(hitX, hitZ,state);
        if (corner < 0) {
            return false;
        }
        ItemStack handItemStack = playerIn.getHeldItem(hand);
        TileLightningForge tile = (TileLightningForge) worldIn.getTileEntity(pos);
        IItemHandler tileItems = tile.getInventory(null);
        if(!tileItems.getStackInSlot(corner).isEmpty()) {
            ItemStack item = tileItems.extractItem(corner, 1,false);
            if(!playerIn.addItemStackToInventory(item)) {
                EntityItem worldItem = new EntityItem(worldIn,playerIn.posX,playerIn.posY,playerIn.posZ,item);
                worldItem.motionX = 0;
                worldItem.motionY = 0;
                worldItem.motionZ = 0;
                worldIn.spawnEntity(worldItem);
            }
        }
        else {
            tileItems.insertItem(corner,handItemStack.splitStack(1),false);
        }
        return true;
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        if(worldIn.isRemote) { return; }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        TileLightningForge te = new TileLightningForge(worldIn);
        return te;
    }

    private int getCorner(float hitX, float hitZ, IBlockState state) {
        int corner;
        if(hitX <= 5/16f && hitZ >= 11/16f) {
            corner = 3;
        }
        else if(hitX <= 5/16f&& hitZ <= 5/16f) {
            corner = 2;
        }
        else if(hitX >= 11/16f && hitZ <= 5/16f) {
            corner = 1;
        }
        else if(hitX >= 11/16f && hitZ >= 11/16f) {
            corner = 0;
        }
        else {
            return -1;
        }

        EnumFacing face = state.getValue(FACING);
        int index = face.getHorizontalIndex();
        corner = ((corner - index % 4) + 4) % 4;
        return corner;
    }


}
