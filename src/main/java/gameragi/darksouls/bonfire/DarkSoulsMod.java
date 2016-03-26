package gameragi.darksouls.bonfire;

import gameragi.darksouls.SoulsEntity;
import gameragi.darksouls.bonfire.init.Blocks;
import gameragi.darksouls.bonfire.init.Items;
import gameragi.darksouls.bonfire.proxy.CommonProxy;
import gameragi.darksouls.estus.EstusKeybind;
import gameragi.darksouls.estus.KeyInputHandler;
import gameragi.darksouls.eventlisteners.DeathListener;
import gameragi.darksouls.eventlisteners.EstusGuiListener;
import gameragi.darksouls.eventlisteners.HealthBarGuiListener;
import gameragi.darksouls.eventlisteners.HumanityGuiListener;
import gameragi.darksouls.eventlisteners.ItemPickupListener;
import gameragi.darksouls.eventlisteners.PlayerEventListener;
import gameragi.darksouls.eventlisteners.PlayerRespawnListener;
import gameragi.darksouls.eventlisteners.SoulsGuiListener;
import gameragi.darksouls.servermessages.PlayerEstusMessage;
import gameragi.darksouls.servermessages.PlayerHealMessage;
import gameragi.darksouls.servermessages.PlayerHumanityMessage;
import gameragi.darksouls.servermessages.PlayerSoulMessage;
import gameragi.darksouls.servermessages.PlayerSpawnpointMessage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class DarkSoulsMod {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper network;

	@Instance(Reference.MOD_ID)
	public static DarkSoulsMod instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Blocks.init();
		Blocks.register();
		Items.init();
		Items.register();
		registerEntity(SoulsEntity.class, "soulsEntity");

		FMLCommonHandler.instance().bus().register(new KeyInputHandler(Minecraft.getMinecraft()));
		EstusKeybind.init();
		network = NetworkRegistry.INSTANCE.newSimpleChannel("soulsChannel");
		network.registerMessage(PlayerHealMessage.Handler.class, PlayerHealMessage.class, 0, Side.SERVER);
		network.registerMessage(PlayerSpawnpointMessage.Handler.class, PlayerSpawnpointMessage.class, 1, Side.SERVER);
		network.registerMessage(PlayerSoulMessage.Handler.class, PlayerSoulMessage.class, 2, Side.SERVER);

		network.registerMessage(PlayerHumanityMessage.HandlerOnServer.class, PlayerHumanityMessage.class, 3, Side.SERVER);
		network.registerMessage(PlayerHumanityMessage.HandlerOnClient.class, PlayerHumanityMessage.class, 4, Side.CLIENT);

		network.registerMessage(PlayerEstusMessage.HandlerOnServer.class, PlayerEstusMessage.class, 5, Side.SERVER);
		network.registerMessage(PlayerEstusMessage.HandlerOnClient.class, PlayerEstusMessage.class, 6, Side.CLIENT);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenders();
		MinecraftForge.EVENT_BUS.register(new PlayerRespawnListener());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EstusGuiListener(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new HumanityGuiListener(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new HealthBarGuiListener(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new SoulsGuiListener(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new PlayerEventListener());
		MinecraftForge.EVENT_BUS.register(new DeathListener());
		MinecraftForge.EVENT_BUS.register(new ItemPickupListener());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void registerEntity(Class entityClass, String name) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
		EntityRegistry.registerModEntity(entityClass, name, entityID, instance, 64, 1, true);
	}
}
