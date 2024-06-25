package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin extends OptionsSubScreen {

    public ControlsScreenMixin(Screen screen, Options options, Component component) {
        super(screen, options, component);
    }

    @Inject(
            method = "addOptions",
            at = @At("RETURN")
    )
    private void addDebugButton(CallbackInfo ci) {
        list.addSmall(Button.builder(Component.translatable("controls.keybinds.debug"), (p_280844_) -> {
            Screen s = ClientUtils.getCurrentScreen();
            ClientUtils.setScreen(new DebugKeybindsScreen(s));
        }).build(), null);
    }

}
