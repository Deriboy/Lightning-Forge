package deriktj.lightning_forge.common.block;

import deriktj.lightning_forge.common.block.base.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockWorldTreePlanks extends BlockBase {
    public BlockWorldTreePlanks(String name) {
        super(name, 0.5f, 0, SoundType.WOOD, CreativeTabs.BUILDING_BLOCKS, Material.WOOD);
    }
}
