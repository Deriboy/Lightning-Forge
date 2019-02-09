package deriktj.lightning_forge.common.block;

import deriktj.lightning_forge.common.block.base.BlockBaseSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;
        getGenerator().generate(world,rand,pos);


    }
}
