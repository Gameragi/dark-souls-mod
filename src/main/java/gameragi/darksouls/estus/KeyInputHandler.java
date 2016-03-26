package gameragi.darksouls.estus;

import java.util.List;

import gameragi.darksouls.ExtendedPlayer;
import gameragi.darksouls.bonfire.tileentities.TileEntityBonfire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
	
	private Minecraft mc;
	private ExtendedPlayer player;
	
	public KeyInputHandler(Minecraft mc){
		this.mc = mc;
	}

	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(EstusKeybind.estus.isPressed()){
        	player = ExtendedPlayer.get(mc.thePlayer);
    	    if(player.drinkEstus()){
    	    	ResourceLocation soundLoc = new ResourceLocation("random.fizz");
    	    	PositionedSoundRecord sound = new PositionedSoundRecord(soundLoc, 0.4F, 0.3F, mc.thePlayer.getPosition().getX(), mc.thePlayer.getPosition().getY(), mc.thePlayer.getPosition().getZ());
    	    	mc.getSoundHandler().playSound(sound);
    	    }
        }
        
        if(EstusKeybind.kindle.isPressed()){
        	System.out.println("kindle key was pressed");
        	List<TileEntity> loadedEntities = mc.theWorld.loadedTileEntityList;
        	EntityPlayer player = mc.thePlayer;
        	for(TileEntity entity:loadedEntities){
        		if(entity instanceof TileEntityBonfire && entity.getDistanceSq(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()) <= 4F){
        			System.out.println("bonfire entity detected in kindle range");
        			if(((TileEntityBonfire)entity).kindleBonfire(player)){
        				System.out.println("kindling bonfire successful");
        			}
        		}
        	}
        }
    }
}
