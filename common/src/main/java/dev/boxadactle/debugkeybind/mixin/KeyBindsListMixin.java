package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(KeyBindsList.KeyEntry.class)
public class KeyBindsListMixin {

    @Shadow @Final private KeyMapping key;

    @Shadow @Final private Button changeButton;

    @Shadow private boolean hasCollision;

    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void checkDebugCollisions(GuiGraphics poseStack, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f, CallbackInfo ci) {
        List<Component> collisions = DebugKeybinds.getCollisions(key);

        if (!collisions.isEmpty()) {
            changeButton.setMessage(GuiUtils.colorize(changeButton.getMessage(), GuiUtils.RED));
        }
    }

    @Inject(
            method = "refreshEntry",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isUnbound()Z"),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void checkDebugCollisions(CallbackInfo ci, MutableComponent mutableComponent) {
        List<Component> collisions = DebugKeybinds.getCollisions(key);

        if (!collisions.isEmpty()) {
            if (hasCollision) mutableComponent.append(", ");

            boolean bl = false;
            this.hasCollision = true;

            for (Component c : collisions) {
                if (bl) mutableComponent.append(", ");

                bl = true;
                mutableComponent.append(c);
            }
        }
    }

}
