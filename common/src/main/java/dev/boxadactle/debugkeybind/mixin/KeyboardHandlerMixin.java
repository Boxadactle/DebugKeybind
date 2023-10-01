package dev.boxadactle.debugkeybind.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.debugkeybind.DebugKeybind;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow private boolean handledDebugKey;

    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract boolean handleDebugKeys(int i);

    @ModifyArg(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z",
                    ordinal = 1
            ),
            index = 1
    )
    private int modifyKey(int i) {
        return DebugKeybind.DEBUG.getKeyCode();
    }

    @ModifyArg(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z",
                    ordinal = 3
            ),
            index = 1
    )
    private int modifyKey2(int i) {
        return DebugKeybind.DEBUG.getKeyCode();
    }

    @ModifyArg(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z",
                    ordinal = 4
            ),
            index = 1
    )
    private int modifyKey4(int i) {
        return DebugKeybind.DEBUG.getKeyCode();
    }

    @ModifyArg(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z",
                    ordinal = 5
            ),
            index = 1
    )
    private int modifyKey5(int i) {
        return DebugKeybind.DEBUG.getKeyCode();
    }

    @Inject(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z",
                    ordinal = 5
            )
    )
    private void handleF3Escape(long h, int i, int j, int k, int l, CallbackInfo ci) {
        if (i == DebugKeybind.PAUSE_WITHOUT_MENU.getKeyCode()) {
            boolean flag2 = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), DebugKeybind.DEBUG.getKeyCode());
            this.minecraft.pauseGame(flag2);
        }
    }

    @Inject(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z",
                    ordinal = 5
            )
    )
    private void overrideF1(long l, int i, int j, int k, int m, CallbackInfo ci) {
        // we check if the keycode isnt F1, as the original F1 is still hard-coded
        // We don't need to handle the menu if it is f1, because minecraft will do it for us
        if (!DebugKeybind.HIDE_GUI.isDefault()) {
            if (i == DebugKeybind.HIDE_GUI.getKeyCode()) {
                this.minecraft.options.hideGui = !this.minecraft.options.hideGui;
            }

            // now to check if the key pressed was F1, so we can hide the menu (to be revealed by the hard-code).
            // The keybind is not bound to F1, so the menu should not open
            else if(i == 290) {
                this.minecraft.options.hideGui = !this.minecraft.options.hideGui;
            }
        }
    }

    @Inject(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyMapping;set(Lcom/mojang/blaze3d/platform/InputConstants$Key;Z)V",
                    ordinal = 0
            )
    )
    private void checkKey(long l, int i, int j, int k, int m, CallbackInfo ci) {
        // we check if the keycode isnt F3, as the original F3 is still hard-coded
        // We don't need to handle the menu if it is f3, because minecraft will do it for us
        if (!DebugKeybind.DEBUG.isDefault()) {
            if (i == DebugKeybind.DEBUG.getKeyCode()) {
                toggleDebugScreen(true);
            }

            // now to check if the key pressed was F3, so we can open the menu (to be closed by the hard-code).
            // The keybind is not bound to F3, so the menu should not open
            else if(i == 292) {
                toggleDebugScreen(false);
            }
        }
    }

    @Redirect(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyboardHandler;handleDebugKeys(I)Z"
            )
    )
    private boolean remapDebugKeys(KeyboardHandler instance, int i) {
        int code = DebugKeybind.remapActionKey(i);
        return code > 0 && handleDebugKeys(code);
    }

    @Unique
    private void toggleDebugScreen(boolean bl) {
        if (this.handledDebugKey) {
            if (bl) this.handledDebugKey = false;
        } else {
            this.minecraft.options.renderDebug = !this.minecraft.options.renderDebug;
            this.minecraft.options.renderDebugCharts = this.minecraft.options.renderDebug && Screen.hasShiftDown();
            this.minecraft.options.renderFpsChart = this.minecraft.options.renderDebug && Screen.hasAltDown();

        }
    }
}
