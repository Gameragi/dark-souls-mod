package gameragi.darksouls.eventlisteners;

import org.lwjgl.opengl.GL11;

import gameragi.darksouls.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EstusGuiListener extends Gui {

	private ExtendedPlayer player;
	private Minecraft mc;
	private int estus_width = 40;
	private int estus_height = 40;
	private int fontColor = 0xFFFFFF;

	public EstusGuiListener(Minecraft mc) {
		super();

		this.mc = mc;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(RenderGameOverlayEvent event) {
		ResourceLocation estusLoc = new ResourceLocation("bm:textures/ui/estus2.png");
		ResourceLocation emptyLoc = new ResourceLocation("bm:textures/ui/estus2_empty.png");
		ResourceLocation defaultLoc = new ResourceLocation("minecraft:textures/gui/icons.png");
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		FontRenderer fontRender = mc.fontRendererObj;

		// int estus_x = resolution.getScaledWidth() / 2 + 145;
		int estus_x = 20;
		int estus_y = resolution.getScaledHeight() - 50;

		player = ExtendedPlayer.get(mc.thePlayer);
		int estusCount = player.getCurrentEstusCount();

		GL11.glEnable(GL11.GL_BLEND);
		if (estusCount == 0) {
			this.mc.renderEngine.bindTexture(emptyLoc);
		} else {
			this.mc.renderEngine.bindTexture(estusLoc);
		}
		drawTexturedModalRect(estus_x, estus_y, 0, 0, estus_width, estus_height);

		String text = Integer.toString(estusCount);

		fontRender.drawStringWithShadow(text, estus_x + estus_width, estus_y + 30, fontColor);
		GL11.glDisable(GL11.GL_BLEND);
		this.mc.renderEngine.bindTexture(defaultLoc);
	}

	// remove food bar, health bar, and cross hair
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderFoodBar(RenderGameOverlayEvent.Pre event) {
		if (event.type == ElementType.FOOD || event.type == ElementType.HEALTH || event.type == ElementType.CROSSHAIRS || event.type == ElementType.EXPERIENCE || event.type == ElementType.ARMOR) {
			event.setCanceled(true);
		}
		return;
	}
}
