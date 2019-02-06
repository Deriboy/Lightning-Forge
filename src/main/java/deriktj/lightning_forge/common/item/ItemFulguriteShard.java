package deriktj.lightning_forge.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFulguriteShard extends ItemBase {

    public ItemFulguriteShard(String name) {
        super(name,CreativeTabs.MATERIALS);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip"));
    }
}
