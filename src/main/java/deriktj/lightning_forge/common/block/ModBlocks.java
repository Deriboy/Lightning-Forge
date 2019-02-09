package deriktj.lightning_forge.common.block;

import deriktj.lightning_forge.common.block.base.BlockBaseFence;
import deriktj.lightning_forge.common.block.base.BlockBaseFenceGate;
import deriktj.lightning_forge.common.block.base.BlockBaseSlab;
import deriktj.lightning_forge.common.block.base.BlockBaseStairs;
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

    @GameRegistry.ObjectHolder("lightning_forge:frozen_log")
    public static BlockWorldTreeLog frozen_log;

    @GameRegistry.ObjectHolder("lightning_forge:frozen_leaves")
    public static BlockWorldTreeLeaves frozen_leaves;

    @GameRegistry.ObjectHolder("lightning_forge:frozen_sapling")
    public static BlockWorldTreeSapling frozen_sapling;

    @GameRegistry.ObjectHolder("lightning_forge:frozen_planks")
    public static BlockWorldTreePlanks frozen_planks;

    @GameRegistry.ObjectHolder("lightning_forge:frozen_fence")
    public static BlockBaseFence frozen_fence;

    @GameRegistry.ObjectHolder("lightning_forge:frozen_fence_gate")
    public static BlockBaseFenceGate frozen_fence_gate;

    @GameRegistry.ObjectHolder("lightning_forge:frozen_slab")
    public static BlockBaseSlab frozen_slab;

    @GameRegistry.ObjectHolder("lightning_forge:frozen_slab_double")
    public static BlockBaseSlab frozen_slab_double;

    @GameRegistry.ObjectHolder("lightning_forge:frozen_stairs")
    public static BlockBaseStairs frozen_stairs;

    @GameRegistry.ObjectHolder("lightning_forge:scorched_slab_double")
    public static BlockBaseSlab scorched_slab_double;

    @GameRegistry.ObjectHolder("lightning_forge:scorched_slab")
    public static BlockBaseSlab scorched_slab;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightning_forge.initModel();
        white_fulgurite.initModel();
        red_fulgurite.initModel();
        white_lightning_glass.initModel();
        red_lightning_glass.initModel();
        frozen_log.initModel();
        frozen_leaves.initModel();
        frozen_sapling.initModel();
        frozen_planks.initModel();
        frozen_fence.initModel();
        frozen_fence_gate.initModel();
        frozen_slab.initModel();
        frozen_slab_double.initModel();
        frozen_stairs.initModel();

        scorched_slab.initModel();
        scorched_slab_double.initModel();
    }
}
