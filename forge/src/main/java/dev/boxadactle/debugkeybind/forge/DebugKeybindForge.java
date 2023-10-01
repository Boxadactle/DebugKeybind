package dev.boxadactle.debugkeybind.forge;

import dev.boxadactle.debugkeybind.core.DebugKeybindMain;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(DebugKeybindMain.MOD_ID)
public class DebugKeybindForge {
    public DebugKeybindForge() {
        DebugKeybindMain.init();

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
            return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
                return new DebugKeybindsScreen(screen);
            });
        });
    }
}