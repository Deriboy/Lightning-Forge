package deriktj.lightning_forge.common.block.base;

import deriktj.lightning_forge.common.core.ModLightningForge;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBaseFenceGate extends BlockFenceGate {

    private String name;

    public BlockBaseFenceGate(String name, float hardness, float lightLevel) {
        super(BlockPlanks.EnumType.OAK);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(ModLightningForge.MODID + "." + name);
        setHardness(hardness);
        setLightLevel(lightLevel);
        setSoundType(SoundType.WOOD);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        Blocks.FIRE.setFireInfo(this,5,5);
    }

    public String getName() {
        return name;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomStateMapper(this,
                (new StateMap.Builder()).ignore(new IProperty[] { BlockFenceGate.POWERED }).build());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
