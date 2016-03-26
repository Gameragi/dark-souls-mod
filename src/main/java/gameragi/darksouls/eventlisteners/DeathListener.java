package gameragi.darksouls.eventlisteners;

import java.util.List;

import gameragi.darksouls.ExtendedPlayer;
import gameragi.darksouls.bonfire.init.Items;
import gameragi.darksouls.bonfire.items.SoulsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeathListener {

	@SuppressWarnings("unchecked")
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onEntityDeath(LivingDeathEvent event) {
		if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.entity;
			String playerName = player.getName();
			int playerXp = player.experienceTotal;
			int humanity = ExtendedPlayer.get(player).getHumanity();

			// clear current player souls entities in world
			List<Entity> allEntities = event.entity.worldObj.loadedEntityList;
			for (Entity entity : allEntities) {
				if (entity instanceof EntityItem) {
					if (((EntityItem) entity).getEntityItem() != null && ((EntityItem) entity).getEntityItem().getTagCompound() != null && ((EntityItem) entity).getEntityItem().getTagCompound().getString("player_name") == playerName) {
						entity.setDead();
					}
				}
			}

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("player_name", playerName);
			nbt.setInteger("xp", playerXp);
			nbt.setInteger("humanity", humanity);
			SoulsItem souls = (SoulsItem) Items.souls;
			ItemStack stack = new ItemStack(souls);
			stack.setTagCompound(nbt);
			event.entity.worldObj.spawnEntityInWorld(new EntityItem(event.entity.worldObj, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), stack));

			player.removeExperienceLevel(player.experienceLevel + 1);
		}
	}
}
