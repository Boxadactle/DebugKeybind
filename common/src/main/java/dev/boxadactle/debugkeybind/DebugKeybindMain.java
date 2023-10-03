package dev.boxadactle.debugkeybind;

import dev.boxadactle.boxlib.config.BConfigClass;
import dev.boxadactle.boxlib.config.BConfigHandler;
import dev.boxadactle.boxlib.util.ModLogger;
import dev.boxadactle.debugkeybind.keybind.KeybindConfig;
import net.minecraft.client.KeyMapping;

public class DebugKeybindMain
{
	public static final String MOD_NAME = "DebugKeybind";

	public static final String MOD_ID = "debugkeybind";

	public static final String VERSION = "6.0.0";

	public static final String VERSION_STRING = MOD_NAME + " v" + VERSION;

	public static final ModLogger LOGGER = new ModLogger(MOD_NAME);

	public static BConfigClass<KeybindConfig> CONFIG;

	public static void init() {

		CONFIG = BConfigHandler.registerConfig(KeybindConfig.class);

	}

	public static boolean shouldBeRegistered(KeyMapping k) {
		return shouldBeRegistered(k.getCategory());
	}

	public static boolean shouldBeRegistered(String c) {
		if (c.equals("key.categories.debug")) return false;
		if (c.equals("key.categories.debug_actions")) return false;

		return true;
	}

}
