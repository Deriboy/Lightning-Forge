package deriktj.lightning_forge.common.core;

import deriktj.lightning_forge.common.ModLightningForge;
import deriktj.lightning_forge.common.block.ModBlocks;
import deriktj.lightning_forge.common.tile.TileLightningForge;
import net.minecraft.block.BlockSand;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.LightningStrikeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LightningHandler {

    @SubscribeEvent
    public static void mainLightningStrikeEventHandler(LightningStrikeEvent event) {
        BlockPos pos = event.getEntity().getPosition();
        World world = event.getEntity().getEntityWorld();
        BlockPos groundPos = pos.add(0,-1,0);
        if(!world.isRemote && world.getBlockState(groundPos).getBlock() == Blocks.SAND) {
            buildFulguriteStructure(world,groundPos);
        }

        for (TileEntity t : event.getEntity().getEntityWorld().loadedTileEntityList){
            if (t instanceof TileLightningForge){
                ((TileLightningForge) t).lightningStrikeEventHandler(event);
            }
        }
    }

    @SubscribeEvent
    public static void mainLightningSpawnEventHandler(EntityJoinWorldEvent event) {
        for (TileEntity t : event.getEntity().getEntityWorld().loadedTileEntityList){
            if (t instanceof TileLightningForge){
                ((TileLightningForge) t).lightningSpawnEventHandler(event);
            }
        }
    }

    private static void buildFulguriteStructure(World world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.getAllInBox(pos.getX()-1,pos.getY()-1,pos.getZ()-1,pos.getX()+1,pos.getY(),pos.getZ()+1)) {
            if((blockPos.equals(pos) || world.rand.nextDouble() < 0.25)){
                ModLightningForge.logger.info(world.getBlockState(pos).getPropertyKeys());
                if(world.getBlockState(blockPos).getProperties().get(BlockSand.VARIANT) == BlockSand.EnumType.SAND) {
                    world.setBlockState(blockPos, ModBlocks.fulgurite.getDefaultState());
                }
                else if(world.getBlockState(blockPos).getProperties().get(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) {
                    world.setBlockState(blockPos, ModBlocks.red_fulgurite.getDefaultState());
                }
            }
        }
    }
}
