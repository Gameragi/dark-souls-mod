package gameragi.darksouls.bonfire.proxy;

import gameragi.darksouls.bonfire.init.Blocks;
import gameragi.darksouls.bonfire.init.Items;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenders(){
		Blocks.registerRenders();
		Items.registerRenders();
	}
}
