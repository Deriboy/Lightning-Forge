package deriktj.lightning_forge.api;

import deriktj.lightning_forge.api.recipe.RecipeLightningForge;
import deriktj.lightning_forge.common.tile.TileLightningForge;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

public class LightningForgeAPI {

    //TODO: Refactor basically everything to use interfaces in the api

    public static List<RecipeLightningForge> lightningForgeRecipes = new ArrayList<>();

    public static Map<Block,Integer> valid_rod_blocks = new HashMap<>();
    public static Map<Block,Integer> valid_rod_base_blocks = new HashMap<>();

    public static final Item.ToolMaterial adamantineToolMaterial = EnumHelper.addToolMaterial("ADAMANTINE", 4, 3000, 9F, 4F, 0);

    /**
     * Registers a lightning forge recipe
     * @param result the item that results from the recipe
     * @param minimumRodQuality 0 or greater
     * @param inputs up to four items (order doesn't matter)
     * @return the created recipe
     */
    public static RecipeLightningForge registerLightningForgeRecipe(ItemStack result,int minimumRodQuality, ItemStack... inputs) {
        RecipeLightningForge forgeRecipe = new RecipeLightningForge(result,minimumRodQuality,inputs);
        LightningForgeAPI.lightningForgeRecipes.add(forgeRecipe);
        return forgeRecipe;
    }

    /**
     *
     * @param inv
     * @return
     */
    public static RecipeLightningForge getMatchingRecipe(IItemHandler inv) {
        for(RecipeLightningForge recipe : LightningForgeAPI.lightningForgeRecipes) {
            if(recipe.validRecipe(inv)) {
                return recipe;
            }
        }
        return null;
    }

    /**
     *
     * @param block
     * @return
     */
    public static boolean registerValidRodBlock(Block block,int quality) {
        if(!block.equals(Blocks.AIR) && !valid_rod_blocks.containsKey(block)) {
            valid_rod_blocks.put(block,quality);
            return true;
        }
        return false;
    }

    /**
     *
     * @param block
     * @return
     */
    public static boolean registerValidRodBaseBlock(Block block,int quality) {

        if(!block.equals(Blocks.AIR) && !valid_rod_base_blocks.containsKey(block)) {
            valid_rod_base_blocks.put(block,quality);
            return true;
        }
        return false;
    }

    /**
     *
     * @param block
     * @return
     */
    public static boolean validRodBlock(Block block) {
       return valid_rod_blocks.containsKey(block);
    }

    /**
     *
     * @param block
     * @return
     */
    public static boolean validRodBaseBlock(Block block) {
       return valid_rod_base_blocks.containsKey(block);
    }

    /**
     *
     * @param block
     * @return
     */
    public static int rodQualityOf(Block block) {
        return valid_rod_blocks.get(block);
    }

    /**
     *
     * @param block
     * @return
     */
    public static int baseRodQualityOf(Block block) {
        return valid_rod_base_blocks.get(block);
    }
}
