package deriktj.lightning_forge.common.entity;

import deriktj.lightning_forge.common.ModLightningForge;
import deriktj.lightning_forge.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

    public static void init() {
        int id = 0;
        EntityRegistry.registerModEntity(new ResourceLocation(ModLightningForge.MODID,"bottled_lightning"),EntityBottledLightning.class,"bottledLightning",id++, ModLightningForge.instance, 64, 10, true);

    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityBottledLightning.class, renderManager -> new RenderSnowball<>(renderManager, ModItems.bottled_lightning, Minecraft.getMinecraft().getRenderItem()));
    }
}
