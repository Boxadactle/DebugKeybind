package dev.boxadactle.debugkeybind.command;

import dev.boxadactle.boxlib.command.api.BClientCommand;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;

public class F3Command {

    public static BClientCommand create() {
        return BClientCommand.create("f3", (context) -> {
            ClientUtils.getClient().getDebugOverlay().toggleOverlay();
            return 0;
        })
                .registerSubcommand(new ReloadSubcommand())
                .registerSubcommand(new ToggleSubcommand())
                .registerSubcommand(new CopySubcommand())
                .registerSubcommand(new DebugSubcommand("clear_chat", DebugKeybinds.CLEAR_CHAT))
                .registerSubcommand(new DebugSubcommand("creative_spectator", DebugKeybinds.CREATIVE_SPECTATOR))
                .registerSubcommand(new DebugSubcommand("clear_chat", DebugKeybinds.CLEAR_CHAT))
                .registerSubcommand(new DebugSubcommand("help", DebugKeybinds.HELP))
                .registerSubcommand(new DebugSubcommand("dump_textures", DebugKeybinds.DUMP_DYNAMIC_TEXTURES))
                .registerSubcommand(new DebugSubcommand("gamemode", DebugKeybinds.OPEN_GAMEMODE_SWITCHER))
                .registerSubcommand(new DebugSubcommand("clear_chat", DebugKeybinds.CLEAR_CHAT))
                .registerSubcommand(new DebugSubcommand("pause_menuless", DebugKeybinds.PAUSE_WITHOUT_MENU));
    }

}
