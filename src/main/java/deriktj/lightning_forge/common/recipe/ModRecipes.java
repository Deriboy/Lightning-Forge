package deriktj.lightning_forge.common.recipe;

import deriktj.lightning_forge.api.LightningForgeAPI;
import deriktj.lightning_forge.api.recipe.RecipeLightningForge;
import deriktj.lightning_forge.common.item.ItemAncientPickaxe;
import deriktj.lightning_forge.common.item.ModItems;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModRecipes {

    public static RecipeLightningForge ancient_pickaxe_recipe;

    public static void init() {

        ancient_pickaxe_recipe = LightningForgeAPI.registerLightningForgeRecipe(ModItems.ancient_pickaxe.getItemStack(),
                60,
                ModItems.artifact_piece.getArtifactPieceStack("pickaxe"),
                new ItemStack(Items.DIAMOND),
                new ItemStack(Items.NETHER_STAR),
                new ItemStack(Items.STICK)
                );
    }
}
