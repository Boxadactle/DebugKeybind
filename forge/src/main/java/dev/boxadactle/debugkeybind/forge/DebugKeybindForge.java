package dev.boxadactle.debugkeybind.forge;

import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(DebugKeybindMain.MOD_ID)
public class DebugKeybindForge {

    public DebugKeybindForge() {
        DebugKeybindMain.init();

        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () ->
                new ConfigGuiHandler.ConfigGuiFactory((minecraft, screen) -> new DebugKeybindsScreen(screen))
        );
    }

}
