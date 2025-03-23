package dev.boxadactle.debugkeybind.fabric;

import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return DebugKeybindsScreen::new;
    }

}
