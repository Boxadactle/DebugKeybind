package dev.boxadactle.debugkeybind.mixin;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.TextureUtil;
import dev.boxadactle.debugkeybind.DebugKeybind;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.Locale;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow private boolean handledDebugKey;

    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void debugFeedbackTranslated(String string, Object... objects);

    @Shadow protected abstract void copyRecreateCommand(boolean bl, boolean bl2);

    @Shadow protected abstract void debugFeedbackComponent(Component component);

    @Shadow public abstract void setClipboard(String string);

    @Shadow private long debugCrashKeyTime;
    
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
                if (this.handledDebugKey) {
                    this.handledDebugKey = false;
                } else {
                    this.minecraft.options.renderDebug = !this.minecraft.options.renderDebug;
                    this.minecraft.options.renderDebugCharts = this.minecraft.options.renderDebug && Screen.hasShiftDown();
                    this.minecraft.options.renderFpsChart = this.minecraft.options.renderDebug && Screen.hasAltDown();
                }
            }

            // now to check if the key pressed was F3, so we can open the menu (to be closed by the hard-code).
            // The keybind is not bound to F3, so the menu should not open
            else if(i == 292) {
                if (this.handledDebugKey) {
                    this.handledDebugKey = false;
                } else {
                    this.minecraft.options.renderDebug = !this.minecraft.options.renderDebug;
                    this.minecraft.options.renderDebugCharts = this.minecraft.options.renderDebug && Screen.hasShiftDown();
                    this.minecraft.options.renderFpsChart = this.minecraft.options.renderDebug && Screen.hasAltDown();
                }
            }
        }
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

    
    /**
     * @author Boxadactle
     * @reason Replace method since values are hardcoded.
     *         Copy+pasted and modified
     */
    @Overwrite
    private boolean handleDebugKeys(int i) {
        if (this.debugCrashKeyTime > 0L && this.debugCrashKeyTime < Util.getMillis() - 100L) {
            return true;
        } else {
            if (i == DebugKeybind.RELOAD_CHUNKS.getKeyCode()) {
                this.minecraft.levelRenderer.allChanged();
                this.debugFeedbackTranslated("debug.reload_chunks.message");
                return true;
            }

            if (i == DebugKeybind.SHOW_HITBOXES.getKeyCode()) {
                boolean bl = !this.minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes();
                this.minecraft.getEntityRenderDispatcher().setRenderHitBoxes(bl);
                this.debugFeedbackTranslated(bl ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off");
                return true;
            }

            if (i == DebugKeybind.COPY_LOCATION.getKeyCode()) {
                if (this.minecraft.player.isReducedDebugInfo()) {
                    return false;
                } else {
                    ClientPacketListener clientPacketListener = this.minecraft.player.connection;
                    if (clientPacketListener == null) {
                        return false;
                    }

                    this.debugFeedbackTranslated("debug.copy_location.message");
                    this.setClipboard(String.format(Locale.ROOT, "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f", this.minecraft.player.level().dimension().location(), this.minecraft.player.getX(), this.minecraft.player.getY(), this.minecraft.player.getZ(), this.minecraft.player.getYRot(), this.minecraft.player.getXRot()));
                    return true;
                }
            }

            if (i == DebugKeybind.CLEAR_CHAT.getKeyCode()) {
                if (this.minecraft.gui != null) {
                    this.minecraft.gui.getChat().clearMessages(false);
                }

                return true;

            }

            if (i == DebugKeybind.CHUNK_BORDERS.getKeyCode()) {
                boolean bl2 = this.minecraft.debugRenderer.switchRenderChunkborder();
                this.debugFeedbackTranslated(bl2 ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off");
                return true;
            }

            if (i == DebugKeybind.ADVANCED_TOOLTIPS.getKeyCode()) {
                this.minecraft.options.advancedItemTooltips = !this.minecraft.options.advancedItemTooltips;
                this.debugFeedbackTranslated(this.minecraft.options.advancedItemTooltips ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off");
                this.minecraft.options.save();

                return true;
            }

            if (i == DebugKeybind.INSPECT.getKeyCode()) {
                if (!this.minecraft.player.isReducedDebugInfo()) {
                    this.copyRecreateCommand(this.minecraft.player.hasPermissions(2), !Screen.hasShiftDown());
                }

                return true;
            }

            if (i == DebugKeybind.PROFILING.getKeyCode()) {
                if (this.minecraft.debugClientMetricsStart(this::debugFeedbackComponent)) {
                    this.debugFeedbackTranslated("debug.profiling.start", 10);
                }

                return true;
            }

            if (i == DebugKeybind.CREATIVE_SPECTATOR.getKeyCode()) {
                if (!this.minecraft.player.hasPermissions(2)) {
                    this.debugFeedbackTranslated("debug.creative_spectator.error");
                } else if (!this.minecraft.player.isSpectator()) {
                    this.minecraft.player.connection.sendUnsignedCommand("gamemode spectator");
                } else {
                    ClientPacketListener var10000 = this.minecraft.player.connection;
                    GameType var10001 = this.minecraft.gameMode.getPreviousPlayerMode();
                    var10000.sendUnsignedCommand("gamemode " + ((GameType) MoreObjects.firstNonNull(var10001, GameType.CREATIVE)).getName());
                }

                return true;
            }

            if (i == DebugKeybind.PAUSE_FOCUS.getKeyCode()) {
                this.minecraft.options.pauseOnLostFocus = !this.minecraft.options.pauseOnLostFocus;
                this.minecraft.options.save();
                this.debugFeedbackTranslated(this.minecraft.options.pauseOnLostFocus ? "debug.pause_focus.on" : "debug.pause_focus.off");
                return true;
            }

            if (i == DebugKeybind.HELP.getKeyCode()) {
                this.debugFeedbackTranslated("debug.help.message");

                Component debugKey = DebugKeybind.DEBUG.getTranslatedKey();

                ChatComponent chatComponent = this.minecraft.gui.getChat();
                chatComponent.addMessage(Component.translatable("debug.reload_chunks.help", debugKey, DebugKeybind.RELOAD_CHUNKS.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.show_hitboxes.help", debugKey, DebugKeybind.SHOW_HITBOXES.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.copy_location.help", debugKey, DebugKeybind.COPY_LOCATION.getTranslatedKey(), debugKey, "C"));
                chatComponent.addMessage(Component.translatable("debug.clear_chat.help", debugKey, DebugKeybind.CLEAR_CHAT.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.chunk_boundaries.help", debugKey, DebugKeybind.CHUNK_BORDERS.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.advanced_tooltips.help", debugKey, DebugKeybind.ADVANCED_TOOLTIPS.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.inspect.help", debugKey, DebugKeybind.INSPECT.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.profiling.help", debugKey, DebugKeybind.PROFILING.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.creative_spectator.help", debugKey, DebugKeybind.CREATIVE_SPECTATOR.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.pause_focus.help", debugKey, DebugKeybind.PAUSE_FOCUS.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.help.help", debugKey, DebugKeybind.HELP.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.dump_dynamic_textures.help", debugKey, DebugKeybind.DUMP_DYNAMIC_TEXTURES.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.reload_resourcepacks.help", debugKey, DebugKeybind.RELOAD_RESOURCEPACKS.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.pause.help", debugKey, DebugKeybind.PAUSE_WITHOUT_MENU.getTranslatedKey()));
                chatComponent.addMessage(Component.translatable("debug.gamemodes.help", debugKey, DebugKeybind.OPEN_GAMEMODE_SWITCHER.getTranslatedKey()));
                return true;
            }

            if (i == DebugKeybind.DUMP_DYNAMIC_TEXTURES.getKeyCode()) {
                Path path = this.minecraft.gameDirectory.toPath().toAbsolutePath();
                Path path2 = TextureUtil.getDebugTexturePath(path);
                this.minecraft.getTextureManager().dumpAllSheets(path2);
                Component component = Component.literal(path.relativize(path2).toString()).withStyle(ChatFormatting.UNDERLINE).withStyle((style) -> {
                    return style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path2.toFile().toString()));
                });
                this.debugFeedbackTranslated("debug.dump_dynamic_textures", component);
                return true;
            }

            if (i == DebugKeybind.RELOAD_RESOURCEPACKS.getKeyCode()) {
                this.debugFeedbackTranslated("debug.reload_resourcepacks.message");
                this.minecraft.reloadResourcePacks();
                return true;
            }

            if (i == DebugKeybind.OPEN_GAMEMODE_SWITCHER.getKeyCode()) {
                if (!this.minecraft.player.hasPermissions(2)) {
                    this.debugFeedbackTranslated("debug.gamemodes.error");
                } else {
                    this.minecraft.setScreen(new GameModeSwitcherScreen());
                }
            }
        }
        return false;
    }
}
