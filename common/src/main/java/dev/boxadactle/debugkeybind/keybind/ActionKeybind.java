package dev.boxadactle.debugkeybind.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ActionKeybind implements DebugKeybind {

    String name;
    String category;
    InputConstants.Key key;
    InputConstants.Key defaultKey;

    public ActionKeybind(String string, int i, String string2) {
        name = string;
        category = string2;
        key = InputConstants.Type.KEYSYM.getOrCreate(i);
        defaultKey = key;
    }

    @Override
    public void setToDefault() {
        key = defaultKey;
    }

    @Override
    public InputConstants.Key getDefaultKey() {
        return defaultKey;
    }

    @Override
    public void setKey(InputConstants.Key key) {
        this.key = key;
    }

    @Override
    public void setKey(int key) {
        this.key = InputConstants.Type.KEYSYM.getOrCreate(key);
    }

    @Override
    public InputConstants.Key getKey() {
        return key;
    }

    @Override
    public int getKeyCode() {
        return key.getValue();
    }

    @Override
    public int getDefaultKeyCode() {
        return defaultKey.getValue();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public boolean isUnbound() {
        return key.equals(InputConstants.UNKNOWN);
    }

    @Override
    public Component getTranslatedKey() {
        return key.getDisplayName();
    }

    @Override
    public boolean isDefault() {
        return key.getValue() == defaultKey.getValue();
    }

    @Override
    public String saveString() {
        return this.key.getName();
    }

    @Override
    public List<Component> checkConflicts(List<DebugKeybind> keybinds) {
        List<Component> list = new ArrayList<>();

        for (DebugKeybind k : keybinds) {
            if (!k.getName().equals(name) && k.getKeyCode() == getKeyCode()) list.add(Component.translatable(k.getName()));
        }

        return list;
    }
}
