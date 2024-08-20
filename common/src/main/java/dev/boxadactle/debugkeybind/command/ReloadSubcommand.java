
package dev.boxadactle.debugkeybind.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.boxadactle.boxlib.command.BCommandManager;
import dev.boxadactle.boxlib.command.BCommandSourceStack;
import dev.boxadactle.boxlib.command.api.BClientSubcommand;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;

public class ReloadSubcommand implements BClientSubcommand {
    @Override
    public ArgumentBuilder<BCommandSourceStack, ?> getSubcommand() {
        return BCommandManager.literal("reload");
    }

    @Override
    public void build(ArgumentBuilder<BCommandSourceStack, ?> builder) {
        builder.then(new DebugSubcommand("chunks", DebugKeybinds.RELOAD_CHUNKS).buildSubcommand());
        builder.then(new DebugSubcommand("resources", DebugKeybinds.RELOAD_RESOURCEPACKS).buildSubcommand());
    }
}