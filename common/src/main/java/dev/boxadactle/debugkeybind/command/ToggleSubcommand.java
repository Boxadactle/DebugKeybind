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
                .registerSubcommand(new DebugSubcommand("profiling", DebugKeybinds.PROFILING));
    }
}