package gameragi.darksouls;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class SoulsEntity extends EntityXPOrb {

	private EntityPlayer player;

	public SoulsEntity(EntityPlayer player, World worldIn, double x, double y, double z, int expValue) {
		super(worldIn);
		this.setPosition(x, y, z);
		this.setSize(0.5F, 0.5F);
		this.xpValue = expValue;
		this.player = player;
	}

	@Override
	public void onUpdate() {
		// super.onUpdate();
		spawnParticles(Minecraft.getMinecraft().theWorld, this.getPosition());

	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		if (!this.worldObj.isRemote && entityIn.getName().equals(this.player.getName())) {
			this.worldObj.playSoundAtEntity(entityIn, "random.orb", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
			entityIn.onItemPickup(this, 1);
			entityIn.addExperience(this.xpValue);
			this.setDead();
		}
	}

	private void spawnParticles(World worldIn, BlockPos pos) {
		Random random = worldIn.rand;

		for (int i = 0; i < 6; ++i) {
			double d1 = (double) ((float) pos.getX() + 0.45 + random.nextFloat() * (0.1));
			double d2 = (double) ((float) pos.getY() + 0.2 + random.nextFloat() * (0.3));
			double d3 = (double) ((float) pos.getZ() + 0.45 + random.nextFloat() * (0.1));

			worldIn.spawnParticle(EnumParticleTypes.FLAME, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

}
