package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(KeyBindsList.KeyEntry.class)
public class KeyBindsListMixin {

    @Shadow @Final private KeyMapping key;

    @Shadow private boolean hasCollision;

    @Redirect(
            method = "refreshEntry",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;empty()Lnet/minecraft/network/chat/MutableComponent;"
            )
    )
    private MutableComponent checkDebugCollisions() {
        List<Component> collisions = DebugKeybinds.getCollisions(key);

        MutableComponent mutableComponent = Component.empty();

        if (!collisions.isEmpty()) {
            boolean bl = false;
            this.hasCollision = true;

            for (Component c : collisions) {
                if (bl) mutableComponent.append(", ");

                bl = true;
                mutableComponent.append(c);
            }
        }

        return mutableComponent;
    }

}
