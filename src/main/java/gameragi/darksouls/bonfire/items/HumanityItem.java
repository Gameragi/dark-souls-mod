package gameragi.darksouls.bonfire.items;

import gameragi.darksouls.ExtendedPlayer;
import gameragi.darksouls.bonfire.DarkSoulsMod;
import gameragi.darksouls.servermessages.PlayerHumanityMessage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class HumanityItem extends ItemFood {

	public HumanityItem() {
		super(20, false); //heals 20 food and cannot be used as wolf food
		this.setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!worldIn.isRemote) {
			ExtendedPlayer.get(player).consumeHumanity();
			DarkSoulsMod.network.sendTo(new PlayerHumanityMessage(ExtendedPlayer.get(player).getHumanity()), (EntityPlayerMP) player);
			System.out.println("Player's current humanity count: " + ExtendedPlayer.get(player).getHumanity());
		}
	}
	
	/**
     * How long it takes to use or consume an item
     */
	@Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32; //default is 32
    }
	
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

}
