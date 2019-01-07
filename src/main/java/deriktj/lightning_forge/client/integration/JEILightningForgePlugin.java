package deriktj.lightning_forge.client.integration;
import deriktj.lightning_forge.api.LightningForgeAPI;
import deriktj.lightning_forge.api.recipe.RecipeLightningForge;
import deriktj.lightning_forge.common.block.ModBlocks;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEILightningForgePlugin implements IModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new LightningForgeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(RecipeLightningForge.class, LightningForgeRecipeWrapper::new,LightningForgeRecipeCategory.UID);
        registry.addRecipes(LightningForgeAPI.lightningForgeRecipes,LightningForgeRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.lightning_forge),LightningForgeRecipeCategory.UID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
