package deriktj.lightning_forge.common.block;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    @GameRegistry.ObjectHolder("lightning_forge:lightning_forge")
    public static BlockLightningForge lightning_forge;

    @GameRegistry.ObjectHolder("lightning_forge:fulgurite")
    public static BlockFulgurite fulgurite;

    @GameRegistry.ObjectHolder("lightning_forge:red_fulgurite")
    public static BlockRedFulgurite red_fulgurite;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightning_forge.initModel();
        fulgurite.initModel();
        red_fulgurite.initModel();
    }
}
