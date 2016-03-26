package gameragi.darksouls.servermessages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlayerHealMessage implements IMessage{
	
	private int playerHealAmount;
	
	public PlayerHealMessage(){}
	
	public PlayerHealMessage(Integer playerHealAmount){
		this.playerHealAmount = playerHealAmount;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		playerHealAmount = ByteBufUtils.readVarInt(buf, 5);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, playerHealAmount, 5);
	}
	
	public static class Handler implements IMessageHandler<PlayerHealMessage,IMessage>{

		@Override
		public IMessage onMessage(final PlayerHealMessage message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable(){

				@Override
				public void run() {
					System.out.println(String.format("Received %s from %s", message.playerHealAmount, ctx.getServerHandler().playerEntity.getDisplayName()));
					ctx.getServerHandler().playerEntity.heal(message.playerHealAmount);
					ctx.getServerHandler().playerEntity.getFoodStats().setFoodLevel(20); //XXX remove this when you turn off hunger
				}
			});
			return null;
		}
		
	}

}
