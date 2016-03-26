package gameragi.darksouls.eventlisteners;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoulsGuiListener extends Gui {

	private Minecraft mc;
	private int soulsBack_width = 49;
	private int soulsBack_height = 12;
	private int fontColor = 0xFFFFFF;

	public SoulsGuiListener(Minecraft mc) {
		super();

		this.mc = mc;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(RenderGameOverlayEvent event) {
		ResourceLocation soulsBack = new ResourceLocation("bm:textures/ui/souls_back.png");
		ResourceLocation defaultLoc = new ResourceLocation("minecraft:textures/gui/icons.png");
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		FontRenderer fontRender = mc.fontRendererObj;

		int experienceTotal = mc.thePlayer.experienceTotal;
		experienceTotal *= 10;

		int xpCopy = experienceTotal;
		int numberOfDigits = 0;
		do {
			numberOfDigits++;
			xpCopy /= 10;
		} while (xpCopy > 0);

		int soulsCount_x = resolution.getScaledWidth() - 40 - numberOfDigits * 6;
		int soulsCount_y = resolution.getScaledHeight() - 20;

		GL11.glEnable(GL11.GL_BLEND);

		this.mc.renderEngine.bindTexture(soulsBack);
		drawTexturedModalRect(resolution.getScaledWidth() - 85, resolution.getScaledHeight() - 22, 0, 0, soulsBack_width, soulsBack_height);

		String text = Integer.toString(experienceTotal);

		fontRender.drawStringWithShadow(text, soulsCount_x, soulsCount_y, fontColor);
		GL11.glDisable(GL11.GL_BLEND);
		this.mc.renderEngine.bindTexture(defaultLoc);
	}

}
