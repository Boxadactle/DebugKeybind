package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBindsScreen.class)
public class KeybindsScreenMixin extends OptionsSubScreen {

    public KeybindsScreenMixin(Screen lastScreen, Options options, Component title) {
        super(lastScreen, options, title);
    }

    @Inject(
            method = "addContents",
            at = @At("RETURN")
    )
    public void addButton(CallbackInfo ci) {
        addRenderableWidget(Button.builder(Component.translatable("key.categories.debug"), b -> ClientUtils.setScreen(new DebugKeybindsScreen(lastScreen))).bounds(width - 77, 5, 75, 20).build());
    }

    @Override
    protected void addOptions() {

    }

    @Override
    public void onClose() {
        super.onClose();
        DebugKeybindMain.CONFIG.save();
    }
}
