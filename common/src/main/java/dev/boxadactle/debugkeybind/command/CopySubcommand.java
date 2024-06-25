package dev.boxadactle.debugkeybind.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.boxadactle.boxlib.command.BCommandManager;
import dev.boxadactle.boxlib.command.BCommandSourceStack;
import dev.boxadactle.boxlib.command.api.BClientSubcommand;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;

public class CopySubcommand implements BClientSubcommand {
    @Override
    public ArgumentBuilder<BCommandSourceStack, ?> getSubcommand() {
        return BCommandManager.literal("copy");
    }

    @Override
    public void build(ArgumentBuilder<BCommandSourceStack, ?> builder) {
        builder.then(new DebugSubcommand("location", DebugKeybinds.COPY_LOCATION).buildSubcommand());
        builder.then(new DebugSubcommand("inspect_data", DebugKeybinds.INSPECT).buildSubcommand());
    }
}
