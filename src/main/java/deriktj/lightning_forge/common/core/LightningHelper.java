package deriktj.lightning_forge.common.core;

import deriktj.lightning_forge.common.block.ModBlocks;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightningHelper {

    public static void buildFulguriteStructure(World world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.getAllInBox(pos.getX()-1,pos.getY()-1,pos.getZ()-1,pos.getX()+1,pos.getY(),pos.getZ()+1)) {
            if((blockPos.equals(pos) || world.rand.nextDouble() < 0.25)){
                ModLightningForge.logger.info(world.getBlockState(pos).getPropertyKeys());
                if(world.getBlockState(blockPos).getProperties().get(BlockSand.VARIANT) == BlockSand.EnumType.SAND) {
                    world.setBlockState(blockPos, ModBlocks.white_fulgurite.getDefaultState());
                }
                else if(world.getBlockState(blockPos).getProperties().get(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) {
                    world.setBlockState(blockPos, ModBlocks.red_fulgurite.getDefaultState());
                }
            }
        }
    }

    public static void strikeAt(BlockPos strikePos,World world,boolean canRedirect) {
        int x = strikePos.getX();
        int y = strikePos.getY();
        int z = strikePos.getZ();

        EntityLightningBolt bolt = new EntityLightningBolt(world,x,y,z,false);

        NBTTagCompound data = bolt.getEntityData();
        NBTTagCompound lftag = new NBTTagCompound();
        lftag.setBoolean("canredirect",canRedirect);
        data.setTag("lf",lftag);
        world.addWeatherEffect(bolt);
    }
}
