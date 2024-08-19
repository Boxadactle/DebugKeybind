package dev.boxadactle.debugkeybind.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.Util;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

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

            Component debugKey = DebugKeybinds.DEBUG.getKeyTranslation();

            ChatComponent chatComponent = this.minecraft.gui.getChat();
            chatComponent.addMessage(new TranslatableComponent("debug.reload_chunks.help", debugKey, DebugKeybinds.RELOAD_CHUNKS.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.show_hitboxes.help", debugKey, DebugKeybinds.SHOW_HITBOXES.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.copy_location.help", debugKey, DebugKeybinds.COPY_LOCATION.getKeyTranslation(), debugKey, "C"));
            chatComponent.addMessage(new TranslatableComponent("debug.cycle_renderdistance.help", debugKey, DebugKeybinds.CYCLE_RENDER_DISTANCE.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.clear_chat.help", debugKey, DebugKeybinds.CLEAR_CHAT.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.chunk_boundaries.help", debugKey, DebugKeybinds.CHUNK_BORDERS.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.advanced_tooltips.help", debugKey, DebugKeybinds.ADVANCED_TOOLTIPS.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.inspect.help", debugKey, DebugKeybinds.INSPECT.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.profiling.help", debugKey, DebugKeybinds.PROFILING.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.creative_spectator.help", debugKey, DebugKeybinds.CREATIVE_SPECTATOR.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.pause_focus.help", debugKey, DebugKeybinds.PAUSE_FOCUS.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.help.help", debugKey, DebugKeybinds.HELP.getKeyTranslation()));
//            chatComponent.addMessage(new TranslatableComponent("debug.dump_dynamic_textures.help", debugKey, DebugKeybinds.DUMP_DYNAMIC_TEXTURES.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.reload_resourcepacks.help", debugKey, DebugKeybinds.RELOAD_RESOURCEPACKS.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.pause.help", debugKey, DebugKeybinds.PAUSE_WITHOUT_MENU.getKeyTranslation()));
            chatComponent.addMessage(new TranslatableComponent("debug.gamemodes.help", debugKey, DebugKeybinds.OPEN_GAMEMODE_SWITCHER.getKeyTranslation()));
            cir.setReturnValue(true);
        }
    }
}
