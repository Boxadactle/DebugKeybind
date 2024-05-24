package dev.boxadactle.debugkeybind.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.Util;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow private boolean handledDebugKey;

    @Shadow @Final private Minecraft minecraft;

    @Shadow private long debugCrashKeyTime;

    @Shadow protected abstract void debugFeedbackTranslated(String string, Object... objects);

    @ModifyConstant(
            method = "keyPress",
            constant = @Constant(intValue = 292)
    )
    private int overrideDebugKey(int value) {
        return DebugKeybinds.DEBUG.getKeyCode();
    }


    @ModifyConstant(
            method = "keyPress",
            constant = @Constant(intValue = 256)
    )
    private int handleF3Escape(int value) {
        if (!InputConstants.isKeyDown(ClientUtils.getWindow(), DebugKeybinds.DEBUG.getKeyCode())) {
            return 256;
        }

        return DebugKeybinds.PAUSE_WITHOUT_MENU.getKeyCode();
    }

    @ModifyConstant(
            method = "keyPress",
            constant = @Constant(intValue = 290)
    )
    private int handleF1(int value) {
        return DebugKeybinds.HIDE_GUI.getKeyCode();
    }

    @ModifyArg(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyboardHandler;handleDebugKeys(I)Z"
            )
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
