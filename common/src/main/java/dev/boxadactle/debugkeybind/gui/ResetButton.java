package dev.boxadactle.debugkeybind.gui;

import dev.boxadactle.boxlib.gui.config.BOptionButton;
import dev.boxadactle.boxlib.gui.config.widget.button.BCustomButton;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import net.minecraft.network.chat.Component;

public class ResetButton extends BCustomButton {

    DebugKeybind keybind;
    Runnable refresh;

    public ResetButton(DebugKeybind keybind, Runnable refresh) {
        super(Component.translatable("controls.reset"));

        this.keybind = keybind;
        this.refresh = refresh;
    }

    public void refresh() {
        active = !keybind.isDefault();
    }

    @Override
    protected void buttonClicked(BOptionButton<?> button) {
        keybind.setToDefault();
        refresh();

        refresh.run();
    }
}
