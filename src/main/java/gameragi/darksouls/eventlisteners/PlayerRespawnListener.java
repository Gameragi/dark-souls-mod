package gameragi.darksouls.eventlisteners;

import gameragi.darksouls.ExtendedPlayer;
import gameragi.darksouls.bonfire.DarkSoulsMod;
import gameragi.darksouls.servermessages.PlayerEstusMessage;
import gameragi.darksouls.servermessages.PlayerHumanityMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerRespawnListener {
	
	public PlayerRespawnListener() {
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onPlayerSpawn(EntityEvent.CanUpdate event){
		if(!event.entity.worldObj.isRemote){
			EntityPlayer player;
			try{
				player = (EntityPlayer)event.entity;
			}catch(Exception e){
				return;
			}
			System.out.println("player tick humanity = " + ExtendedPlayer.get(player).getHumanity());
			DarkSoulsMod.network.sendTo(new PlayerHumanityMessage(ExtendedPlayer.get(player).getHumanity()), (EntityPlayerMP) event.entity);
			DarkSoulsMod.network.sendTo(new PlayerEstusMessage(ExtendedPlayer.get(player).getCurrentEstusCount()), (EntityPlayerMP) event.entity);
		}
	}
}
