package dev.boxadactle.debugkeybind.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.boxadactle.boxlib.command.BCommandManager;
import dev.boxadactle.boxlib.command.BCommandSourceStack;
import dev.boxadactle.boxlib.command.api.BClientSubcommand;
import dev.boxadactle.boxlib.scheduling.Scheduling;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import dev.boxadactle.debugkeybind.mixin.DebugInvoker;

public class DebugSubcommand implements BClientSubcommand {

    String name;
    DebugKeybind keybind;

    public DebugSubcommand(String name, DebugKeybind keybind) {
        this.name = name;
        this.keybind = keybind;
    }

    @Override
    public ArgumentBuilder<BCommandSourceStack, ?> getSubcommand() {
        return BCommandManager.literal(name);
    }

    @Override
    public void build(ArgumentBuilder<BCommandSourceStack, ?> builder) {
        builder.executes(context -> {
            Scheduling.nextTick(() -> ((DebugInvoker) ClientUtils.getClient().keyboardHandler).invokeHandleDebugKeys(keybind.getDefaultKeyCode()));

            return 0;
        });
    }
}