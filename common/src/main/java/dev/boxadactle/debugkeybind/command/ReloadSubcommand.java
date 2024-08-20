
package dev.boxadactle.debugkeybind.command;

import dev.boxadactle.boxlib.command.api.BSubcommand;
import dev.boxadactle.boxlib.command.api.subcommand.BasicSubcommand;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;

public class ReloadSubcommand {
    public static BSubcommand create() {
        return new BasicSubcommand("reload", (ignored) -> -1)
                .registerSubcommand(new DebugSubcommand("chunks", DebugKeybinds.RELOAD_CHUNKS))
                .registerSubcommand(new DebugSubcommand("resources", DebugKeybinds.RELOAD_RESOURCEPACKS));
    }
}