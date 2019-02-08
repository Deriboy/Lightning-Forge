package deriktj.lightning_forge.client.core;

import deriktj.lightning_forge.common.block.BlockBaseLeaves;
import deriktj.lightning_forge.common.block.ModBlocks;
import deriktj.lightning_forge.client.blocks.render.LightningForgeFastTESR;
import deriktj.lightning_forge.common.core.CommonProxy;
import deriktj.lightning_forge.common.entity.EntityBottledLightning;
import deriktj.lightning_forge.common.entity.ModEntities;
import deriktj.lightning_forge.common.item.ModItems;
import deriktj.lightning_forge.common.tile.TileLightningForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
        ModItems.initModels();
        ModEntities.initModels();
        ClientRegistry.bindTileEntitySpecialRenderer(TileLightningForge.class, LightningForgeFastTESR.SINGLETON);
    }

    public void setGraphicsLevel(BlockBaseLeaves blockBaseLeaves, boolean b) {
        blockBaseLeaves.setGraphicsLevel(b);
    }

}