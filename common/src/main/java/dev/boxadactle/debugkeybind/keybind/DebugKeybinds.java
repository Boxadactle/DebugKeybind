package dev.boxadactle.debugkeybind.keybind;

import com.google.common.collect.Lists;
import dev.boxadactle.boxlib.keybind.KeybindHelper;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.input.KeyEvent;
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

    public static ActionKeybind TOGGLE_PROFILER_CHART = createActionKeybind("key.debug_actions.toggle_profiler_chart", 49);
    public static ActionKeybind TOGGLE_FPS_CHARTS = createActionKeybind("key.debug_actions.toggle_fps_charts", 50);
    public static ActionKeybind TOGGLE_NETWORK_CHARTS = createActionKeybind("key.debug_actions.toggle_network_charts", 51);
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
    public static ActionKeybind CHUNK_SECTION_PATH = createActionKeybind("key.debug_actions.chunk_section_path", 69);
    public static ActionKeybind TOGGLE_FOG = createActionKeybind("key.debug_actions.toggle_fog", 70);
    public static ActionKeybind TOGGLE_SMART_CULL = createActionKeybind("key.debug_actions.toggle_smart_cull", 76, 77);
    public static ActionKeybind TOGGLE_FRUSTUM_OCTREE = createActionKeybind("key.debug_actions.toggle_frustum_octree", 79);
    public static ActionKeybind CAPTURE_FRUSTUM = createActionKeybind("key.debug_actions.capture_frustum", 85);
    public static ActionKeybind TOGGLE_SECTION_VISIBILITY = createActionKeybind("key.debug_actions.toggle_section_visibility", 86);
    public static ActionKeybind TOGGLE_WIREFRAME = createActionKeybind("key.debug_actions.toggle_wireframe", 87);
    public static ActionKeybind TOGGLE_DEBUG_KEY = createActionKeybind("key.debug_actions.toggle_debug_key", 88, -1);

    public static void refreshActionBindings() {
        map.clear();

        for (ActionKeybind k : list2) {
            map.put(k.getKeyCode(), k);
        }
    }

    public static KeyEvent remapActionKey(KeyEvent key) {
        ActionKeybind k = map.get(key.key());
        int keyCode = k != null ? k.getRebind() : -1;

        // if keyCode is -1, we need to check if the key is an action keybind by default
        // if it is, we return -1, to cancel the hard-coded keybind
        if (keyCode == -1) {
            for (ActionKeybind keybind : list2) {
                if (keybind.getRebind() == key.key()) {
                    return null;
                }
            }
        }

        return keyCode != -1 ? new KeyEvent(keyCode, key.scancode(), key.modifiers()) : key;
    }

    public static List<DebugKeybind> toList() {
        List<DebugKeybind> keybinds = new ArrayList<>();
        keybinds.addAll(getGlobalKeybinds());
        keybinds.addAll(getActionKeybinds());
        return keybinds;
    }

    public static List<DebugKeybind> getGlobalKeybinds() {
        return Lists.newArrayList(
                DEBUG,
                HIDE_GUI
        );
    }

    public static List<DebugKeybind> getActionKeybinds() {
        return Lists.newArrayList(
                TOGGLE_PROFILER_CHART,
                TOGGLE_FPS_CHARTS,
                TOGGLE_NETWORK_CHARTS,
                TOGGLE_DEBUG_KEY,
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
                PAUSE_WITHOUT_MENU,
                CHUNK_SECTION_PATH,
                TOGGLE_FOG,
                TOGGLE_SMART_CULL,
                TOGGLE_FRUSTUM_OCTREE,
                CAPTURE_FRUSTUM,
                TOGGLE_SECTION_VISIBILITY,
                TOGGLE_WIREFRAME
        );
    }

    static boolean canCollide(DebugKeybind k) {
        return !(k instanceof ActionKeybind) || !DebugKeybindMain.CONFIG.get().requireDebugKey;
    }

    public static List<Component> getCollisions(KeyMapping k) {
        List<Component> collisions = new ArrayList<>();

        for (DebugKeybind keybind : toList()) {
            if (canCollide(keybind) && keybind.getKeyCode() == (KeybindHelper.getBoundKey(k)).getValue()) {
                collisions.add(keybind.getTranslation());
            }
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

    private static ActionKeybind createActionKeybind(String key, int i, int defaultI) {
        ActionKeybind keybind = new ActionKeybind(key, i, "key.categories.debug_actions", defaultI);
        list2.add(keybind);
        return keybind;
    }

    public static List<Component> createHelpComponents() {
        List<Component> components = new ArrayList<>();
        for (DebugKeybind keybind : getActionKeybinds()) {
            if (keybind.getKeyCode() != -1) {
                Component base = DebugKeybindMain.CONFIG.get().requireDebugKey ? DEBUG.getKeyTranslation().copy().append(" + ") : Component.empty();
                components.add(GuiUtils.colorize(base.copy()
                        .append(GuiUtils.colorize(keybind.getKeyTranslation(), GuiUtils.GREEN))
                        .append(" = ")
                        .append(GuiUtils.colorize(keybind.getComponent(), GuiUtils.AQUA))
                , GuiUtils.GOLD));
            } else {
                components.add(GuiUtils.colorize(Component.translatable("controls.keybinds.disabled")
                        .append(" = ")
                        .append(GuiUtils.colorize(keybind.getComponent(), GuiUtils.AQUA))
                , GuiUtils.RED));
            }
        }
        return components;
    }
}
