package dev.boxadactle.debugkeybind;

import dev.boxadactle.boxlib.config.BConfigClass;
import dev.boxadactle.boxlib.config.BConfigHandler;
import dev.boxadactle.boxlib.util.ModLogger;
import dev.boxadactle.debugkeybind.keybind.KeybindConfig;
import net.minecraft.client.resources.language.I18n;

public class DebugKeybindMain {
	public static final String MOD_NAME = "DebugKeybind";

	public static final String MOD_ID = "debugkeybind";

	public static final String VERSION = "1.2.0";

	public static final String VERSION_STRING = MOD_NAME + " v" + VERSION;

	public static final ModLogger LOGGER = new ModLogger(MOD_NAME);

	public static BConfigClass<KeybindConfig> CONFIG;

	public static void init() {
		LOGGER.info("Initializing " + VERSION_STRING + "...");

		CONFIG = BConfigHandler.registerConfig(KeybindConfig.class);

//		BCommandManager.register(F3Command.create());
	}

}
