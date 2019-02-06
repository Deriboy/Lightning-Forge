package deriktj.lightning_forge.common.recipe;

import deriktj.lightning_forge.api.LightningForgeAPI;
import deriktj.lightning_forge.api.recipe.RecipeLightningForge;
import deriktj.lightning_forge.common.block.ModBlocks;
import deriktj.lightning_forge.common.item.ItemAncientPickaxe;
import deriktj.lightning_forge.common.item.ModItems;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModRecipes {

    public static RecipeLightningForge ancient_pickaxe_recipe;
    public static RecipeLightningForge bottled_lightning_recipe;

    public static void init() {

        ancient_pickaxe_recipe = LightningForgeAPI.registerLightningForgeRecipe(ModItems.ancient_pickaxe.getItemStack(),
                60,
                new ItemStack(ModItems.artifact_piece_pickaxe),
                new ItemStack(Items.DIAMOND),
                new ItemStack(Items.NETHER_STAR),
                new ItemStack(Items.STICK)
                );
        bottled_lightning_recipe = LightningForgeAPI.registerLightningForgeRecipe(new ItemStack(ModItems.bottled_lightning),
                15,
                new ItemStack(ModItems.lightning_glass_phial)
                );

        GameRegistry.addSmelting(new ItemStack(ModItems.fulgurite_shard_white), new ItemStack(ModBlocks.white_lightning_glass),0.1f );
        GameRegistry.addSmelting(new ItemStack(ModItems.fulgurite_shard_red), new ItemStack(ModBlocks.red_lightning_glass),0.1f );

    }
}
