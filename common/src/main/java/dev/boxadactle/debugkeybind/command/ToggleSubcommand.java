package dev.boxadactle.debugkeybind.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.boxadactle.boxlib.command.BCommandManager;
import dev.boxadactle.boxlib.command.BCommandSourceStack;
import dev.boxadactle.boxlib.command.api.BClientSubcommand;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;

public class ToggleSubcommand implements BClientSubcommand {
    @Override
    public ArgumentBuilder<BCommandSourceStack, ?> getSubcommand() {
        return BCommandManager.literal("toggle");
    }

    @Override
    public void build(ArgumentBuilder<BCommandSourceStack, ?> builder) {
        builder.then(new DebugSubcommand("hitboxes", DebugKeybinds.SHOW_HITBOXES).buildSubcommand());
        builder.then(new DebugSubcommand("chunk_borders", DebugKeybinds.CHUNK_BORDERS).buildSubcommand());
        builder.then(new DebugSubcommand("advanced_tooltips", DebugKeybinds.ADVANCED_TOOLTIPS).buildSubcommand());
        builder.then(new DebugSubcommand("pause_on_lost_focus", DebugKeybinds.PAUSE_FOCUS).buildSubcommand());
        builder.then(new DebugSubcommand("profiling", DebugKeybinds.PROFILING).buildSubcommand());
    }
}