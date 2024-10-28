package dev.boxadactle.debugkeybind.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.keybind.ActionKeybind;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import dev.boxadactle.debugkeybind.keybind.GlobalKeybind;
import net.minecraft.Util;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow private long debugCrashKeyTime;

    @Shadow protected abstract void debugFeedbackTranslated(String string, Object... objects);

    @Shadow protected abstract boolean handleChunkDebugKeys(int i);

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
    private void overrideHelpMenuAndAddChunkKeys(int i, CallbackInfoReturnable<Boolean> cir) {
        if (!(this.debugCrashKeyTime > 0L && this.debugCrashKeyTime < Util.getMillis() - 100L)) {
            if (i == 81) {
                this.debugFeedbackTranslated("debug.help.message");

                GlobalKeybind debugKey = DebugKeybinds.DEBUG;

                ChatComponent chatComponent = this.minecraft.gui.getChat();
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.reload_chunks.help", debugKey, DebugKeybinds.RELOAD_CHUNKS));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.show_hitboxes.help", debugKey, DebugKeybinds.SHOW_HITBOXES));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.copy_location.help", debugKey, DebugKeybinds.COPY_LOCATION));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.clear_chat.help", debugKey, DebugKeybinds.CLEAR_CHAT));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.chunk_boundaries.help", debugKey, DebugKeybinds.CHUNK_BORDERS));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.advanced_tooltips.help", debugKey, DebugKeybinds.ADVANCED_TOOLTIPS));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.inspect.help", debugKey, DebugKeybinds.INSPECT));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.profiling.help", debugKey, DebugKeybinds.PROFILING));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.creative_spectator.help", debugKey, DebugKeybinds.CREATIVE_SPECTATOR));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.pause_focus.help", debugKey, DebugKeybinds.PAUSE_FOCUS));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.help.help", debugKey, DebugKeybinds.HELP));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.dump_dynamic_textures.help", debugKey, DebugKeybinds.DUMP_DYNAMIC_TEXTURES));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.reload_resourcepacks.help", debugKey, DebugKeybinds.RELOAD_RESOURCEPACKS));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.pause.help", debugKey, DebugKeybinds.PAUSE_WITHOUT_MENU));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.gamemodes.help", debugKey, DebugKeybinds.OPEN_GAMEMODE_SWITCHER));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.toggle_profiler_chart.help", debugKey, DebugKeybinds.TOGGLE_PROFILER_CHART));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.toggle_fps_charts.help", debugKey, DebugKeybinds.TOGGLE_FPS_CHARTS));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.toggle_network_charts.help", debugKey, DebugKeybinds.TOGGLE_NETWORK_CHARTS));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.toggle_fog.help", debugKey, DebugKeybinds.TOGGLE_FOG));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.toggle_smart_cull.help", debugKey, DebugKeybinds.TOGGLE_SMART_CULL));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.toggle_frustum_octree.help", debugKey, DebugKeybinds.TOGGLE_FRUSTUM_OCTREE));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.capture_frustum.help", debugKey, DebugKeybinds.CAPTURE_FRUSTUM));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.toggle_section_visibility.help", debugKey, DebugKeybinds.TOGGLE_SECTION_VISIBILITY));
                chatComponent.addMessage(debugKeybind$translateHelpMessage("debug.toggle_wireframe.help", debugKey, DebugKeybinds.TOGGLE_WIREFRAME));
                cir.setReturnValue(true);
            } else {
                boolean bl = handleChunkDebugKeys(i);
                if (bl) cir.setReturnValue(true);
            }
        }
    }

    @Unique
    private Component debugKeybind$translateHelpMessage(String key, GlobalKeybind debugKey, ActionKeybind keybind) {
        String debugName = debugKey.getDefaultKey().getDisplayName().getString().toUpperCase();
        String keyName = keybind.getDefaultKey().getDisplayName().getString().toUpperCase();

        String message = I18n.get(key);

        return Component.literal(
                message.replaceAll(debugName, debugKey.getKeyTranslation().getString())
                        .replaceFirst(keyName, keybind.getKeyTranslation().getString())
        );
    }
}
