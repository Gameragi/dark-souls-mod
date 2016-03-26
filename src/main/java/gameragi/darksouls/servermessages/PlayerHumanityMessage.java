package gameragi.darksouls.servermessages;

import gameragi.darksouls.ExtendedPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlayerHumanityMessage implements IMessage {
	private static int humanity;

	public PlayerHumanityMessage(){}
	
	public PlayerHumanityMessage(Integer humanity){
		this.humanity = humanity;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		humanity = ByteBufUtils.readVarInt(buf, 5);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, humanity, 5);
	}
	
	public static class HandlerOnServer implements IMessageHandler<PlayerHumanityMessage,IMessage>{

		@Override
		public IMessage onMessage(final PlayerHumanityMessage message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable(){

				@Override
				public void run() {
					System.out.println(String.format("Received %s humanity from %s", message.humanity, ctx.getServerHandler().playerEntity.getName()));
					ExtendedPlayer player = ExtendedPlayer.get(ctx.getServerHandler().playerEntity);
					player.setHumanity(humanity);
				}
			});
			return null;
		}
		
	}
	
	public static class HandlerOnClient implements IMessageHandler<PlayerHumanityMessage,IMessage>{

		@Override
		public IMessage onMessage(final PlayerHumanityMessage message, final MessageContext ctx) {
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable(){

				@Override
				public void run() {
					System.out.println(String.format("Received %s humanity from %s", message.humanity, Minecraft.getMinecraft().thePlayer.getName()));
					ExtendedPlayer player = ExtendedPlayer.get(Minecraft.getMinecraft().thePlayer);
					player.setHumanity(humanity);
				}
			});
			return null;
		}
		
	}

}
