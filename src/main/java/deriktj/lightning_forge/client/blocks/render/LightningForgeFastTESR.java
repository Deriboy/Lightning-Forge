package deriktj.lightning_forge.client.blocks.render;

import deriktj.lightning_forge.common.ModLightningForge;
import deriktj.lightning_forge.common.tile.TileLightningForge;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.items.IItemHandler;

import static deriktj.lightning_forge.common.block.FacingBlock.FACING;

public class LightningForgeFastTESR extends FastTESR<TileLightningForge> {

    public static final LightningForgeFastTESR SINGLETON = new LightningForgeFastTESR();

    private LightningForgeFastTESR() {
        super();
    }

    @Override
    public void renderTileEntityFast(TileLightningForge lf, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {

        World world = lf.getWorld();
        world.isThundering();
        IItemHandler inv = lf.getInventory(null);
        float speed = 2;
        float scale = 0.5f;
        for(int corner = 0; corner < lf.getInventory(null).getSlots(); corner++){
            long j =  lf.getWorld().getTotalWorldTime();
            EntityItem item = new EntityItem(world, 0.0D, 0.0D, 0.0D, inv.getStackInSlot(corner));

            item.hoverStart = 0;
            IBlockState state = lf.getWorld().getBlockState(lf.getPos());
            Vec3d pos = cornerPos(corner,state);

            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x + .5,(float)y + 0,(float)z + .5);   //translate to center
            if(world.isThundering() && lf.getCurrentRecipe() != null) {
                GlStateManager.rotate(j*speed,0,1,0);                       //orbit
            }
            GlStateManager.translate(pos.x, pos.y,  pos.z);                                 // to corners
            GlStateManager.scale(scale,scale,scale);                                        //scale
            GlStateManager.rotate(j*speed + corner*90, 0, 1, 0);            //spin
            Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F,false);
            GlStateManager.popMatrix();
        }
    }

    private Vec3d cornerPos(int corner,IBlockState state) {
        Vec3d pos;
        EnumFacing face = state.getValue(FACING);
        int index = face.getHorizontalIndex();
        corner = (corner + index + 1) % 4;
        switch(corner){
            case 0:
                pos = new Vec3d(-.3,1,.3);

                break;
            case 1:
                pos = new Vec3d(.3,1,.3);
                break;
            case 2:
                pos = new Vec3d(.3,1,-.3);
                break;
            case 3:
                pos = new Vec3d(-.3,1,-.3);
                break;
            default:
                pos = new Vec3d(0,1,0);
                break;
        }
        return pos;

    }

}
