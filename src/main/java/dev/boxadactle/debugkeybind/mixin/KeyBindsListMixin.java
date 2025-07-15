package dev.boxadactle.debugkeybind.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.controls.ControlList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ControlList.KeyEntry.class)
public class KeyBindsListMixin {

    @Shadow @Final private KeyMapping key;

    @Shadow @Final private Button changeButton;

    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void checkDebugCollisions(PoseStack poseStack, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f, CallbackInfo ci) {
        List<String> collisions = DebugKeybinds.getCollisions(key);

        if (!collisions.isEmpty()) {
            changeButton.setMessage(GuiUtils.colorize(changeButton.getMessage(), GuiUtils.RED));
        }
    }

//    @Redirect(
//            method = "render",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/network/chat/Component;empty()Lnet/minecraft/network/chat/MutableComponent;"
//            )
//    )
//    private MutableComponent checkDebugCollisions() {
//        List<Component> collisions = DebugKeybinds.getCollisions(key);
//
//        MutableComponent mutableComponent = Component.empty();
//
//        if (!collisions.isEmpty()) {
//            boolean bl = false;
//            this.hasCollision = true;
//
//            for (Component c : collisions) {
//                if (bl) mutableComponent.append(", ");
//
//                bl = true;
//                mutableComponent.append(c);
//            }
//        }
//
//        return mutableComponent;
//    }

}
