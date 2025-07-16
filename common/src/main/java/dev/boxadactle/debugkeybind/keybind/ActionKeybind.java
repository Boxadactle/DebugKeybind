package dev.boxadactle.debugkeybind.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.boxlib.keybind.KeybindHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ActionKeybind implements DebugKeybind {

    String name;
    String category;
    InputConstants.Key key;
    InputConstants.Key defaultKey;
    InputConstants.Key oDefaultKey;

    public ActionKeybind(String string, int i, String string2) {
        name = string;
        category = string2;
        key = InputConstants.Type.KEYSYM.getOrCreate(i);
        defaultKey = key;
    }

    public ActionKeybind(String string, int i, String string2, int oDefault) {
        this(string, i, string2);
        oDefaultKey = InputConstants.Type.KEYSYM.getOrCreate(oDefault);
    }

    public int getRebind() {
        return defaultKey.getValue();
    }

    @Override
    public void setToDefault() {
        key = oDefaultKey != null ? oDefaultKey : defaultKey;
    }

    @Override
    public InputConstants.Key getDefaultKey() {
        return oDefaultKey != null ? oDefaultKey : defaultKey;
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
        return (oDefaultKey != null ? oDefaultKey : defaultKey).getValue();
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
    public Component getTranslation() {
        return Component.translatable(name);
    }

    @Override
    public boolean isDefault() {
        return key.getValue() == (oDefaultKey != null ? oDefaultKey : defaultKey).getValue();
    }

    @Override
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
            if (KeybindHelper.getBoundKey(k).getValue() == getKeyCode()) list.add(Component.translatable(k.getName()));
        }

        return list;
    }
}
