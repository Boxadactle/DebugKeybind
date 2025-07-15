package dev.boxadactle.debugkeybind.fabric;

import dev.boxadactle.debugkeybind.DebugKeybindMain;
import net.fabricmc.api.ClientModInitializer;

public class DebugKeybindFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DebugKeybindMain.init();
    }
}