package deriktj.lightning_forge.common.block;

import deriktj.lightning_forge.common.worldgen.WorldGenFrozenTrees;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BlockWorldTreeSapling extends BlockBaseSapling {

    public BlockWorldTreeSapling(String name, float lightLevel) {
        super(name, lightLevel);
    }

    /*@Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return worldIn.getBiome(pos).isSnowyBiome(); //TODO: change me
    }*/

    /*@Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }*/

    @Override
    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldgenerator = new WorldGenFrozenTrees(true);


        worldgenerator.generate(worldIn, rand, pos);


    }
}
