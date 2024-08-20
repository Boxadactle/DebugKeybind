package dev.boxadactle.debugkeybind.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.boxadactle.boxlib.gui.config.BOptionButton;
import dev.boxadactle.boxlib.gui.config.widget.button.BCustomButton;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.boxlib.util.RenderUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;
import java.util.function.Supplier;

public class KeybindButton extends BCustomButton {

    DebugKeybind keybind;
    Supplier<Boolean> onSelect;
    String tooltip = null;

    public boolean hasCollisions = false;

    public KeybindButton(DebugKeybind keybind, Supplier<Boolean> onSelect) {
        super(keybind.getKeyTranslation());

        this.keybind = keybind;
        this.onSelect = onSelect;
    }

    public void update(int keyPressed) {
        if (keyPressed != 256) {
            keybind.setKey(keyPressed);
        }

        setMessage(keybind.getKeyTranslation());
    }

    public void resetKey() {
        keybind.setToDefault();
        setMessage(keybind.getKeyTranslation());
    }

    public void updateConflicts(List<String> conflicts) {
        if (conflicts.isEmpty()) {
            setMessage(keybind.getKeyTranslation());
            tooltip = null;
            hasCollisions = false;

            return;
        }

        setMessage(GuiUtils.colorize(
                GuiUtils.surround(
                        "[ ", " ]",
                        GuiUtils.colorize(
                                keybind.getKeyTranslation().copy().withStyle(ChatFormatting.UNDERLINE),
                                GuiUtils.WHITE
                        )
                ),
                GuiUtils.RED
        ));

        MutableComponent tooltip = Component.literal("");

        for (int i = 0; i < conflicts.size() ; i++) {
            tooltip.append(conflicts.get(i));

            if (i != conflicts.size() - 1) {
                tooltip.append(Component.literal(",\n"));
            }
        }

        this.tooltip = I18n.get("controls.keybinds.duplicateKeybinds", tooltip);

        hasCollisions = true;
    }

    @Override
    protected void buttonClicked(BOptionButton<?> button) {
        if (onSelect.get()) {
            setMessage(
                    GuiUtils.colorize(
                            GuiUtils.surround("> ", " <", GuiUtils.colorize(
                                    keybind.getKeyTranslation().copy().withStyle(ChatFormatting.UNDERLINE),
                                    GuiUtils.WHITE
                            )),
                            GuiUtils.YELLOW
                    )
            );
        }
    }

    @Override
    public void renderButton(PoseStack p_93657_, int mouseX, int mouseY, float delta) {
        super.renderButton(p_93657_, mouseX, mouseY, delta);

        if (hasCollisions) {
            RenderUtils.drawSquare(p_93657_, x - 12, y, 10, height, GuiUtils.RED);
        }
    }
}
