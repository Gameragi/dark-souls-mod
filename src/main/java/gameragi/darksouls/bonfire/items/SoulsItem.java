package gameragi.darksouls.bonfire.items;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class SoulsItem extends Item {

	public SoulsItem() {
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("souls");
	}

	@Override
	public boolean onEntityItemUpdate(net.minecraft.entity.item.EntityItem entityItem) {
		Random random = Minecraft.getMinecraft().theWorld.rand;

        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)entityItem.getPosition().getX() + 0.2 + random.nextFloat()*(0.8));
            double d2 = (double)((float)entityItem.getPosition().getY() + 0.2 + random.nextFloat()*(0.5));
            double d3 = (double)((float)entityItem.getPosition().getZ() + 0.2 + random.nextFloat()*(0.8));
            
            Minecraft.getMinecraft().theWorld.spawnParticle(EnumParticleTypes.SPELL_MOB, d1, d2, d3, 0.0D, 255.0D, 0.0D, new int[0]);
            
            d1 = (double)((float)entityItem.getPosition().getX() + 0.0 + random.nextFloat()*(1.0));
            d2 = (double)((float)entityItem.getPosition().getY() + 0.0 + random.nextFloat()*(0.01));
            d3 = (double)((float)entityItem.getPosition().getZ() + 0.0 + random.nextFloat()*(1.0));
            
            Minecraft.getMinecraft().theWorld.spawnParticle(EnumParticleTypes.TOWN_AURA, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
        }
		return false;
	}

	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return 6000; // 5 min
	}
	
}
