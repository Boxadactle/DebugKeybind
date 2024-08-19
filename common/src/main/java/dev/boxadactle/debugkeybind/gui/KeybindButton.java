package dev.boxadactle.debugkeybind.gui;

import dev.boxadactle.boxlib.function.Consumer3;
import dev.boxadactle.boxlib.gui.config.BOptionButton;
import dev.boxadactle.boxlib.gui.config.widget.button.BCustomButton;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.boxlib.util.RenderUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

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

//        setMessage(GuiUtils.colorize(
//                GuiUtils.surround(
//                        "[ ", " ]",
//                        GuiUtils.colorize(
//                                new TextComponent(keybind.getKeyTranslation()).withStyle(ChatFormatting.UNDERLINE),
//                                ChatFormatting.WHITE
//                        )
//                ),
//                ChatFormatting.RED
//        ).getColoredString());
        setMessage(
                ChatFormatting.RED + "[ " +
                GuiUtils.colorize(
                        new TextComponent(keybind.getKeyTranslation()).withStyle(ChatFormatting.UNDERLINE),
                        ChatFormatting.WHITE
                ).getColoredString() +
                ChatFormatting.RED + " ]"
        );

        Component tooltip = new TextComponent("");

        for (int i = 0; i < conflicts.size() ; i++) {
            tooltip.append(conflicts.get(i));

            if (i != conflicts.size() - 1) {
                tooltip.append(new TextComponent(",\n"));
            }
        }

        this.tooltip = I18n.get("controls.keybinds.duplicateKeybinds", tooltip);

        hasCollisions = true;
    }

    @Override
    protected void buttonClicked(BOptionButton<?> button) {
        if (onSelect.get()) {
            setMessage(
                    ChatFormatting.YELLOW + "> " +
                    GuiUtils.colorize(
                            new TextComponent(keybind.getKeyTranslation()).withStyle(ChatFormatting.UNDERLINE),
                            ChatFormatting.WHITE
                    ).getColoredString() +
                    ChatFormatting.YELLOW + " <"
            );
//            setMessage(GuiUtils.colorize(
//                    GuiUtils.surround(
//                            "> ", " <",
//                            GuiUtils.colorize(
//                                    new TextComponent(keybind.getKeyTranslation()).withStyle(ChatFormatting.UNDERLINE),
//                                    ChatFormatting.WHITE
//                            )
//                    ),
//                    ChatFormatting.YELLOW
//            ).getColoredString());
        }
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float delta) {
        super.renderButton(mouseX, mouseY, delta);

        if (hasCollisions) {
            RenderUtils.drawSquare(x - 12, y, 10, height, GuiUtils.RED);
        }
    }
}
