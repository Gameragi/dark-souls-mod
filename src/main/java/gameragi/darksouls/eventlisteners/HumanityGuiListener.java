package gameragi.darksouls.eventlisteners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import gameragi.darksouls.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HumanityGuiListener extends Gui{
	
	private Minecraft mc;
	private ExtendedPlayer player;
	private int humanityGui_width = 50;
	private int humanityGui_height = 50;
	private int fontColor = 0xFFFFFF;
	private int numberHeight = 26;
	private int numberWidth = 19;
	private int humanityGui_x = 10;
    private int humanityGui_y = 10;
	private Integer[][] numberPositions = new Integer[10][2];
	
	public HumanityGuiListener(Minecraft mc){
		super();
		for(int i = 0;i<10;i++){
			numberPositions[i][0] = 3 + i*23;
			numberPositions[i][1] = 3;
		}

		this.mc = mc;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void eventHandler(RenderGameOverlayEvent event){
		ResourceLocation humanityGuiLoc = new ResourceLocation("bm:textures/ui/humanity_gui.png");
		ResourceLocation humanityGuiFontLoc = new ResourceLocation("bm:textures/ui/humanity_gui_font.png");
		ResourceLocation defaultLoc = new ResourceLocation("minecraft:textures/gui/icons.png");
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		FontRenderer fontRender = mc.fontRendererObj;
		fontRender.FONT_HEIGHT = 10;
		
		int humanityCount = ExtendedPlayer.get(mc.thePlayer).getHumanity();
	    
	    GL11.glEnable(GL11.GL_BLEND);
    	this.mc.renderEngine.bindTexture(humanityGuiLoc);
	    drawTexturedModalRect(humanityGui_x,humanityGui_y, 0, 0, humanityGui_width, humanityGui_height);

	    GL11.glDisable(GL11.GL_BLEND);
	    renderNumbers(humanityCount,humanityGuiFontLoc);
	    
	    this.mc.renderEngine.bindTexture(defaultLoc);
	}
	
	private void renderNumbers(int value,ResourceLocation loc){
		int secondDigit = value % 10;
		value = value/10;
		int firstDigit = value % 10;
		
		this.mc.renderEngine.bindTexture(loc);
		drawTexturedModalRect(humanityGui_x + 7,humanityGui_y + 12, numberPositions[firstDigit][0], numberPositions[firstDigit][1], numberWidth, numberHeight);
		drawTexturedModalRect(humanityGui_x + humanityGui_width - numberWidth - 7,humanityGui_y + 12, numberPositions[secondDigit][0], numberPositions[secondDigit][1], numberWidth, numberHeight);
	}

}
