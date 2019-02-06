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

import java.util.Arrays;

public class ItemArtifactPiece extends ItemBase{

    public ItemArtifactPiece(String name) {
        super(name,CreativeTabs.MATERIALS);
    }
}
