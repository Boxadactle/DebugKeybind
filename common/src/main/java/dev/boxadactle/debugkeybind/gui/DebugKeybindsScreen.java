package dev.boxadactle.debugkeybind.gui;

import dev.boxadactle.boxlib.gui.config.BOptionScreen;
import dev.boxadactle.boxlib.gui.config.widget.button.BCustomButton;
import dev.boxadactle.boxlib.gui.config.widget.label.BCenteredLabel;
import dev.boxadactle.boxlib.gui.config.widget.label.BLabel;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import dev.boxadactle.debugkeybind.keybind.GlobalKeybind;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;
import java.util.function.Function;

public class DebugKeybindsScreen extends BOptionScreen {

    KeybindEntry selectedEntry;

    public DebugKeybindsScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected Component getName() {
        return new TranslatableComponent("controls.keybinds.debug.title");
    }

    @Override
    protected int getRowWidth() {
        return 340;
    }

    @Override
    protected int getScrollbarPosition() {
        return width - 15;
    }

    private void refreshEntries() {
        configList.children().forEach(entry -> {
            if (entry instanceof KeybindEntry) ((KeybindEntry) entry).refresh();
        });
    }

    @Override
    protected void initFooter(int startX, int startY) {
        Button resetButton = createHalfCancelButton(startX, startY, (button) -> {
            configList.children().forEach(entry -> {
                if (entry instanceof KeybindEntry) ((KeybindEntry) entry).resetKey();
            });

            refreshEntries();
        });
        resetButton.setMessage(new TranslatableComponent("controls.resetAll"));

        Button doneButton = createHalfDoneButton(startX, startY, (b) -> {
            ClientUtils.setScreen(parent);

            DebugKeybindMain.CONFIG.save();
        });
        doneButton.x = (startX + getButtonWidth(ButtonType.SMALL) + getPadding());

        addButton(resetButton);
        addButton(doneButton);
    }

    @Override
    protected void initConfigButtons() {
        addConfigLine(new BCenteredLabel(new TranslatableComponent("key.categories.debug")));

        for (DebugKeybind keybind : DebugKeybinds.getGlobalKeybinds()) {
            addConfigLine(new KeybindEntry(keybind, this::setSelectedEntry, this::refreshEntries));
        }

        addConfigLine(new BCenteredLabel(new TranslatableComponent("key.categories.debug_actions")));

        for (DebugKeybind keybind : DebugKeybinds.getActionKeybinds()) {
            addConfigLine(new KeybindEntry(keybind, this::setSelectedEntry, this::refreshEntries));
        }
    }

    private boolean setSelectedEntry(KeybindEntry entry) {
        if (selectedEntry != null) {
            selectedEntry = null;
            return false;
        }

        selectedEntry = entry;
        return true;
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (selectedEntry != null) {
            selectedEntry.updateKey(i);
            selectedEntry = null;
        }

        return super.keyPressed(i, j, k);
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        if (selectedEntry != null) {
            selectedEntry.updateKey(256);
        }

        return super.mouseClicked(d, e, i);
    }

    @Override
    public void onClose() {
        super.onClose();

        DebugKeybindMain.CONFIG.save();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return selectedEntry == null;
    }
}
