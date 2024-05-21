package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

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

    // i have found a better way to do this
    @ModifyConstant(
            method = "keyPressed",
            constant = @Constant(intValue = 293)
    )
    private int overrideF4Press(int i) {
        return DebugKeybinds.OPEN_GAMEMODE_SWITCHER.getKeyCode();
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V",
                    ordinal = 1
            ),
            index = 1
    )
    private Component overrideSwitchKeyComponent(Component component) {
        return Component.translatable(
                "debug.gamemodes.select_next",
                Component.translatable(
                        "debug.gamemodes.press_f4",
                                DebugKeybinds.OPEN_GAMEMODE_SWITCHER.getTranslatedKey()
                ).withStyle(ChatFormatting.AQUA)
        );
    }

}
