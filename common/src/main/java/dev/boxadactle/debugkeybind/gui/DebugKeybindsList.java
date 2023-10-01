package dev.boxadactle.debugkeybind.gui;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import dev.boxadactle.debugkeybind.keybind.GlobalKeybind;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DebugKeybindsList extends ContainerObjectSelectionList<DebugKeybindsList.Entry> {

    DebugKeybindsScreen keyBindsScreen;
    int maxNameWidth;

    public DebugKeybindsList(DebugKeybindsScreen keyBindsScreen, Minecraft minecraft) {
        super(minecraft, keyBindsScreen.width + 45, keyBindsScreen.height, 20, keyBindsScreen.height - 32, 20);
        this.keyBindsScreen = keyBindsScreen;
        DebugKeybind[] keyMappings = ArrayUtils.clone(DebugKeybinds.toArray());
        // in the original class, this is run, but we don't run it here as it is already manually sorted
        // Arrays.sort(keyMappings);
        String string = null;
        DebugKeybind[] var5 = keyMappings;
        int var6 = keyMappings.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            DebugKeybind keyMapping = var5[var7];
            String string2 = keyMapping.getCategory();
            if (!string2.equals(string)) {
                string = string2;
                this.addEntry(new DebugKeybindsList.CategoryEntry(Component.translatable(string2)));
            }

            Component component = Component.translatable(keyMapping.getName());
            int i = minecraft.font.width(component);
            if (i > this.maxNameWidth) {
                this.maxNameWidth = i;
            }

            this.addEntry(new DebugKeybindsList.KeyEntry(keyMapping, component));
        }

    }

    public DebugKeybindsList(Minecraft minecraft, int i, int j, int k, int l, int m) {
        super(minecraft, i, j, k, l, m);
    }

    public void resetMappingAndUpdateButtons() {
        this.refreshEntries();
    }

    public void refreshEntries() {
        this.children().forEach(DebugKeybindsList.Entry::refreshEntry);
    }

    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 15;
    }

    public int getRowWidth() {
        return super.getRowWidth() + 32;
    }

    @Environment(EnvType.CLIENT)
    public class CategoryEntry extends DebugKeybindsList.Entry {
        final Component name;
        private final int width;

        public CategoryEntry(Component component) {
            this.name = component;
            this.width = DebugKeybindsList.this.minecraft.font.width(this.name);
        }

        public void render(PoseStack guiGraphics, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
            Font var10001 = DebugKeybindsList.this.minecraft.font;
            Component var10002 = this.name;
            int var10003 = DebugKeybindsList.this.minecraft.screen.width / 2 - this.width / 2;
            int var10004 = j + m;
            Objects.requireNonNull(DebugKeybindsList.this.minecraft.font);
            drawString(guiGraphics, var10001, var10002, var10003, var10004 - 9 - 1, 16777215);
        }

        public List<? extends GuiEventListener> children() {
            return Collections.emptyList();
        }

        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(new NarratableEntry() {
                public NarratableEntry.NarrationPriority narrationPriority() {
                    return NarrationPriority.HOVERED;
                }

                public void updateNarration(NarrationElementOutput narrationElementOutput) {
                    narrationElementOutput.add(NarratedElementType.TITLE, DebugKeybindsList.CategoryEntry.this.name);
                }
            });
        }

        protected void refreshEntry() {
        }
    }

    @Environment(EnvType.CLIENT)
    public class KeyEntry extends DebugKeybindsList.Entry {
        private final DebugKeybind key;
        private final Component name;
        private final Button changeButton;
        private final Button resetButton;
        private boolean hasCollision = false;
        private MutableComponent collisionTooltip = Component.empty();

        KeyEntry(DebugKeybind keyMapping, Component component) {
            this.key = keyMapping;
            this.name = component;
            this.changeButton = new Button(0, 0, 75, 20, component, (button) -> {
                DebugKeybindsList.this.keyBindsScreen.selectedKey = keyMapping;
                DebugKeybindsList.this.resetMappingAndUpdateButtons();
            }) {
                @Override
                protected @NotNull MutableComponent createNarrationMessage() {
                    return hasCollision ? collisionTooltip : super.createNarrationMessage();
                }
            };
            this.resetButton = new Button(0, 0, 50, 20, Component.translatable("controls.reset"), (button) -> {
                keyMapping.setToDefault();
                DebugKeybindsList.this.resetMappingAndUpdateButtons();
            });
            this.refreshEntry();
        }

        public void render(PoseStack guiGraphics, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
            Font var10001 = DebugKeybindsList.this.minecraft.font;
            Component var10002 = this.name;
            int var10003 = k + 90 - DebugKeybindsList.this.maxNameWidth;
            int var10004 = j + m / 2;
            Objects.requireNonNull(DebugKeybindsList.this.minecraft.font);
            drawString(guiGraphics, var10001, var10002, var10003, var10004 - 9 / 2, 16777215);
            this.resetButton.x = (k + 190);
            this.resetButton.y = (j);
            this.resetButton.render(guiGraphics, n, o, f);
            this.changeButton.x = (k + 105);
            this.changeButton.y = (j);
            if (this.hasCollision) {
                int q = this.changeButton.x - 6;
                fill(guiGraphics, q, j + 2, q + 3, j + m + 2, GuiUtils.RED | -16777216);
            }

            this.changeButton.render(guiGraphics, n, o, f);
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.changeButton, this.resetButton);
        }

        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.changeButton, this.resetButton);
        }

        protected void refreshEntry() {
            this.changeButton.setMessage(this.key.getTranslatedKey());
            this.resetButton.active = !this.key.isDefault();
            this.hasCollision = false;
            MutableComponent mutableComponent = Component.empty();
            if (!this.key.isUnbound()) {
                List<DebugKeybind> var2 = DebugKeybinds.toList();

                List<Component> var3 = key.checkConflicts(var2);

                this.hasCollision = !var3.isEmpty();

                if (this.hasCollision) {
                    for (int i = 0; i < var3.size(); i++) {
                        if (i != 0) mutableComponent.append(", ");

                        mutableComponent.append(var3.get(i));
                    }
                }

                if (key instanceof GlobalKeybind) {
                    List<KeyMapping> var4 = Arrays.stream(minecraft.options.keyMappings).toList();
                    List<Component> var5 = ((GlobalKeybind) key).checkMinecraftConflicts(var4);

                    this.hasCollision = this.hasCollision || !var5.isEmpty();

                    if (!var5.isEmpty()) {
                        for (int i = 0; i < var5.size(); i++) {
                            if (i != 0) mutableComponent.append(", ");

                            mutableComponent.append(var5.get(i));
                        }
                    }
                }

                this.collisionTooltip = mutableComponent;
            }

            if (this.hasCollision) {
                this.changeButton.setMessage(Component.literal("[ ").append(this.changeButton.getMessage().copy().withStyle(ChatFormatting.WHITE)).append(" ]").withStyle(ChatFormatting.RED));
            }

            if (DebugKeybindsList.this.keyBindsScreen.selectedKey == this.key) {
                this.changeButton.setMessage(Component.literal("> ").append(this.changeButton.getMessage().copy().withStyle(new ChatFormatting[]{ChatFormatting.WHITE, ChatFormatting.UNDERLINE})).append(" <").withStyle(ChatFormatting.YELLOW));
            }

        }
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<DebugKeybindsList.Entry> {
        public Entry() {
        }

        abstract void refreshEntry();
    }


}
