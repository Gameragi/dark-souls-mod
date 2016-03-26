package gameragi.darksouls.bonfire.tileentities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gameragi.darksouls.ExtendedPlayer;
import gameragi.darksouls.bonfire.DarkSoulsMod;
import gameragi.darksouls.bonfire.blocks.BlockBonfire;
import gameragi.darksouls.servermessages.PlayerHealMessage;
import gameragi.darksouls.servermessages.PlayerSpawnpointMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class TileEntityBonfire extends TileEntity implements IUpdatePlayerListBox {

	private static final int DETECT_RANGE = 2;
	private List<String> registeredPlayers = new ArrayList<String>();
	private ExtendedPlayer extPlayer;
	private Map<String, Boolean> okToRest = new HashMap<String, Boolean>();
	private Integer kindleLevel = 0;

	/**
	 * Returns a list of all players in detect range
	 */
	@SuppressWarnings("unchecked")
	public List<EntityPlayer> anyPlayersInRange() {
		List<EntityPlayer> inRangePlayers = new ArrayList<EntityPlayer>();
		for (EntityPlayer player : (List<EntityPlayer>) this.worldObj.playerEntities) {
			double distanceSquared = player.getPosition().distanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D);
			if (Math.sqrt(distanceSquared) <= (double) TileEntityBonfire.DETECT_RANGE) {
				inRangePlayers.add(player);
			}
		}
		return inRangePlayers;
	}

	/**
	 * Gets called continuously. Set closest player's spawn point if that player
	 * is sneaking within the detection range of the bonfire.
	 */
	@SuppressWarnings("unchecked")
	public void update() {
		List<EntityPlayer> closePlayers = anyPlayersInRange();

		if (closePlayers.size() == 0) {
			Iterator<Entry<String, Boolean>> it = okToRest.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) it.next();
				entry.setValue(true);
			}
		}
		for (EntityPlayer player : closePlayers) {
			if (okToRest.get(player.getName()) == null) {
				okToRest.put(player.getName(), true);
			}

			// client side
			if (this.worldObj.isRemote) {
				extPlayer = ExtendedPlayer.get(player);

				if (!spawnsHere(player.getBedLocation(0)) && registeredPlayers.contains(player.getName())) {
					registeredPlayers.remove(player.getName());
					System.out.println("Detected player's spawn is elsewhere, removing from bonfire list: " + player.getName());
				}

				if (player.isSneaking() && okToRest.get(player.getName())) {
					okToRest.put(player.getName(), false);
					if (!registeredPlayers.contains(player.getName())) {
						DarkSoulsMod.network.sendToServer(new PlayerSpawnpointMessage());
						registeredPlayers.add(player.getName());
						System.out.println(this.getPos().getX() + " New spawnpoint set for " + player.getName());
						Minecraft.getMinecraft().thePlayer.sendChatMessage("Resting at the bonfire.");
					}
					// heal player to full
					DarkSoulsMod.network.sendToServer(new PlayerHealMessage(20));

					// restore estus count to max
					System.out.println("Replenishing estus with a kindleLevel of: " + this.kindleLevel);
					extPlayer.replenishEstus(5 + this.kindleLevel * 5);
				}
				// server side
			} else {
				if (player.isSneaking() && okToRest.get(player.getName())) {
					okToRest.put(player.getName(), false);
					// play bonfire sound for all nearby players, only works on
					// server side
					this.worldObj.playSoundEffect(this.getPos().getX() + 0.5, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5, "mob.ghast.fireball", 0.4F, 0.3F);

					// clear all loaded mob entities
					List<Entity> allEntities = this.worldObj.loadedEntityList;
					for (Entity entity : allEntities) {
						if (entity instanceof IMob) {
							entity.setDead();
						}
					}

					// XXX Need to add spawner reset logic here
				}
			}
		}
	}

	private boolean spawnsHere(BlockPos spawnPos) {
		double distance = Math.sqrt(this.getPos().distanceSqToCenter(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()));
		if (spawnPos != null && distance > DETECT_RANGE * 2) {
			return false;
		}
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.kindleLevel = compound.getInteger("kindle_level");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("kindle_level", this.kindleLevel);
	}

	public Boolean kindleBonfire(EntityPlayer player) {
		if (ExtendedPlayer.get(player).getHumanity() > 0 && this.kindleLevel < 3) {
			ExtendedPlayer.get(player).spendHumanity();
			this.kindleLevel++;
			this.markDirty();
			((BlockBonfire) (this.worldObj.getBlockState(this.pos).getBlock())).kindleEvent(this.worldObj, this.pos);
			// if(!this.worldObj.isRemote){
			this.worldObj.playSoundEffect(this.getPos().getX() + 0.5, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5, "mob.ghast.fireball", 0.4F, 0.3F);
			// }
			System.out.println("my new kindle level is: " + this.kindleLevel);
			return true;
		}
		return false;
	}

	public Integer getKindleLevel() {
		return kindleLevel;
	}

	public void setKindleLevel(Integer kindleLevel) {
		this.kindleLevel = kindleLevel;
	}
}
