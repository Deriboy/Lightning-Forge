package deriktj.lightning_forge.common.block;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    @GameRegistry.ObjectHolder("lightning_forge:lightning_forge")
    public static BlockLightningForge lightning_forge;

    @GameRegistry.ObjectHolder("lightning_forge:white_fulgurite")
    public static BlockFulgurite white_fulgurite;

    @GameRegistry.ObjectHolder("lightning_forge:red_fulgurite")
    public static BlockFulgurite red_fulgurite;

    @GameRegistry.ObjectHolder("lightning_forge:white_lightning_glass")
    public static BlockLightningGlass white_lightning_glass;
    @GameRegistry.ObjectHolder("lightning_forge:red_lightning_glass")
    public static BlockLightningGlass red_lightning_glass;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightning_forge.initModel();
        white_fulgurite.initModel();
        red_fulgurite.initModel();
        white_lightning_glass.initModel();
        red_lightning_glass.initModel();
    }
}
