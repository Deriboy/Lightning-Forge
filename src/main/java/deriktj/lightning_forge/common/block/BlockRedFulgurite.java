package deriktj.lightning_forge.common.block;
import deriktj.lightning_forge.common.ModLightningForge;
import deriktj.lightning_forge.common.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockRedFulgurite extends Block{

    private String name = "red_fulgurite";

    public BlockRedFulgurite() {
        super(Material.GLASS);
        setRegistryName(name);
        setUnlocalizedName(ModLightningForge.MODID + "." + name);
        setHardness(1f);
        setLightLevel(0f);
        setSoundType(SoundType.GLASS);
        setCreativeTab(CreativeTabs.MATERIALS);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.fulgurite_shard;
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
    public int damageDropped(IBlockState state) {
        return 1;
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

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModLightningForge.logger.info(getRegistryName());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


}
