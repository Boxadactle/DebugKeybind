package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GamemodeSwitcherScreenMixin {

    @Shadow public abstract boolean keyPressed(int i, int j, int k);

    @ModifyArg(
            method = "checkToClose",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z"
            ),
            index = 1
    )
    private int overrideF3Close(int p_84832_) {
        return DebugKeybinds.DEBUG.getKeyCode();
    }

    // please PR if you can think of a bettter way to do this lmao
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void overrideF4Press(int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        int l = DebugKeybinds.OPEN_GAMEMODE_SWITCHER.getKeyCode();

        // check if key is keybind and keybind is not default (F4)
        // if false, it will run the original method, otherwise it will
        // run the original method but change the keycode arg to the code for F4
        if (i == l && l != 293) {
            cir.setReturnValue(keyPressed(293, j, k));
            DebugKeybindMain.LOGGER.info("Intercepted the keypressed method and made it think we pressed F4");
        }
    }

}
