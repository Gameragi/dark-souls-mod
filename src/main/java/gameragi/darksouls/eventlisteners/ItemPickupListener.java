package gameragi.darksouls.eventlisteners;

import gameragi.darksouls.ExtendedPlayer;
import gameragi.darksouls.bonfire.DarkSoulsMod;
import gameragi.darksouls.servermessages.PlayerHumanityMessage;
import gameragi.darksouls.servermessages.PlayerSoulMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemPickupListener {
	int humanity = 0;

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onEntityItemPickup(EntityItemPickupEvent event){
		if(event.entity instanceof EntityPlayer && event.item.getEntityItem().getItem().getUnlocalizedName().equals("item.souls")){
			
			boolean isCorrectPlayer = false;
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(!player.worldObj.isRemote){
				NBTTagCompound nbtTag = event.item.getEntityItem().getTagCompound();
				int xpToTransfer = nbtTag.getInteger("xp");
				String playerName = nbtTag.getString("player_name");
				humanity = nbtTag.getInteger("humanity");
				
				if(player.getName().equals(playerName)){
					System.out.println("XP found on souls: " + xpToTransfer + " for player: " + playerName);
					DarkSoulsMod.network.sendToServer(new PlayerSoulMessage(xpToTransfer));
					DarkSoulsMod.network.sendToServer(new PlayerHumanityMessage(humanity));
					DarkSoulsMod.network.sendTo(new PlayerHumanityMessage(humanity), (EntityPlayerMP) player);
					player.worldObj.playSoundAtEntity(player, "fireworks.twinkle1", 1.0F, 0.5F);
					event.item.setDead();
					isCorrectPlayer = true;
				}
			}
			event.setCanceled(true);
		}
	}
}
