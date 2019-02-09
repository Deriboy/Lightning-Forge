package deriktj.lightning_forge.common.block.base;

import deriktj.lightning_forge.common.core.ModLightningForge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.collection.immutable.List;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Locale;

public class BlockBaseSlab extends BlockSlab {

    private String name;
    private boolean isDouble;
    private Block slab;

    private static final PropertyEnum<Yes> YES = PropertyEnum.create("yes", Yes.class);

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    public BlockBaseSlab(String name, float hardness, Material materialIn, SoundType soundType, boolean isDouble) {
        super(materialIn);
        this.name = name;
        this.isDouble = isDouble;
        setRegistryName(name);
        setUnlocalizedName(ModLightningForge.MODID + "." + name);
        setHardness(hardness);
        setSoundType(soundType);
        setDefaultState(blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
        if(!isDouble) {
            setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
            useNeighborBrightness = true;
        }
    }

    public void setSlab(Block slab) {
        this.slab = slab;
    }


    @Override
    public String getUnlocalizedName(int meta) {
        return getUnlocalizedName();
    }

    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player) {
        if(isDouble) {
            return new ItemStack(slab);
        }
        else {
            return new ItemStack(this);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (isDouble) {
            return getDefaultState();
        } else {
            return getDefaultState().withProperty(HALF, meta == 8 ? EnumBlockHalf.TOP : EnumBlockHalf.BOTTOM);
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (isDouble) {
            return 0;
        } else {
            return state.getValue(HALF) == EnumBlockHalf.TOP ? 8 : 0;
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if(!isDouble) {
            super.getDrops(drops, world, pos, state, fortune);
        }
        else {
            drops.add(new ItemStack(Item.getItemFromBlock(slab),2));
        }
    }

    @Override
    public boolean isDouble() {
        return isDouble;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, getVariantProperty());
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return YES;
    }
    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return Yes.SINGLETON;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        if(isDouble) {
            ModelLoader.setCustomStateMapper(this,
                    (new StateMap.Builder()).ignore(new IProperty[] { BlockBaseSlab.YES , BlockBaseSlab.HALF }).build());
        }
        else {
            ModelLoader.setCustomStateMapper(this,
                    (new StateMap.Builder()).ignore(new IProperty[] { BlockBaseSlab.YES }).build());
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        }
    }

    public String getName() {
        return name;
    }

    private enum Yes implements IStringSerializable {
        SINGLETON {
            @Override
            public String getName() {
                return name().toLowerCase(Locale.ROOT);
            }
        }
    }
}
