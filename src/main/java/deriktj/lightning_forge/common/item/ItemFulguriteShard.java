package deriktj.lightning_forge.common.item;

import deriktj.lightning_forge.common.ModLightningForge;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFulguriteShard extends Item {

    private String name = "fulgurite_shard";
    private String[] variants = {"sand","red_sand"};

    public ItemFulguriteShard() {
        super();
        setRegistryName(name);        // The unique name (within your mod) that identifies this item
        setUnlocalizedName(ModLightningForge.MODID + "." + name);     // Used for localization (en_US.lang)
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.MATERIALS);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for(int i = 0; i < variants.length; i++) {
                items.add(new ItemStack(this,1,i));
            }
        }

    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "_" + variants[stack.getMetadata()];
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        for(int i = 0; i < variants.length; i++) {
            ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(getRegistryName() + "_" + variants[i], "inventory"));
        }
    }
}
