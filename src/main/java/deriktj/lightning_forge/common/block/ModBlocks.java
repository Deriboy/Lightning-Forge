package deriktj.lightning_forge.common.block;

import deriktj.lightning_forge.common.item.ItemAncientPickaxe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    @GameRegistry.ObjectHolder("lightning_forge:lightning_forge")
    public static BlockLightningForge lightning_forge;
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightning_forge.initModel();
    }
}
