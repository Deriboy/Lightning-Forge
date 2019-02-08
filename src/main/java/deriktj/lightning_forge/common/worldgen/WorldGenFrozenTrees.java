package deriktj.lightning_forge.common.worldgen;

import deriktj.lightning_forge.common.block.BlockWorldTreeLog;
import deriktj.lightning_forge.common.block.ModBlocks;
import deriktj.lightning_forge.common.core.ModLightningForge;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenFrozenTrees extends WorldGenAbstractTree {

    private IBlockState blockStateWoodX = ModBlocks.frozen_log.getDefaultState().withProperty(BlockWorldTreeLog.LOG_AXIS,BlockWorldTreeLog.EnumAxis.X).withProperty(BlockWorldTreeLog.LOG_ALIVE, BlockWorldTreeLog.EnumAlive.ALIVE);
    private IBlockState blockStateWoodY = ModBlocks.frozen_log.getDefaultState().withProperty(BlockWorldTreeLog.LOG_AXIS,BlockWorldTreeLog.EnumAxis.Y).withProperty(BlockWorldTreeLog.LOG_ALIVE, BlockWorldTreeLog.EnumAlive.ALIVE);
    private IBlockState blockStateWoodZ = ModBlocks.frozen_log.getDefaultState().withProperty(BlockWorldTreeLog.LOG_AXIS,BlockWorldTreeLog.EnumAxis.Z).withProperty(BlockWorldTreeLog.LOG_ALIVE, BlockWorldTreeLog.EnumAlive.ALIVE);
    private IBlockState blockStateWood = ModBlocks.frozen_log.getDefaultState().withProperty(BlockWorldTreeLog.LOG_AXIS,BlockWorldTreeLog.EnumAxis.NONE).withProperty(BlockWorldTreeLog.LOG_ALIVE, BlockWorldTreeLog.EnumAlive.ALIVE);
    private IBlockState blockStateLeaves = ModBlocks.frozen_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    /** The minimum height of a generated tree. */
    private final int minSmallTreeHeight = 7;
    private final int minMediumTreeHeight = 4;
    private final int minBigTreeHeight = 4;

    private enum Variant {
        SMALL,
        //MEDIUM,
        //BIG
    }

    public WorldGenFrozenTrees(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        Variant variant = Variant.values()[rand.nextInt(Variant.values().length)];
        int height = 0;
        if(variant == Variant.SMALL) {
            height = rand.nextInt(3) + minSmallTreeHeight;
        }
        ModLightningForge.logger.info("TREE");
        ModLightningForge.logger.info(height);
        if(!canGrowHere(world,pos,variant,height)) {
            return false;
        }

        growTree(world,pos,variant,height);



        return true;
    }

    private void growTree(World world, BlockPos pos, Variant variant, int height ) {
        if(variant == Variant.SMALL) {
            for(int y = 0; y < height; y++) {
                //if(y >= height / 2) {
                    for(int x = -2; x <= 2; x++) {
                        for(int z = -2; z <= 2; z++) {
                            if(Math.abs(x) + Math.abs(z) <= (3 - Math.abs(height - y - 3))) {
                                this.setBlockAndNotifyAdequately(world, pos.add(x,y,z), blockStateLeaves);
                            }
                        }
                    }
                //}
                if(y < height - 3) {
                    //trunk
                    this.setBlockAndNotifyAdequately(world, pos.add(0,y,0), blockStateWoodY);
                }
                else if(y == height - 3) {
                    //trunk tip
                    this.setBlockAndNotifyAdequately(world, pos.add(0,y,0), blockStateWood);
                }
            }

        }
    }

    private boolean canGrowHere(World world, BlockPos pos, Variant variant, int height ) {
        if(variant == Variant.SMALL) {
            NonNullList<BlockPos> checkPositions = NonNullList.create();
            BlockPos.getAllInBox(pos,pos.add(0,height/2-1,0)).forEach(checkPositions::add);
            BlockPos.getAllInBox(pos.add(-2,height/2,-2),pos.add(2,(height-1),2)).forEach(checkPositions::add);
            checkPositions.remove(pos);

            for(BlockPos checkPos : checkPositions) {
                if(!isReplaceable(world,checkPos)) {
                    return false;
                }
            }
        }
        return true;
    }


}
