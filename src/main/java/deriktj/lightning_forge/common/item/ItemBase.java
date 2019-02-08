package deriktj.lightning_forge.common.item;

import deriktj.lightning_forge.common.core.ModLightningForge;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemBase extends Item {

    private String name;

    public ItemBase(String name,CreativeTabs tab) {
        super();
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(ModLightningForge.MODID + "." + name);
        setCreativeTab(tab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public String getName() {
        return name;
    }
}

