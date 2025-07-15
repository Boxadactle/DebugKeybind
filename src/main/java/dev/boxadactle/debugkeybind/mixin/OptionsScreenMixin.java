package dev.boxadactle.debugkeybind.mixin;

import dev.boxadactle.boxlib.gui.config.BOptionScreen;
import dev.boxadactle.boxlib.gui.config.widget.button.BConfigScreenButton;
import dev.boxadactle.boxlib.gui.config.widget.button.BCustomButton;
import dev.boxadactle.debugkeybind.gui.DebugKeybindsScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Component component) {
        super(component);
    }

    @ModifyArg(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/Button;<init>(IIIILnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)V",
                    ordinal = 4
            ),
            index = 5
    )
    private Button.OnPress changeControlsBehavior(Button.OnPress arg) {
        return (button) -> {
            this.minecraft.setScreen(new BOptionScreen(this) {
                @Override
                protected Component getName() {
                    return new TranslatableComponent("controls.title");
                }

                @Override
                protected void initFooter(int i, int i1) {
                    addButton(createDoneButton(i, i1, parent));
                }

                @Override
                public int getRowWidth() {
                    return 310;
                }

                @Override
                public int getPadding() {
                    return 10;
                }

                @Override
                public int getScrollingWidgetStart() {
                    return super.getScrollingWidgetStart() + 10;
                }

                @Override
                public int getScrollingWidgetEnd() {
                    return super.getScrollingWidgetEnd() - 10;
                }

                @Override
                protected void initConfigButtons() {
                    addConfigLine(
                            BCustomButton.create(new TranslatableComponent("controls.keybinds.button"), () -> {
                                this.minecraft.setScreen(new ControlsScreen(this, this.minecraft.options));
                            }),
                            new BConfigScreenButton(
                                    new TranslatableComponent("controls.keybinds.debug"),
                                    this,
                                    DebugKeybindsScreen::new
                            )
                    );
                }
            });
        };
    }
}
