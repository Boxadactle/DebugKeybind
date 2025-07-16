package dev.boxadactle.debugkeybind.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
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

    Component getTranslation();

    default Component getKeyTranslation() {
        return getKey().getDisplayName();
    }

    boolean isDefault();

    List<Component> checkConflicts(List<DebugKeybind> keybinds);

    List<Component> checkMinecraftConflicts(List<KeyMapping> keyMappings);

    default Component getComponent() {
        return Component.translatable(getName());
    }
}
