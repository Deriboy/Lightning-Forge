package deriktj.lightning_forge.common.item;

import deriktj.lightning_forge.common.item.artifact.ItemAncientPickaxe;
import deriktj.lightning_forge.common.item.artifact.ItemFrostOrb;
import deriktj.lightning_forge.common.item.crafting.ItemArtifactPiece;
import deriktj.lightning_forge.common.item.crafting.ItemFulguriteShard;
import deriktj.lightning_forge.common.item.crafting.ItemLightningGlassPhial;
import deriktj.lightning_forge.common.item.crafting.ItemWorldTreeCore;
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

    @GameRegistry.ObjectHolder("lightning_forge:frozen_core")
    public static ItemWorldTreeCore frozen_core;

    @GameRegistry.ObjectHolder("lightning_forge:frost_orb")
    public static ItemFrostOrb frost_orb;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        ancient_pickaxe.initModel();
        artifact_piece_pickaxe.initModel();
        artifact_piece_spear.initModel();
        fulgurite_shard_white.initModel();
        fulgurite_shard_red.initModel();
        lightning_glass_phial.initModel();
        bottled_lightning.initModel();
        frozen_core.initModel();
        frost_orb.initModel();

    }
}
