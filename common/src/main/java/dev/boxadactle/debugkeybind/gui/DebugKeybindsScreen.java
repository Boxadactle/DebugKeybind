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

import java.util.List;
import java.util.function.Function;

public class DebugKeybindsScreen extends BOptionScreen {

    KeybindEntry selectedEntry;

    public DebugKeybindsScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected String getName() {
        return I18n.get("controls.keybinds.debug.title");
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
        resetButton.setMessage(I18n.get("controls.resetAll"));

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
        addConfigLine(new BCenteredLabel(I18n.get("key.categories.debug")));

        for (DebugKeybind keybind : DebugKeybinds.getGlobalKeybinds()) {
            addConfigLine(new KeybindEntry(keybind, this::setSelectedEntry, this::refreshEntries));
        }

        addConfigLine(new BCenteredLabel(I18n.get("key.categories.debug_actions")));

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

    public class KeybindEntry extends ConfigEntry {
        public DebugKeybind keybind;

        public BLabel label;
        public KeybindButton keybindButton;
        public ResetButton resetButton;

        Runnable globalRefresh;

        public KeybindEntry(DebugKeybind keybind, Function<KeybindEntry, Boolean> onSelect, Runnable refresh) {
            label = new BLabel(keybind.getTranslation());
            keybindButton = new KeybindButton(keybind, () -> onSelect.apply(this), DebugKeybindsScreen.this::renderTooltip);
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
}
