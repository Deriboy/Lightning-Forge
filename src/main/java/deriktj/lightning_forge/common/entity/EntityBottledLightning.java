package deriktj.lightning_forge.common.entity;

import deriktj.lightning_forge.common.core.LightningHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBottledLightning extends EntityThrowable {

    public EntityBottledLightning(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }
    public EntityBottledLightning(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(!this.world.isRemote) {
            if(result.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                BlockPos strikePos = result.getBlockPos().offset(result.sideHit);
                LightningHelper.strikeAt(strikePos,this.world,false);
                this.setDead();
            }
            else if(result.typeOfHit.equals(RayTraceResult.Type.ENTITY)) {
                BlockPos strikePos = result.entityHit.getPosition();
                LightningHelper.strikeAt(strikePos,this.world,false);
                this.setDead();
            }
            else if(result.typeOfHit.equals(RayTraceResult.Type.MISS)) {
                this.setDead();
            }
        }
    }

}
