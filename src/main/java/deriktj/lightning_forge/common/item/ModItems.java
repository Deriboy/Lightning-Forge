package deriktj.lightning_forge.common.item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("lightning_forge:ancient_pickaxe")
    public static ItemAncientPickaxe ancient_pickaxe;

    @GameRegistry.ObjectHolder("lightning_forge:artifact_piece")
    public static ItemArtifactPiece artifact_piece;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        ancient_pickaxe.initModel();
        artifact_piece.initModel();
    }
}
