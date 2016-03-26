package gameragi.darksouls;

import gameragi.darksouls.bonfire.DarkSoulsMod;
import gameragi.darksouls.servermessages.PlayerEstusMessage;
import gameragi.darksouls.servermessages.PlayerHealMessage;
import gameragi.darksouls.servermessages.PlayerHumanityMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExtendedPlayer implements IExtendedEntityProperties {
	
	public static final String DARK_SOULS_PROP = "DarkSoulsProperties";
	public static final String CURRENT_ESTUS_COUNT = "CurrentEstusCount";
	public static final String CURRENT_ESTUS_MAX = "CurrentEstusMax";
	public static final String SOULS_XP = "SoulsXp";
	public static final String HUMANITY = "Humanity";
	public static final int INITIAL_ESTUS_MAX = 5;
	
	private final EntityPlayer player;
	
	private int currentEstusCount;
	private int maxEstus;
	private int soulsXp;
	private int humanity;
	
	public ExtendedPlayer(EntityPlayer player){
		this.player = player;
		this.currentEstusCount = this.maxEstus = INITIAL_ESTUS_MAX;
		this.soulsXp = 0;
		this.humanity = 0;
	}
	
	public static final void register(EntityPlayer player){
		player.registerExtendedProperties(ExtendedPlayer.DARK_SOULS_PROP, new ExtendedPlayer(player));
	}
	
	public static final ExtendedPlayer get(EntityPlayer player){
		return (ExtendedPlayer) player.getExtendedProperties(DARK_SOULS_PROP);
	}
	

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger(CURRENT_ESTUS_COUNT, this.currentEstusCount);
		properties.setInteger(CURRENT_ESTUS_MAX, this.maxEstus);
		properties.setInteger(SOULS_XP, this.soulsXp);
		properties.setInteger(HUMANITY, this.humanity);

		compound.setTag(DARK_SOULS_PROP, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(DARK_SOULS_PROP);
		
		this.currentEstusCount = properties.getInteger(CURRENT_ESTUS_COUNT);
		this.maxEstus = properties.getInteger(CURRENT_ESTUS_MAX);
		this.soulsXp = properties.getInteger(SOULS_XP);
		this.humanity = properties.getInteger(HUMANITY);
		
		System.out.println("Estus from NBT: " + this.currentEstusCount + "/" + this.maxEstus);
	}

	@Override
	public void init(Entity entity, World world) {
		
	}
	
	/**
	 * Consume one Estus Flask if the player has at least 1 and heal 4 hearts.
	 * @return true if successful
	 */
	@SideOnly(Side.CLIENT)
	public boolean drinkEstus(){
		if(this.currentEstusCount > 0){
			DarkSoulsMod.network.sendToServer(new PlayerHealMessage(8));
			this.currentEstusCount -= 1;
			return true;
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int consumeHumanity(){
		DarkSoulsMod.network.sendToServer(new PlayerHealMessage(20));
		this.humanity = this.humanity < 99 ? this.humanity + 1 : 99;
		return humanity;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean spendHumanity(){
		if(humanity > 0){
			this.humanity -= 1;
			return true;
		}
		return false;
	}
	
	/**
	 * Restores player's current Estus count to their max.
	 */
	public void replenishEstus(int amount){
		this.currentEstusCount = (this.currentEstusCount < amount) ?  amount : this.currentEstusCount;
	}

	public int getCurrentEstusCount() {
		return currentEstusCount;
	}

	public int getMaxEstus() {
		return maxEstus;
	}
	
	public void setSoulsXp(int amount){
		soulsXp = amount;
	}
	
	public int getSoulsXp(){
		return soulsXp;
	}
	
	public void setHumanity(int amount){
		humanity = amount;
	}
	
	public int getHumanity(){
		return humanity;
	}

	public void setCurrentEstusCount(int currentEstusCount) {
		this.currentEstusCount = currentEstusCount;
	}
}
