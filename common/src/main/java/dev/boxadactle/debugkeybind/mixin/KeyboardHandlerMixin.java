package dev.boxadactle.debugkeybind.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.Util;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow private boolean handledDebugKey;

    @Shadow @Final private Minecraft minecraft;

    @Shadow private long debugCrashKeyTime;

    @Shadow protected abstract void debugFeedbackTranslated(String string, Object... objects);

    @ModifyArg(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z",
                    ordinal = 0
            ),
            index = 1
    )
    private int modifyKey(int i) {
        return DebugKeybinds.DEBUG.getKeyCode();
    }

    @Inject(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(II)Lcom/mojang/blaze3d/platform/InputConstants$Key;",
                    ordinal = 0
            )
    )
    private void handleExtraKeys(long l, int i, int j, int k, int m, CallbackInfo ci) {
        if (k != 0) {
            handleF3Escape(i);
            overrideF1(i);
        }
    }

    @Unique
    private void handleF3Escape(int i) {
        // check if the current screen is null so the game doesnt pause immediately
        // after being unpaused with escape
        if (ClientUtils.getCurrentScreen() == null && i == DebugKeybinds.PAUSE_WITHOUT_MENU.getKeyCode()) {
            boolean flag2 = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), DebugKeybinds.DEBUG.getKeyCode());
            this.minecraft.pauseGame(flag2);
        }
    }

    @Unique
    private void overrideF1(int i) {
        // we check if the keycode isnt F1, as the original F1 is still hard-coded
        // We don't need to handle the menu if it is f1, because minecraft will do it for us
        if (!DebugKeybinds.HIDE_GUI.isDefault()) {
            if (i == DebugKeybinds.HIDE_GUI.getKeyCode()) {
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
        if (!DebugKeybinds.DEBUG.isDefault()) {
            if (i == DebugKeybinds.DEBUG.getKeyCode()) {
                toggleDebugScreen(true);
            }

            // now to check if the key pressed was F3, so we can open the menu (to be closed by the hard-code).
            // The keybind is not bound to F3, so the menu should not open
            else if(i == 292) {
                toggleDebugScreen(false);
            }
        }
    }

    @Unique
    private void toggleDebugScreen(boolean bl) {
        if (this.handledDebugKey) {
            if (bl) this.handledDebugKey = false;
        } else {
            this.minecraft.getDebugOverlay().toggleOverlay();
        }
    }

    @ModifyArg(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyboardHandler;handleDebugKeys(I)Z",
                    ordinal = 0
            ),
            index = 0
    )
    private int remapDebugKeys(int i) {
        return DebugKeybinds.remapActionKey(i);
    }

    // have to override help keybind
    @Inject(
            method = "handleDebugKeys",
            at = @At("HEAD"),
            cancellable = true
    )
    private void overrideHelpMenu(int i, CallbackInfoReturnable<Boolean> cir) {
        if (!(this.debugCrashKeyTime > 0L && this.debugCrashKeyTime < Util.getMillis() - 100L) && i == 81) {
            this.debugFeedbackTranslated("debug.help.message");

            Component debugKey = DebugKeybinds.DEBUG.getTranslatedKey();

            ChatComponent chatComponent = this.minecraft.gui.getChat();
            chatComponent.addMessage(Component.translatable("debug.reload_chunks.help", debugKey, DebugKeybinds.RELOAD_CHUNKS.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.show_hitboxes.help", debugKey, DebugKeybinds.SHOW_HITBOXES.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.copy_location.help", debugKey, DebugKeybinds.COPY_LOCATION.getTranslatedKey(), debugKey, "C"));
            chatComponent.addMessage(Component.translatable("debug.clear_chat.help", debugKey, DebugKeybinds.CLEAR_CHAT.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.chunk_boundaries.help", debugKey, DebugKeybinds.CHUNK_BORDERS.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.advanced_tooltips.help", debugKey, DebugKeybinds.ADVANCED_TOOLTIPS.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.inspect.help", debugKey, DebugKeybinds.INSPECT.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.profiling.help", debugKey, DebugKeybinds.PROFILING.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.creative_spectator.help", debugKey, DebugKeybinds.CREATIVE_SPECTATOR.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.pause_focus.help", debugKey, DebugKeybinds.PAUSE_FOCUS.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.help.help", debugKey, DebugKeybinds.HELP.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.dump_dynamic_textures.help", debugKey, DebugKeybinds.DUMP_DYNAMIC_TEXTURES.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.reload_resourcepacks.help", debugKey, DebugKeybinds.RELOAD_RESOURCEPACKS.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.pause.help", debugKey, DebugKeybinds.PAUSE_WITHOUT_MENU.getTranslatedKey()));
            chatComponent.addMessage(Component.translatable("debug.gamemodes.help", debugKey, DebugKeybinds.OPEN_GAMEMODE_SWITCHER.getTranslatedKey()));
            cir.setReturnValue(true);
        }
    }
}
