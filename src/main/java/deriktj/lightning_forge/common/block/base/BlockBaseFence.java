package deriktj.lightning_forge.common.block.base;

import deriktj.lightning_forge.common.core.ModLightningForge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBaseFence extends BlockFence {

    private String name;

    public BlockBaseFence(String name, float hardness, float lightLevel) {
        super(Material.WOOD,Material.WOOD.getMaterialMapColor());
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(ModLightningForge.MODID + "." + name);
        setHardness(hardness);
        setLightLevel(lightLevel);
        setSoundType(SoundType.WOOD);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        Blocks.FIRE.setFireInfo(this,5,5);
    }


    public String getName() {
        return name;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
