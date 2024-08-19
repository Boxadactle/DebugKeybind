package dev.boxadactle.debugkeybind.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;
import java.util.Objects;

public interface DebugKeybind {

    void setToDefault();

    InputConstants.Key getDefaultKey();

    void setKey(InputConstants.Key key);

    void setKey(int key);

    InputConstants.Key getKey();

    int getKeyCode();

    int getDefaultKeyCode();

    String getName();

    String getCategory();

    boolean isUnbound();

    String getTranslation();

    default String getKeyTranslation() {
        if (isUnbound()) return I18n.get("key.keyboard.unknown");

        String string = getKey().getName();
        int i = getKey().getValue();
        String string2 = switch (getKey().getType()) {
            case KEYSYM -> InputConstants.translateKeyCode(i);
            case SCANCODE -> InputConstants.translateScanCode(i);
            case MOUSE -> {
                String string3 = I18n.get(string);
                yield Objects.equals(string3, string) ? I18n.get(InputConstants.Type.MOUSE.getDefaultPrefix(), i + 1) : string3;
            }
        };

        return string2 == null ? I18n.get(string) : string2;
    }

    boolean isDefault();

    String saveString();

    List<String> checkConflicts(List<DebugKeybind> keybinds);

    default Component getComponent() {
        return new TranslatableComponent(getName());
    }
}
