package gameragi.darksouls.servermessages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlayerSpawnpointMessage implements IMessage{

	public PlayerSpawnpointMessage(){}
	

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}
	
	public static class Handler implements IMessageHandler<PlayerSpawnpointMessage,IMessage>{

		@Override
		public IMessage onMessage(final PlayerSpawnpointMessage message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable(){

				@Override
				public void run() {
					EntityPlayerMP player = ctx.getServerHandler().playerEntity ;
					player.setSpawnPoint(player.getPosition(), true);
					System.out.println("Set spawnpoint for player at " + player.getPosition().getX() + ", " + player.getPosition().getY() + ", " + player.getPosition().getZ());
				}
			});
			return null;
		}
		
	}

}
