package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin extends Screen {

    @Shadow @Nullable private OptionsList list;

    protected ControlsScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void addButton(CallbackInfo ci) {
        int j = this.width / 2 - 155;
        int k = this.height / 6 - 12 + (24 * 3);

        addRenderableWidget(
                Button.builder(
                        Component.translatable("controls.keybinds.debug"),
                        (b) -> minecraft.setScreen(new DebugKeybindsScreen(this))
                ).bounds(j, k, 150, 20).build()
        );

        list.addSmall(List.of(Button.builder(
                Component.translatable("controls.keybinds.debug"),
                (b) -> minecraft.setScreen(new DebugKeybindsScreen(this))
        ).build()));
    }

}
