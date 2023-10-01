package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(KeyBindsList.KeyEntry.class)
public class KeyBindsListMixin {

    @Shadow @Final private KeyMapping key;

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/Button;setMessage(Lnet/minecraft/network/chat/Component;)V",
                    ordinal = 0
            ),
            index = 0
    )
    private Component checkDebugCollisions(Component par1) {
        if (DebugKeybinds.collidesWithGlobalKeybinds(key))
            return par1.copy().withStyle(ChatFormatting.RED);

        return par1;
    }

}
