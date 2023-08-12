package dev.boxadactle.debugkeybind;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;

import java.util.List;

public class DebugKeybind {

    public static DebugKeybind DEBUG = createDebugKeybind("key.debug.debugkeybind", 292);

    public static DebugKeybind RELOAD_CHUNKS = createActionKeybind("key.debug_actions.reload_chunks", 65);
    public static DebugKeybind SHOW_HITBOXES = createActionKeybind("key.debug_actions.show_hitboxes", 66);
    public static DebugKeybind COPY_LOCATION = createActionKeybind("key.debug_actions.copy_location", 67);
    public static DebugKeybind CLEAR_CHAT = createActionKeybind("key.debug_actions.clear_chat", 68);
    public static DebugKeybind CHUNK_BORDERS = createActionKeybind("key.debug_actions.chunk_borders", 71);
    public static DebugKeybind ADVANCED_TOOLTIPS = createActionKeybind("key.debug_actions.advanced_tooltips", 72);
    public static DebugKeybind INSPECT = createActionKeybind("key.debug_actions.inspect", 73);
    public static DebugKeybind PROFILING = createActionKeybind("key.debug_actions.profiling", 76);
    public static DebugKeybind CREATIVE_SPECTATOR = createActionKeybind("key.debug_actions.creative_spectator", 78);
    public static DebugKeybind PAUSE_FOCUS = createActionKeybind("key.debug_actions.pause_focus", 80);
    public static DebugKeybind HELP = createActionKeybind("key.debug_actions.help", 81);
    public static DebugKeybind DUMP_DYNAMIC_TEXTURES = createActionKeybind("key.debug_actions.dynamic_textures", 83);
    public static DebugKeybind RELOAD_RESOURCEPACKS = createActionKeybind("key.debug_actions.reload_resourcepacks", 84);
    public static DebugKeybind OPEN_GAMEMODE_SWITCHER = createActionKeybind("key.debug_actions.open_gamemode_switcher", 293);
    public static DebugKeybind PAUSE_WITHOUT_MENU = createActionKeybind("key.debug_actions.pause_without_menu", 256);

    public static void resetAllToDefault() {
        for (DebugKeybind k : toList()) {
            k.setToDefault();
        }
    }

    public static List<DebugKeybind> toList() {
        return Lists.newArrayList(
                DEBUG,

                RELOAD_CHUNKS,
                SHOW_HITBOXES,
                COPY_LOCATION,
                CLEAR_CHAT,
                CHUNK_BORDERS,
                ADVANCED_TOOLTIPS,
                INSPECT,
                PROFILING,
                CREATIVE_SPECTATOR,
                PAUSE_FOCUS,
                HELP,
                DUMP_DYNAMIC_TEXTURES,
                RELOAD_RESOURCEPACKS,
                OPEN_GAMEMODE_SWITCHER,
                PAUSE_WITHOUT_MENU
        );
    }

    public static DebugKeybind[] toArray() {
        return toList().toArray(new DebugKeybind[]{});
    }

    private static DebugKeybind createDebugKeybind(String key, int i) {
        return new DebugKeybind(key, i, "key.categories.debug");
    }

    private static DebugKeybind createActionKeybind(String key, int i) {
        return new DebugKeybind(key, i, "key.categories.debug_actions");
    }

    String name;
    String category;
    InputConstants.Key key;
    InputConstants.Key defaultKey;

    public DebugKeybind(String string, int i, String string2) {
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

    public boolean conflicts(DebugKeybind k) {
        return k.getKeyCode() == key.getValue();
    }
}
