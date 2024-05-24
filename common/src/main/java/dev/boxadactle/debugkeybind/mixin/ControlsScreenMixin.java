package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public class ControlsScreenMixin extends OptionsSubScreen {
    @Shadow @Nullable private OptionsList list;

    public ControlsScreenMixin(Screen screen, Options options, Component component) {
        super(screen, options, component);
    }

    @Inject(
            method = "init",
            at = @At("RETURN")
    )
    private void addDebugButton(CallbackInfo ci) {
        list.addSmall(Button.builder(Component.translatable("controls.keybinds.debug"), (p_280844_) -> {
            Screen s = ClientUtils.getCurrentScreen();
            ClientUtils.setScreen(new DebugKeybindsScreen(s));
        }).build(), null);
    }

//    @ModifyArg(
//            method = "init",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/gui/components/Button$Builder;bounds(IIII)Lnet/minecraft/client/gui/components/Button$Builder;",
//                    ordinal = 2
//            ),
//            index = 1
//    )
//    private int moveDoneButton(int i) {
//        return i + 24;
//    }

}
