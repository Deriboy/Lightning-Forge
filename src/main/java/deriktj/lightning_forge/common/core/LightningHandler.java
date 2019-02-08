package deriktj.lightning_forge.common.core;

import deriktj.lightning_forge.common.tile.TileLightningForge;
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
            LightningHelper.buildFulguriteStructure(world,groundPos);
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


}
