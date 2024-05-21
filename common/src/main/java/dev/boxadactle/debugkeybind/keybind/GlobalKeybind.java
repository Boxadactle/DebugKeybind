package dev.boxadactle.debugkeybind.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.debugkeybind.mixin.KeyAccessor;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class GlobalKeybind implements DebugKeybind {

    String name;
    String category;
    InputConstants.Key key;
    InputConstants.Key defaultKey;

    public GlobalKeybind(String string, int i, String string2) {
        name = string;
        category = string2;
        key = InputConstants.Type.KEYSYM.getOrCreate(i);
        defaultKey = key;
    }

    public void setToDefault() {
        key = defaultKey;
    }

    public InputConstants.Key getDefaultKey() {
        return defaultKey;
    }

    public void setKey(InputConstants.Key key) {
        this.key = key;
    }

    public void setKey(int key) {
        this.key = InputConstants.Type.KEYSYM.getOrCreate(key);
    }

    public InputConstants.Key getKey() {
        return key;
    }

    public int getKeyCode() {
        return key.getValue();
    }

    public int getDefaultKeyCode() {
        return defaultKey.getValue();
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean isUnbound() {
        return key.equals(InputConstants.UNKNOWN);
    }

    public Component getTranslatedKey() {
        return key.getDisplayName();
    }

    public boolean isDefault() {
        return key.getValue() == defaultKey.getValue();
    }

    public String saveString() {
        return this.key.getName();
    }

    public List<Component> checkConflicts(List<DebugKeybind> keybinds) {
        List<Component> list = new ArrayList<>();

        for (DebugKeybind k : keybinds) {
            if (!k.getName().equals(name) && k.getKeyCode() == getKeyCode()) list.add(Component.translatable(k.getName()));
        }

        return list;
    }

    public List<Component> checkMinecraftConflicts(List<KeyMapping> keyMappings) {
        List<Component> list = new ArrayList<>();

        for (KeyMapping k : keyMappings) {
            if (((KeyAccessor)k).getKey().getValue() == getKeyCode()) list.add(Component.translatable(k.getName()));
        }

        return list;
    }
}
