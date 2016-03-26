package gameragi.darksouls.bonfire.init;

import gameragi.darksouls.bonfire.Reference;
import gameragi.darksouls.bonfire.blocks.BlockBonfire;
import gameragi.darksouls.bonfire.tileentities.TileEntityBonfire;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {

	public static Block bonfire_block;

	public static void init() {
		bonfire_block = new BlockBonfire().setUnlocalizedName("bonfire_block");
	}

	public static void register() {
		GameRegistry.registerBlock(bonfire_block, bonfire_block.getUnlocalizedName().substring(5));
		GameRegistry.registerTileEntity(TileEntityBonfire.class, Reference.MOD_ID + ":" + "bonfire_tile_entity");
	}

	public static void registerRenders() {
		registerRender(bonfire_block);
	}

	public static void registerRender(Block block) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
