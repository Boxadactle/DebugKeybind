package dev.boxadactle.debugkeybind.neoforge;

import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(DebugKeybindMain.MOD_ID)
public class DebugKeybindNeoforge {
    public DebugKeybindNeoforge() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () ->
                (minecraft, screen) -> new DebugKeybindsScreen(screen)
        );
    }

    @EventBusSubscriber(modid = DebugKeybindMain.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModNeoforgeEvents {
        @SubscribeEvent
        public static void init(FMLClientSetupEvent e) {
            DebugKeybindMain.init();
        }
    }
}