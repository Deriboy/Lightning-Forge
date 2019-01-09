package deriktj.lightning_forge.common.core;

import deriktj.lightning_forge.common.block.ModBlocks;
import deriktj.lightning_forge.common.ModLightningForge;
import deriktj.lightning_forge.common.block.BlockLightningForge;
import deriktj.lightning_forge.common.item.ItemAncientPickaxe;
import deriktj.lightning_forge.common.item.ItemArtifactPiece;
import deriktj.lightning_forge.common.recipe.ModRecipes;
import deriktj.lightning_forge.common.tile.TileLightningForge;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), ModLightningForge.CONFIGNAME+".cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent e) {
        ModRecipes.init();
        TileLightningForge.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockLightningForge());
        GameRegistry.registerTileEntity(TileLightningForge.class,new ResourceLocation(ModLightningForge.MODID,"lightning_forge"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.lightning_forge).setRegistryName(ModBlocks.lightning_forge.getRegistryName()));
        event.getRegistry().register(new ItemAncientPickaxe());
        event.getRegistry().register(new ItemArtifactPiece());
    }
}