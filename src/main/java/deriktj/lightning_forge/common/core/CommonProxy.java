package deriktj.lightning_forge.common.core;

import deriktj.lightning_forge.common.block.BlockBaseLeaves;
import deriktj.lightning_forge.common.block.BlockFulgurite;
import deriktj.lightning_forge.common.block.BlockLightningGlass;
import deriktj.lightning_forge.common.block.BlockWorldTreeLeaves;
import deriktj.lightning_forge.common.block.BlockWorldTreeLog;
import deriktj.lightning_forge.common.block.BlockWorldTreeSapling;
import deriktj.lightning_forge.common.block.ModBlocks;
import deriktj.lightning_forge.common.block.BlockLightningForge;
import deriktj.lightning_forge.common.entity.ModEntities;
import deriktj.lightning_forge.common.item.artifact.ItemAncientPickaxe;
import deriktj.lightning_forge.common.item.artifact.ItemFrostOrb;
import deriktj.lightning_forge.common.item.crafting.ItemArtifactPiece;
import deriktj.lightning_forge.common.item.ItemBottledLightning;
import deriktj.lightning_forge.common.item.crafting.ItemFulguriteShard;
import deriktj.lightning_forge.common.item.crafting.ItemLightningGlassPhial;
import deriktj.lightning_forge.common.item.crafting.ItemWorldTreeCore;
import deriktj.lightning_forge.common.item.ModItems;
import deriktj.lightning_forge.common.recipe.ModRecipes;
import deriktj.lightning_forge.common.tile.TileLightningForge;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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

@Mod.EventBusSubscriber(modid = ModLightningForge.MODID)
public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), ModLightningForge.CONFIGNAME+".cfg"));
        Config.readConfig();
        ModEntities.init();
    }

    public void init(FMLInitializationEvent e) {
        ModRecipes.init();
        TileLightningForge.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }

        ModBlocks.white_fulgurite.setDrop(ModItems.fulgurite_shard_white);
        ModBlocks.red_fulgurite.setDrop(ModItems.fulgurite_shard_red);
        ModBlocks.frozen_log.setDrop(ModItems.frozen_core);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockLightningForge());
        event.getRegistry().register(new BlockFulgurite("white_fulgurite"));
        event.getRegistry().register(new BlockFulgurite("red_fulgurite"));
        event.getRegistry().register(new BlockLightningGlass("white_lightning_glass"));
        event.getRegistry().register(new BlockLightningGlass("red_lightning_glass"));
        event.getRegistry().register(new BlockWorldTreeLog("frozen_log"));
        event.getRegistry().register(new BlockWorldTreeLeaves("frozen_leaves"));
        event.getRegistry().register(new BlockWorldTreeSapling("frozen_sapling",10));

        GameRegistry.registerTileEntity(TileLightningForge.class,new ResourceLocation(ModLightningForge.MODID,"lightning_forge"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.lightning_forge).setRegistryName(ModBlocks.lightning_forge.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.white_fulgurite).setRegistryName(ModBlocks.white_fulgurite.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.red_fulgurite).setRegistryName(ModBlocks.red_fulgurite.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.white_lightning_glass).setRegistryName(ModBlocks.white_lightning_glass.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.red_lightning_glass).setRegistryName(ModBlocks.red_lightning_glass.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.frozen_leaves).setRegistryName(ModBlocks.frozen_leaves.getRegistryName()));
        event.getRegistry().register(new ItemFulguriteShard("fulgurite_shard_white"));
        event.getRegistry().register(new ItemFulguriteShard("fulgurite_shard_red"));
        event.getRegistry().register(new ItemAncientPickaxe());
        event.getRegistry().register(new ItemArtifactPiece("artifact_piece_pickaxe"));
        event.getRegistry().register(new ItemArtifactPiece("artifact_piece_spear"));
        event.getRegistry().register(new ItemLightningGlassPhial());
        event.getRegistry().register(new ItemBottledLightning());
        event.getRegistry().register(new ItemWorldTreeCore("frozen_core"));
        event.getRegistry().register(new ItemFrostOrb());

        /**
         * Registrations that require weird stuff go here
         */

        event.getRegistry().register(new ItemBlock(ModBlocks.frozen_log){
            @Override
            public int getItemBurnTime(ItemStack itemStack) {
                return 300;
            }
        }.setRegistryName(ModBlocks.frozen_log.getRegistryName()));


        event.getRegistry().register(new ItemBlock(ModBlocks.frozen_sapling){
            @Override
            public int getItemBurnTime(ItemStack itemStack) {
                return 100;
            }
        }.setRegistryName(ModBlocks.frozen_sapling.getRegistryName()));

    }

    public void setGraphicsLevel(BlockBaseLeaves blockBaseLeaves, boolean b) {
        //do nothing because this is the server
    }
}