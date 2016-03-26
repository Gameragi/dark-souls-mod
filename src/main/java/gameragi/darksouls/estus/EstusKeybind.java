package gameragi.darksouls.estus;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class EstusKeybind {

	// Declare KeyBinding
	public static KeyBinding estus;
	public static KeyBinding kindle;

	public static void init() {
		estus = new KeyBinding("Drink Estus Flask", Keyboard.KEY_F, "key.categories.gameplay");
		kindle = new KeyBinding("Kindle Bonfire", Keyboard.KEY_R, "key.categories.gameplay");

		// Register KeyBinding to the ClientRegistry
		ClientRegistry.registerKeyBinding(estus);
		ClientRegistry.registerKeyBinding(kindle);
	}
}
