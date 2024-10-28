package dev.boxadactle.debugkeybind.command;

import dev.boxadactle.boxlib.command.api.BSubcommand;
import dev.boxadactle.boxlib.command.api.subcommand.BasicSubcommand;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;

public class ToggleSubcommand {
    public static BSubcommand create() {
        return new BasicSubcommand("toggle", (ignored) -> -1)
                .registerSubcommand(new DebugSubcommand("hitboxes", DebugKeybinds.SHOW_HITBOXES))
                .registerSubcommand(new DebugSubcommand("chunk_borders", DebugKeybinds.CHUNK_BORDERS))
                .registerSubcommand(new DebugSubcommand("advanced_tooltips", DebugKeybinds.ADVANCED_TOOLTIPS))
                .registerSubcommand(new DebugSubcommand("pause_on_lost_focus", DebugKeybinds.PAUSE_FOCUS))
                .registerSubcommand(new DebugSubcommand("profiling", DebugKeybinds.PROFILING))
                .registerSubcommand(new DebugSubcommand("profiler_chart", DebugKeybinds.TOGGLE_PROFILER_CHART))
                .registerSubcommand(new DebugSubcommand("fps_charts", DebugKeybinds.TOGGLE_FPS_CHARTS))
                .registerSubcommand(new DebugSubcommand("network_charts", DebugKeybinds.TOGGLE_NETWORK_CHARTS))
                .registerSubcommand(new DebugSubcommand("fog", DebugKeybinds.TOGGLE_FOG))
                .registerSubcommand(new DebugSubcommand("smart_cull", DebugKeybinds.TOGGLE_SMART_CULL))
                .registerSubcommand(new DebugSubcommand("frustum_octree", DebugKeybinds.TOGGLE_FRUSTUM_OCTREE))
                .registerSubcommand(new DebugSubcommand("section_visibility", DebugKeybinds.TOGGLE_SECTION_VISIBILITY))
                .registerSubcommand(new DebugSubcommand("wireframe", DebugKeybinds.TOGGLE_WIREFRAME));
    }
}