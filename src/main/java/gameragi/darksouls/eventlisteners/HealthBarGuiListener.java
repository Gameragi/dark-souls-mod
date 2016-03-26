package gameragi.darksouls.eventlisteners;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HealthBarGuiListener extends Gui {

	private Minecraft mc;
	private int healthBar_width = 232;
	private int healthBar_height = 10;

	private int healthBar_x = 60;
	private int healthBar_y = 15;

	private int healthBack_y = 33;
	private int healthCurrent_y = 20;
	private int healthOverlay_y = 7;

	public HealthBarGuiListener(Minecraft mc) {
		super();

		this.mc = mc;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(RenderGameOverlayEvent event) {
		ResourceLocation healthBarLoc = new ResourceLocation("bm:textures/ui/health_bar.png");
		ResourceLocation defaultLoc = new ResourceLocation("minecraft:textures/gui/icons.png");

		float playerHealth = mc.thePlayer.getHealth();
		float playerMaxHealth = mc.thePlayer.getMaxHealth();

		GL11.glEnable(GL11.GL_BLEND);
		this.mc.renderEngine.bindTexture(healthBarLoc);
		// draw health bar background
		drawTexturedModalRect(healthBar_x, healthBar_y, 5, healthBack_y, healthBar_width, healthBar_height);

		// draw health bar
		drawTexturedModalRect(healthBar_x, healthBar_y, 5, healthCurrent_y, Math.round((playerHealth / playerMaxHealth) * healthBar_width), healthBar_height);

		// draw health bar overlay
		drawTexturedModalRect(healthBar_x, healthBar_y, 5, healthOverlay_y, healthBar_width, healthBar_height);

		GL11.glDisable(GL11.GL_BLEND);
		this.mc.renderEngine.bindTexture(defaultLoc);
	}
}
