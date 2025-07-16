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
    public int toggleProfilerChart = getDefault(TOGGLE_PROFILER_CHART);
    public int toggleFpsCharts = getDefault(TOGGLE_FPS_CHARTS);
    public int toggleNetworkCharts = getDefault(TOGGLE_NETWORK_CHARTS);
    public int reloadChunks = getDefault(RELOAD_CHUNKS);
    public int showHitboxes = getDefault(SHOW_HITBOXES);
    public int copyLocation = getDefault(COPY_LOCATION);
    public int clearChat = getDefault(CLEAR_CHAT);
    public int chunkBorders = getDefault(CHUNK_BORDERS);
    public int advancedTooltips = getDefault(ADVANCED_TOOLTIPS);
    public int inspect = getDefault(INSPECT);
    public int profiling = getDefault(PROFILING);
    public int creativeSpectator = getDefault(CREATIVE_SPECTATOR);
    public int pauseFocus = getDefault(PAUSE_FOCUS);
    public int help = getDefault(HELP);
    public int dumpDynamicTextures = getDefault(DUMP_DYNAMIC_TEXTURES);
    public int reloadResourcePacks = getDefault(RELOAD_RESOURCEPACKS);
    public int openGamemodeSwitcher = getDefault(OPEN_GAMEMODE_SWITCHER);
    public int pauseWithoutMenu = getDefault(PAUSE_WITHOUT_MENU);
    public int chunkSectionPath = getDefault(CHUNK_SECTION_PATH);
    public int toggleFog = getDefault(TOGGLE_FOG);
    public int toggleSmartCull = getDefault(TOGGLE_SMART_CULL);
    public int toggleFrustumOctree = getDefault(TOGGLE_FRUSTUM_OCTREE);
    public int captureFrustum = getDefault(CAPTURE_FRUSTUM);
    public int toggleSectionVisibility = getDefault(TOGGLE_SECTION_VISIBILITY);
    public int toggleWireframe = getDefault(TOGGLE_WIREFRAME);
    public int toggleDebugKey = getDefault(TOGGLE_DEBUG_KEY);

    public boolean requireDebugKey = true;

    @Override
    public void onConfigLoadPost() {
        DEBUG.setKey(toKey(debug));
        HIDE_GUI.setKey(toKey(hideGui));

        TOGGLE_PROFILER_CHART.setKey(toKey(toggleProfilerChart));
        TOGGLE_FPS_CHARTS.setKey(toKey(toggleFpsCharts));
        TOGGLE_NETWORK_CHARTS.setKey(toKey(toggleNetworkCharts));
        RELOAD_CHUNKS.setKey(toKey(reloadChunks));
        SHOW_HITBOXES.setKey(toKey(showHitboxes));
        COPY_LOCATION.setKey(toKey(copyLocation));
        CLEAR_CHAT.setKey(toKey(clearChat));
        CHUNK_BORDERS.setKey(toKey(chunkBorders));
        ADVANCED_TOOLTIPS.setKey(toKey(advancedTooltips));
        INSPECT.setKey(toKey(inspect));
        PROFILING.setKey(toKey(profiling));
        CREATIVE_SPECTATOR.setKey(toKey(creativeSpectator));
        PAUSE_FOCUS.setKey(toKey(pauseFocus));
        HELP.setKey(toKey(help));
        DUMP_DYNAMIC_TEXTURES.setKey(toKey(dumpDynamicTextures));
        RELOAD_RESOURCEPACKS.setKey(toKey(reloadResourcePacks));
        OPEN_GAMEMODE_SWITCHER.setKey(toKey(openGamemodeSwitcher));
        PAUSE_WITHOUT_MENU.setKey(toKey(pauseWithoutMenu));
        CHUNK_SECTION_PATH.setKey(toKey(chunkSectionPath));
        TOGGLE_FOG.setKey(toKey(toggleFog));
        TOGGLE_SMART_CULL.setKey(toKey(toggleSmartCull));
        TOGGLE_FRUSTUM_OCTREE.setKey(toKey(toggleFrustumOctree));
        CAPTURE_FRUSTUM.setKey(toKey(captureFrustum));
        TOGGLE_SECTION_VISIBILITY.setKey(toKey(toggleSectionVisibility));
        TOGGLE_WIREFRAME.setKey(toKey(toggleWireframe));
        TOGGLE_DEBUG_KEY.setKey(toKey(toggleDebugKey));

        DebugKeybinds.refreshActionBindings();

        DebugKeybindMain.LOGGER.info("Loaded all keybinds");
    }

    @Override
    public void onConfigSavePre() {
        debug = getKey(DEBUG);
        hideGui = getKey(HIDE_GUI);

        toggleProfilerChart = getKey(TOGGLE_PROFILER_CHART);
        toggleFpsCharts = getKey(TOGGLE_FPS_CHARTS);
        toggleNetworkCharts = getKey(TOGGLE_NETWORK_CHARTS);
        reloadChunks = getKey(RELOAD_CHUNKS);
        showHitboxes = getKey(SHOW_HITBOXES);
        copyLocation = getKey(COPY_LOCATION);
        clearChat = getKey(CLEAR_CHAT);
        chunkBorders = getKey(CHUNK_BORDERS);
        advancedTooltips = getKey(ADVANCED_TOOLTIPS);
        inspect = getKey(INSPECT);
        profiling = getKey(PROFILING);
        creativeSpectator = getKey(CREATIVE_SPECTATOR);
        pauseFocus = getKey(PAUSE_FOCUS);
        help = getKey(HELP);
        dumpDynamicTextures = getKey(DUMP_DYNAMIC_TEXTURES);
        reloadResourcePacks = getKey(RELOAD_RESOURCEPACKS);
        openGamemodeSwitcher = getKey(OPEN_GAMEMODE_SWITCHER);
        pauseWithoutMenu = getKey(PAUSE_WITHOUT_MENU);
        chunkSectionPath = getKey(CHUNK_SECTION_PATH);
        toggleFog = getKey(TOGGLE_FOG);
        toggleSmartCull = getKey(TOGGLE_SMART_CULL);
        toggleFrustumOctree = getKey(TOGGLE_FRUSTUM_OCTREE);
        captureFrustum = getKey(CAPTURE_FRUSTUM);
        toggleSectionVisibility = getKey(TOGGLE_SECTION_VISIBILITY);
        toggleWireframe = getKey(TOGGLE_WIREFRAME);
        toggleDebugKey = getKey(TOGGLE_DEBUG_KEY);

        DebugKeybinds.refreshActionBindings();

        DebugKeybindMain.LOGGER.info("Sucessfully loaded all keybinds to be saved");
    }
}
