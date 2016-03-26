package gameragi.darksouls.servermessages;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlayerSoulMessage implements IMessage{
private int xpAmount;
	
	public PlayerSoulMessage(){}
	
	public PlayerSoulMessage(Integer xpAmount){
		this.xpAmount = xpAmount;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		xpAmount = ByteBufUtils.readVarInt(buf, 5);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, xpAmount, 5);
	}
	
	public static class Handler implements IMessageHandler<PlayerSoulMessage,IMessage>{

		@Override
		public IMessage onMessage(final PlayerSoulMessage message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable(){

				@Override
				public void run() {
					System.out.println(String.format("Received %s xp from %s", message.xpAmount, ctx.getServerHandler().playerEntity.getName()));
					ctx.getServerHandler().playerEntity.addExperience(message.xpAmount);
				}
			});
			return null;
		}
		
	}
}
