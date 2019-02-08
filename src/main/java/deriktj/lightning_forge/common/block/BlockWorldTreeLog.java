package deriktj.lightning_forge.common.block;

import deriktj.lightning_forge.common.core.ModLightningForge;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockWorldTreeLog extends BlockBaseLog {

    private Item drop;

    public static final PropertyEnum<EnumAlive> LOG_ALIVE = PropertyEnum.create("alive", EnumAlive.class);

    public BlockWorldTreeLog(String name) {
        super(name, 1.0f, 0);
        setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y).withProperty(LOG_ALIVE,EnumAlive.DEAD));

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops,world,pos,state,fortune);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if(state.getValue(LOG_ALIVE) == EnumAlive.ALIVE && rand.nextInt(6) >= 5 - fortune) {
            drops.add(new ItemStack(drop));
        }
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip"));
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if(stateIn.getValue(LOG_ALIVE).equals(EnumAlive.ALIVE)) {
            this.spawnParticles(worldIn, pos);
        }

    }

    private void spawnParticles(World worldIn, BlockPos pos)
    {
        Random random = worldIn.rand;
        double d0 = 0.0625D;

        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)pos.getX() + random.nextFloat());
            double d2 = (double)((float)pos.getY() + random.nextFloat());
            double d3 = (double)((float)pos.getZ() + random.nextFloat());

            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
            {
                d2 = (double)pos.getY() + 0.0625D + 1.0D;
            }

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
            {
                d2 = (double)pos.getY() - 0.0625D;
            }

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() + 0.0625D + 1.0D;
            }

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() - 0.0625D;
            }

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
            {
                d1 = (double)pos.getX() + 0.0625D + 1.0D;
            }

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
            {
                d1 = (double)pos.getX() - 0.0625D;
            }

            if (d1 < (double)pos.getX() || d1 > (double)(pos.getX() + 1) || d2 < 0.0D || d2 > (double)(pos.getY() + 1) || d3 < (double)pos.getZ() || d3 > (double)(pos.getZ() + 1))
            {
                worldIn.spawnParticle(EnumParticleTypes.CRIT, d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();

        switch (meta & 7) {
            case 0:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y).withProperty(LOG_ALIVE, EnumAlive.DEAD);
                break;
            case 1:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y).withProperty(LOG_ALIVE, EnumAlive.ALIVE);
                break;
            case 2:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X).withProperty(LOG_ALIVE, EnumAlive.DEAD);
                break;
            case 3:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X).withProperty(LOG_ALIVE, EnumAlive.ALIVE);
                break;
            case 4:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z).withProperty(LOG_ALIVE, EnumAlive.DEAD);
                break;
            case 5:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z).withProperty(LOG_ALIVE, EnumAlive.ALIVE);
                break;
            case 6:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE).withProperty(LOG_ALIVE, EnumAlive.DEAD);
                break;
            default:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE).withProperty(LOG_ALIVE, EnumAlive.ALIVE);
        }

        return state;
    }

    @Override
    @SuppressWarnings("incomplete-switch")
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        switch (state.getValue(LOG_AXIS)) {
            case X:
                meta |= 2;
                break;
            case Z:
                meta |= 4;
                break;
            case NONE:
                meta |= 6;
        }
        switch(state.getValue(LOG_ALIVE)) {
            case ALIVE:
                meta |= 1;
        }

        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS,LOG_ALIVE});
    }

    public enum EnumAlive implements IStringSerializable {
        ALIVE("alive"),
        DEAD("dead");

        private final String name;

        EnumAlive(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
