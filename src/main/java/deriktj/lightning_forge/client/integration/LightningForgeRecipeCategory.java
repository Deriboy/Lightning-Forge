package deriktj.lightning_forge.client.integration;

import deriktj.lightning_forge.common.ModLightningForge;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LightningForgeRecipeCategory implements IRecipeCategory {

    public static final String UID = ModLightningForge.MODID + ".forge";
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;

    public LightningForgeRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168,50);
        this.localizedName = I18n.format(ModLightningForge.MODID + ".jei.lightningForge");
        overlay = guiHelper.createDrawable(new ResourceLocation(ModLightningForge.MODID, "textures/gui/lightning_forge_overlay.png"),
                0, 0, 168, 50);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public String getModName() {
        return "";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, IRecipeWrapper iRecipeWrapper, IIngredients iIngredients) {
        iRecipeLayout.getItemStacks().init(0,true,13,0);
        iRecipeLayout.getItemStacks().set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
        iRecipeLayout.getItemStacks().init(1,true,45,0);
        iRecipeLayout.getItemStacks().set(1, iIngredients.getInputs(VanillaTypes.ITEM).get(1));
        iRecipeLayout.getItemStacks().init(2,true,13,32);
        iRecipeLayout.getItemStacks().set(2, iIngredients.getInputs(VanillaTypes.ITEM).get(2));
        iRecipeLayout.getItemStacks().init(3,true,45,32);
        iRecipeLayout.getItemStacks().set(3, iIngredients.getInputs(VanillaTypes.ITEM).get(3));

        iRecipeLayout.getItemStacks().init(4,false,120,16);
        iRecipeLayout.getItemStacks().set(4, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));

    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        overlay.draw(minecraft,0,0);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }
}
