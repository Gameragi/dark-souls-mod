package gameragi.darksouls.eventlisteners;

import java.util.List;

import gameragi.darksouls.ExtendedPlayer;
import gameragi.darksouls.bonfire.DarkSoulsMod;
import gameragi.darksouls.servermessages.PlayerEstusMessage;
import gameragi.darksouls.servermessages.PlayerHumanityMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerEventListener {
	
	public PlayerEventListener() {
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onEntityConstructing(EntityConstructing event){
		if(event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null){
			ExtendedPlayer.register((EntityPlayer) event.entity);
			
			event.entity.worldObj.getGameRules().setOrCreateGameRule("showDeathMessages", "false");
			event.entity.worldObj.getGameRules().setOrCreateGameRule("keepInventory", "true");
			event.entity.worldObj.getGameRules().setOrCreateGameRule("naturalRegeneration", "false");
		}
	}
}
