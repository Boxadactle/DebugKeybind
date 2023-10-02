package dev.boxadactle.debugkeybind.keybind;

import com.google.common.collect.Lists;
import dev.boxadactle.debugkeybind.mixin.KeyAccessor;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DebugKeybinds {

    static List<GlobalKeybind> list = new ArrayList<>();
    static List<ActionKeybind> list2 = new ArrayList<>();
    static HashMap<Integer, ActionKeybind> map = new HashMap<>();

    public static GlobalKeybind DEBUG = createGlobalKeybind("key.debug.debugkeybind", 292);
    public static GlobalKeybind HIDE_GUI = createGlobalKeybind("key.debug.hide_gui", 290);

    public static ActionKeybind RELOAD_CHUNKS = createActionKeybind("key.debug_actions.reload_chunks", 65);
    public static ActionKeybind SHOW_HITBOXES = createActionKeybind("key.debug_actions.show_hitboxes", 66);
    public static ActionKeybind COPY_LOCATION = createActionKeybind("key.debug_actions.copy_location", 67);
    public static ActionKeybind CLEAR_CHAT = createActionKeybind("key.debug_actions.clear_chat", 68);
    public static ActionKeybind CHUNK_BORDERS = createActionKeybind("key.debug_actions.chunk_borders", 71);
    public static ActionKeybind ADVANCED_TOOLTIPS = createActionKeybind("key.debug_actions.advanced_tooltips", 72);
    public static ActionKeybind INSPECT = createActionKeybind("key.debug_actions.inspect", 73);
    public static ActionKeybind PROFILING = createActionKeybind("key.debug_actions.profiling", 76);
    public static ActionKeybind CREATIVE_SPECTATOR = createActionKeybind("key.debug_actions.creative_spectator", 78);
    public static ActionKeybind PAUSE_FOCUS = createActionKeybind("key.debug_actions.pause_focus", 80);
    public static ActionKeybind HELP = createActionKeybind("key.debug_actions.help", 81);
    public static ActionKeybind DUMP_DYNAMIC_TEXTURES = createActionKeybind("key.debug_actions.dynamic_textures", 83);
    public static ActionKeybind RELOAD_RESOURCEPACKS = createActionKeybind("key.debug_actions.reload_resourcepacks", 84);
    public static ActionKeybind OPEN_GAMEMODE_SWITCHER = createActionKeybind("key.debug_actions.open_gamemode_switcher", 293);
    public static ActionKeybind PAUSE_WITHOUT_MENU = createActionKeybind("key.debug_actions.pause_without_menu", 256);

    public static void refreshActionBindings() {
        map.clear();

        for (ActionKeybind k : list2) {
            map.put(k.getKeyCode(), k);
        }
    }

    public static int remapActionKey(int code) {
        ActionKeybind k = map.get(code);
        return k != null ? k.getDefaultKeyCode() : -1;
    }

    public static List<DebugKeybind> toList() {
        return Lists.newArrayList(
                DEBUG,
                HIDE_GUI,

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

    public static List<Component> getCollisions(KeyMapping k) {
        List<Component> collisions = new ArrayList<>();

        for (GlobalKeybind key : list) {
            if (key.getKeyCode() == ((KeyAccessor)k).getKey().getValue()) collisions.add(Component.translatable(key.getName()));
        }

        return collisions;
    }

    public static DebugKeybind[] toArray() {
        return toList().toArray(new DebugKeybind[]{});
    }

    private static GlobalKeybind createGlobalKeybind(String key, int i) {
        GlobalKeybind keybind = new GlobalKeybind(key, i, "key.categories.debug");
        list.add(keybind);
        return keybind;
    }

    private static ActionKeybind createActionKeybind(String key, int i) {
        ActionKeybind keybind = new ActionKeybind(key, i, "key.categories.debug_actions");
        list2.add(keybind);
        return keybind;
    }
}
