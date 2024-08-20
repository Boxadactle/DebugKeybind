package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    @Redirect(
            method = "drawGameInformation",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 2
            )
    )
    private <E> boolean addHelpMessage(List<String> instance, E e) {
        return instance.add(
                String.format(
                        "For help: press %s + %s",
                        DebugKeybinds.DEBUG.getKeyTranslation().getString(),
                        DebugKeybinds.HELP.getKeyTranslation().getString()
                )
        );
    }

}
