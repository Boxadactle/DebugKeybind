package dev.boxadactle.debugkeybind.command;

import dev.boxadactle.boxlib.command.api.BSubcommand;
import dev.boxadactle.boxlib.command.api.subcommand.BasicSubcommand;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;

public class CopySubcommand {
    public static BSubcommand create() {
        return new BasicSubcommand("copy", (ignored) -> -1)
                .registerSubcommand(new DebugSubcommand("location", DebugKeybinds.COPY_LOCATION))
                .registerSubcommand(new DebugSubcommand("inspect_data", DebugKeybinds.INSPECT));
    }
}