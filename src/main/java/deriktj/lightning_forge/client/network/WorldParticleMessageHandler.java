package deriktj.lightning_forge.client.network;

import deriktj.lightning_forge.common.network.WorldParticleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WorldParticleMessageHandler implements IMessageHandler<WorldParticleMessage,IMessage> {

    @Override
    public IMessage onMessage(WorldParticleMessage message, MessageContext ctx) {

        Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().world.spawnParticle(message.type,message.posX,message.posY,message.posZ,message.velX,message.velY,message.velZ));
        return null;
    }

}
