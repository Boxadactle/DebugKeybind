package dev.boxadactle.debugkeybind.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.boxlib.config.BConfig;
import dev.boxadactle.boxlib.config.BConfigFile;
import dev.boxadactle.debugkeybind.DebugKeybindMain;

import static dev.boxadactle.debugkeybind.keybind.DebugKeybinds.*;

@BConfigFile(DebugKeybindMain.MOD_ID)
public class KeybindConfig implements BConfig {

    int getDefault(DebugKeybind k) {
        return k.getDefaultKeyCode();
    }

    int getKey(DebugKeybind k) {
        return k.getKeyCode();
    }

    InputConstants.Key toKey(int k) {
        return k != -1 ? InputConstants.getKey(k, 0) : InputConstants.UNKNOWN;
    }

    // debug
    public int debug = getDefault(DEBUG);
    public int hideGui = getDefault(HIDE_GUI);

    // debug actions
    public int reloadChunks = getDefault(RELOAD_CHUNKS);
    public int showHitboxes = getDefault(SHOW_HITBOXES);
    public int copyLocation = getDefault(COPY_LOCATION);
    public int cycleRenderDistance = getDefault(CYCLE_RENDER_DISTANCE);
    public int clearChat = getDefault(CLEAR_CHAT);
    public int chunkBorders = getDefault(CHUNK_BORDERS);
    public int advancedTooltips = getDefault(ADVANCED_TOOLTIPS);
    public int inspect = getDefault(INSPECT);
//    public int profiling = getDefault(PROFILING);
    public int creativeSpectator = getDefault(CREATIVE_SPECTATOR);
    public int pauseFocus = getDefault(PAUSE_FOCUS);
    public int help = getDefault(HELP);
//    public int dumpDynamicTextures = getDefault(DUMP_DYNAMIC_TEXTURES);
    public int reloadResourcePacks = getDefault(RELOAD_RESOURCEPACKS);
    public int openGamemodeSwitcher = getDefault(OPEN_GAMEMODE_SWITCHER);
    public int pauseWithoutMenu = getDefault(PAUSE_WITHOUT_MENU);

    @Override
    public void onConfigLoadPost() {
        DEBUG.setKey(toKey(debug));
        HIDE_GUI.setKey(toKey(hideGui));

        RELOAD_CHUNKS.setKey(toKey(reloadChunks));
        SHOW_HITBOXES.setKey(toKey(showHitboxes));
        COPY_LOCATION.setKey(toKey(copyLocation));
        CYCLE_RENDER_DISTANCE.setKey(toKey(cycleRenderDistance));
        CLEAR_CHAT.setKey(toKey(clearChat));
        CHUNK_BORDERS.setKey(toKey(chunkBorders));
        ADVANCED_TOOLTIPS.setKey(toKey(advancedTooltips));
        INSPECT.setKey(toKey(inspect));
//        PROFILING.setKey(toKey(profiling));
        CREATIVE_SPECTATOR.setKey(toKey(creativeSpectator));
        PAUSE_FOCUS.setKey(toKey(pauseFocus));
        HELP.setKey(toKey(help));
//        DUMP_DYNAMIC_TEXTURES.setKey(toKey(dumpDynamicTextures));
        RELOAD_RESOURCEPACKS.setKey(toKey(reloadResourcePacks));
        OPEN_GAMEMODE_SWITCHER.setKey(toKey(openGamemodeSwitcher));
        PAUSE_WITHOUT_MENU.setKey(toKey(pauseWithoutMenu));

        DebugKeybinds.refreshActionBindings();

        DebugKeybindMain.LOGGER.info("Loaded all keybinds");
    }

    @Override
    public void onConfigSavePre() {
        debug = getKey(DEBUG);
        hideGui = getKey(HIDE_GUI);

        reloadChunks = getKey(RELOAD_CHUNKS);
        showHitboxes = getKey(SHOW_HITBOXES);
        copyLocation = getKey(COPY_LOCATION);
        cycleRenderDistance = getKey(CYCLE_RENDER_DISTANCE);
        clearChat = getKey(CLEAR_CHAT);
        chunkBorders = getKey(CHUNK_BORDERS);
        advancedTooltips = getKey(ADVANCED_TOOLTIPS);
        inspect = getKey(INSPECT);
//        profiling = getKey(PROFILING);
        creativeSpectator = getKey(CREATIVE_SPECTATOR);
        pauseFocus = getKey(PAUSE_FOCUS);
        help = getKey(HELP);
//        dumpDynamicTextures = getKey(DUMP_DYNAMIC_TEXTURES);
        reloadResourcePacks = getKey(RELOAD_RESOURCEPACKS);
//        openGamemodeSwitcher = getKey(OPEN_GAMEMODE_SWITCHER);
        pauseWithoutMenu = getKey(PAUSE_WITHOUT_MENU);

        DebugKeybinds.refreshActionBindings();

        DebugKeybindMain.LOGGER.info("Sucessfully loaded all keybinds to be saved");
    }
}
