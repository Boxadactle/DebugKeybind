package dev.boxadactle.debugkeybind.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.boxadactle.boxlib.command.BCommandManager;
import dev.boxadactle.boxlib.command.BCommandSourceStack;
import dev.boxadactle.boxlib.command.api.subcommand.BasicSubcommand;
import dev.boxadactle.boxlib.scheduling.Scheduling;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import dev.boxadactle.debugkeybind.mixin.DebugInvoker;
import net.minecraft.client.input.KeyEvent;

public class DebugSubcommand extends BasicSubcommand {

    String name;
    DebugKeybind keybind;

    public DebugSubcommand(String name, DebugKeybind keybind) {
        super(name, null);
        this.name = name;
        this.keybind = keybind;
    }

    @Override
    public void build(ArgumentBuilder<BCommandSourceStack, ?> builder) {
        builder.executes(context -> {
            Scheduling.nextTick(() -> ((DebugInvoker) ClientUtils.getClient().keyboardHandler).invokeHandleDebugKeys(new KeyEvent(keybind.getDefaultKeyCode(), 0, 0)));

            return 0;
        });
    }
}