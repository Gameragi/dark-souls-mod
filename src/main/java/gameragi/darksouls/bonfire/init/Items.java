package gameragi.darksouls.bonfire.init;

import gameragi.darksouls.bonfire.Reference;
import gameragi.darksouls.bonfire.items.HumanityItem;
import gameragi.darksouls.bonfire.items.SoulsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Items {
	
	public static Item souls;
	public static Item humanity;
	
	public static void init(){
		souls = new SoulsItem();
		humanity = new HumanityItem().setMaxStackSize(64).setCreativeTab(CreativeTabs.tabFood).setUnlocalizedName("humanity");;
	}
	
	public static void register(){
		GameRegistry.registerItem(souls,souls.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(humanity,humanity.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders(){
		registerRender(souls);
		registerRender(humanity);
	}
	
	public static void registerRender(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
