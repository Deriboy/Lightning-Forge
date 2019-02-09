package deriktj.lightning_forge.common.block.base;

import deriktj.lightning_forge.common.core.ModLightningForge;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBaseStairs extends BlockStairs {

    private String name;

    public BlockBaseStairs(String name, IBlockState modelState) {
        super(modelState);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(ModLightningForge.MODID + "." + name);
    }

    public String getName() {
        return name;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
