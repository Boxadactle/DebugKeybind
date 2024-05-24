package dev.boxadactle.debugkeybind.neoforge;

import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(DebugKeybindMain.MOD_ID)
public class DebugKeybindNeoforge {
    public DebugKeybindNeoforge() {
        DebugKeybindMain.init();

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () ->
                (minecraft, screen) -> new DebugKeybindsScreen(screen)
        );
    }
}