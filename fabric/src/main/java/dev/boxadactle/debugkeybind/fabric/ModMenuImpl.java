package dev.boxadactle.debugkeybind.fabric;

import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Function;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return DebugKeybindsScreen::new;
    }

    @Override
    public String getModId() {
        return DebugKeybindMain.MOD_ID;
    }
}
