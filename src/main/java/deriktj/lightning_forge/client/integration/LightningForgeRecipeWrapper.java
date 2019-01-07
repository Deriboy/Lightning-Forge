package deriktj.lightning_forge.client.integration;

import deriktj.lightning_forge.api.recipe.RecipeLightningForge;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.List;

public class LightningForgeRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> inputs;
    private final ItemStack output;
    private final int minimumRodQuality;

    public LightningForgeRecipeWrapper(RecipeLightningForge recipe) {
        inputs = recipe.getInputs();
        output = recipe.getResult();
        minimumRodQuality = recipe.getMinimumRodQuality();
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInputs(VanillaTypes.ITEM,inputs);
        iIngredients.setOutput(VanillaTypes.ITEM,output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(String.valueOf(minimumRodQuality), 77, 0, Color.gray.getRGB());
    }
}
