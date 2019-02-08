package deriktj.lightning_forge.common.block;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockFulgurite extends BlockBase{

    private Item drop;

    public BlockFulgurite(String name) {
        super(name,0.5f, 0,SoundType.GLASS,CreativeTabs.BUILDING_BLOCKS,Material.GLASS);

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return drop;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(4) + 1;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return Math.min(quantityDropped(random) + fortune,4);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        Block sideBlock = world.getBlockState(pos.offset(side)).getBlock();
        return sideBlock == this ? false : super.shouldSideBeRendered(state, world, pos, side);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

}
