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

    Component getTranslation();

    default Component getKeyTranslation() {
        return getKey().getDisplayName();
    }

    boolean isDefault();

    List<String> checkConflicts(List<DebugKeybind> keybinds);

    default Component getComponent() {
        return new TranslatableComponent(getName());
    }
}
