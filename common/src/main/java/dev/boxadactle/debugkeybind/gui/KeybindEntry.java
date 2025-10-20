package dev.boxadactle.debugkeybind.gui;

import dev.boxadactle.boxlib.gui.config.BConfigList;
import dev.boxadactle.boxlib.gui.config.BOptionScreen;
import dev.boxadactle.boxlib.gui.config.widget.label.BLabel;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import dev.boxadactle.debugkeybind.keybind.GlobalKeybind;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Function;

public class KeybindEntry extends BConfigList.ConfigEntry {
    public DebugKeybind keybind;

    public BLabel label;
    public KeybindButton keybindButton;
    public ResetButton resetButton;

    Runnable globalRefresh;

    public KeybindEntry(DebugKeybind keybind, Function<KeybindEntry, Boolean> onSelect, Runnable refresh) {
        label = new BLabel(keybind.getTranslation());
        keybindButton = new KeybindButton(keybind, () -> onSelect.apply(this));
        resetButton = new ResetButton(keybind, refresh);

        this.keybind = keybind;

        refresh();

        this.globalRefresh = refresh;
    }

    @Override
    public List<? extends AbstractWidget> getWidgets() {
        return List.of(label, keybindButton, resetButton);
    }

    @Override
    public boolean isInvalid() {
        return false;
    }

    public void updateKey(int code) {
        keybindButton.update(code);

        refresh();
        globalRefresh.run();
    }

    public void resetKey() {
        keybindButton.resetKey();

        globalRefresh.run();
    }

    public void refresh() {
        resetButton.refresh();

        if (keybind.isUnbound()) {
            keybindButton.updateConflicts(List.of());
            return;
        }

        List<Component> collisions = keybind.checkConflicts(DebugKeybinds.toList());

        if (!DebugKeybindMain.CONFIG.get().requireDebugKey || keybind instanceof GlobalKeybind) {
            KeyMapping[] mappings = ClientUtils.getOptions().keyMappings.clone();

            collisions.addAll((keybind.checkMinecraftConflicts(List.of(mappings))));
        }

        keybindButton.updateConflicts(collisions);
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean b, float v) {
        int keybindWidth = 75;
        int resetWidth = 50;
        int padding = 2;

        label.setX(getX() - 25);
        label.setY(getY());
        label.setWidth(getWidth() - keybindWidth - resetWidth - padding);
        label.render(guiGraphics, mouseX, mouseY, v);

        keybindButton.setX(getX() + getWidth() - keybindWidth - resetWidth - padding);
        keybindButton.setY(getY());
        keybindButton.setWidth(keybindWidth);
        keybindButton.render(guiGraphics, mouseX, mouseY, v);

        resetButton.setX(getX() + getWidth() - resetWidth);
        resetButton.setY(getY());
        resetButton.setWidth(resetWidth);
        resetButton.render(guiGraphics, mouseX, mouseY, v);
    }
}