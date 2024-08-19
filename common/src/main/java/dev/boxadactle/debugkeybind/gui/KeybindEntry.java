package dev.boxadactle.debugkeybind.gui;

import dev.boxadactle.boxlib.gui.config.BOptionScreen;
import dev.boxadactle.boxlib.gui.config.widget.label.BLabel;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import dev.boxadactle.debugkeybind.keybind.GlobalKeybind;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.List;
import java.util.function.Function;

public class KeybindEntry extends BOptionScreen.ConfigList.ConfigEntry {
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

        List<String> collisions = keybind.checkConflicts(DebugKeybinds.toList());

        if (keybind instanceof GlobalKeybind) {
            KeyMapping[] mappings = ClientUtils.getOptions().keyMappings.clone();

            collisions.addAll(((GlobalKeybind) keybind).checkMinecraftConflicts(List.of(mappings)));
        }

        keybindButton.updateConflicts(collisions);
    }

    @Override
    public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int keybindWidth = 75;
        int resetWidth = 50;
        int padding = 2;

        label.x = (x - 25);
        label.y = (y);
        label.setWidth(entryWidth - keybindWidth - resetWidth - padding);
        label.render(mouseX, mouseY, tickDelta);

        keybindButton.x = (x + entryWidth - keybindWidth - resetWidth - padding);
        keybindButton.y =(y);
        keybindButton.setWidth(keybindWidth);
        keybindButton.render(mouseX, mouseY, tickDelta);

        resetButton.x = (x + entryWidth - resetWidth);
        resetButton.y = (y);
        resetButton.setWidth(resetWidth);
        resetButton.render(mouseX, mouseY, tickDelta);
    }
}