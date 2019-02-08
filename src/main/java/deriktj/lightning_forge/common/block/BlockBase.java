package deriktj.lightning_forge.common.block;

import deriktj.lightning_forge.common.core.ModLightningForge;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBase extends Block {

    private String name;

    public BlockBase(String name, float hardness, float lightLevel, SoundType soundType, CreativeTabs tab, Material materialIn) {
        super(materialIn);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(ModLightningForge.MODID + "." + name);
        setHardness(hardness);
        setLightLevel(lightLevel);
        setSoundType(soundType);
        setCreativeTab(tab);
    }

    public String getName() {
        return name;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


}
