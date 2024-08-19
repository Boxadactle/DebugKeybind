package dev.boxadactle.debugkeybind.forge;

import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(DebugKeybindMain.MOD_ID)
public class DebugKeybindForge {

    public DebugKeybindForge() {
        DebugKeybindMain.init();

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () ->
                (minecraft, screen) -> new DebugKeybindsScreen(screen)
        );
    }

}
