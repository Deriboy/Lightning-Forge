package deriktj.lightning_forge.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class WorldParticleMessage implements IMessage {

    public EnumParticleTypes type;
    public double posX;
    public double posY;
    public double posZ;
    public double velX;
    public double velY;
    public double velZ;



    public WorldParticleMessage() {
    }

    public WorldParticleMessage(EnumParticleTypes particleType, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this.type = particleType;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type.getParticleID());
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeDouble(velX);
        buf.writeDouble(velY);
        buf.writeDouble(velZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = EnumParticleTypes.getParticleFromId(buf.readInt());
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
        velX = buf.readDouble();
        velY = buf.readDouble();
        velZ = buf.readDouble();
    }
}
