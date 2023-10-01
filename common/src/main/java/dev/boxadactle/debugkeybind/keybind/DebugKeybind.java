package dev.boxadactle.debugkeybind.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;

import java.util.List;

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

    Component getTranslatedKey();

    boolean isDefault();

    String saveString();

    List<Component> checkConflicts(List<DebugKeybind> keybinds);
}
