package deriktj.lightning_forge.common.item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("lightning_forge:ancient_pickaxe")
    public static ItemAncientPickaxe ancient_pickaxe;

    @GameRegistry.ObjectHolder("lightning_forge:artifact_piece_pickaxe")
    public static ItemArtifactPiece artifact_piece_pickaxe;

    @GameRegistry.ObjectHolder("lightning_forge:artifact_piece_spear")
    public static ItemArtifactPiece artifact_piece_spear;

    @GameRegistry.ObjectHolder("lightning_forge:fulgurite_shard_white")
    public static ItemFulguriteShard fulgurite_shard_white;

    @GameRegistry.ObjectHolder("lightning_forge:fulgurite_shard_red")
    public static ItemFulguriteShard fulgurite_shard_red;

    @GameRegistry.ObjectHolder("lightning_forge:lightning_glass_phial")
    public static ItemLightningGlassPhial lightning_glass_phial;

    @GameRegistry.ObjectHolder("lightning_forge:bottled_lightning")
    public static ItemBottledLightning bottled_lightning;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        ancient_pickaxe.initModel();
        artifact_piece_pickaxe.initModel();
        artifact_piece_spear.initModel();
        fulgurite_shard_white.initModel();
        fulgurite_shard_red.initModel();
        lightning_glass_phial.initModel();
        bottled_lightning.initModel();

    }
}
