package dev.boxadactle.debugkeybind.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.boxlib.scheduling.Scheduling;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.keybind.ActionKeybind;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import dev.boxadactle.debugkeybind.keybind.GlobalKeybind;
import net.minecraft.Util;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow private long debugCrashKeyTime;

    @Shadow protected abstract boolean handleChunkDebugKeys(int i);

    @Shadow protected abstract void debugFeedbackTranslated(String string);

    @Shadow protected abstract boolean handleDebugKeys(int key);

    @Shadow private boolean handledDebugKey;

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

    @Inject(
            method = "keyPress",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/KeyboardHandler;handledDebugKey:Z",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER
            )
    )
    public void activateWithoutDebugKey(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (!DebugKeybindMain.CONFIG.get().requireDebugKey) {
            handledDebugKey = handleDebugKeys(DebugKeybinds.remapActionKey(key));
        }
    }

    @ModifyArg(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyboardHandler;handleDebugKeys(I)Z"
            )
    )
    private int remapDebugKeys(int i) {
        return DebugKeybindMain.CONFIG.get().requireDebugKey ? DebugKeybinds.remapActionKey(i) : -1;
    }

    // have to override help keybind
    @Inject(
            method = "handleDebugKeys",
            at = @At("HEAD"),
            cancellable = true
    )
    private void overrideHelpMenuAndAddChunkKeys(int i, CallbackInfoReturnable<Boolean> cir) {
        if (!(this.debugCrashKeyTime > 0L && this.debugCrashKeyTime < Util.getMillis() - 100L)) {
            if (i == 81) {
                debugFeedbackTranslated("debug.help.message");

                // make it look nice lol0
                ChatComponent chatComponent = this.minecraft.gui.getChat();
                chatComponent.addMessage(GuiUtils.colorize(GuiUtils.surround(
                        "------------| ",
                        " |------------",
                        GuiUtils.brackets(GuiUtils.colorize(Component.translatable("controls.keybinds.debug.title"), GuiUtils.BLUE))
                ), GuiUtils.GOLD));
                DebugKeybinds.createHelpComponents().forEach(chatComponent::addMessage);
                chatComponent.addMessage(GuiUtils.colorize(Component.literal("----------------------------"), GuiUtils.GOLD));

                cir.setReturnValue(true);
            } else if (i == 88) {
                Scheduling.nextTick(() -> {
                    DebugKeybindMain.CONFIG.get().requireDebugKey = !DebugKeybindMain.CONFIG.get().requireDebugKey;
                    DebugKeybindMain.CONFIG.save();
                    debugFeedbackTranslated("debug.toggle_debug_key");
                });

                cir.setReturnValue(true);
            } else {
                boolean bl = handleChunkDebugKeys(i);
                if (bl) cir.setReturnValue(true);
            }
        }
    }
}
