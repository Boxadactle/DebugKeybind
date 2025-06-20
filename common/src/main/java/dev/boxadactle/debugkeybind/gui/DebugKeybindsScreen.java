package dev.boxadactle.debugkeybind.gui;

import dev.boxadactle.boxlib.gui.config.BOptionScreen;
import dev.boxadactle.boxlib.gui.config.widget.label.BCenteredLabel;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DebugKeybindsScreen extends BOptionScreen {

    KeybindEntry selectedEntry;

    public DebugKeybindsScreen(Screen parent) {
        super(parent, Component.translatable("controls.keybinds.debug.title"));
    }

    @Override
    protected int getRowWidth() {
        return 340;
    }

    @Override
    protected int getScrollbarX() {
        return width - 15;
    }

    private void refreshEntries() {
        configList.children().forEach(entry -> {
            if (entry instanceof KeybindEntry) ((KeybindEntry) entry).refresh();
        });
    }

    @Override
    protected void initFooter(LinearLayout layout) {
        Button resetButton = layout.addChild(createCancelButton((button) -> {
            configList.children().forEach(entry -> {
                if (entry instanceof KeybindEntry) ((KeybindEntry) entry).resetKey();
            });

            refreshEntries();
        }));
        resetButton.setMessage(Component.translatable("controls.resetAll"));

        Button doneButton = layout.addChild(createDoneButton((b) -> {
            ClientUtils.setScreen(lastScreen);

            DebugKeybindMain.CONFIG.save();
        }));

        addRenderableWidget(resetButton);
        addRenderableWidget(doneButton);
    }

    @Override
    protected void addOptions() {
        addConfigLine(new BCenteredLabel(Component.translatable("key.categories.debug")));

        for (DebugKeybind keybind : DebugKeybinds.getGlobalKeybinds()) {
            addConfigLine(new KeybindEntry(keybind, this::setSelectedEntry, this::refreshEntries));
        }

        addConfigLine(new BCenteredLabel(Component.translatable("key.categories.debug_actions")));

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

            return true;
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
}
