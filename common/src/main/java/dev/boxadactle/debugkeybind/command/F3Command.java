package dev.boxadactle.debugkeybind.command;

import dev.boxadactle.boxlib.command.api.BCommand;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;

public class F3Command {

    public static BCommand create() {
        return BCommand.create("f3", (context) -> {
                    ClientUtils.getClient().getDebugOverlay().toggleOverlay();
                    return 0;
                })
                .registerSubcommand(ReloadSubcommand.create())
                .registerSubcommand(ToggleSubcommand.create())
                .registerSubcommand(CopySubcommand.create())
                .registerSubcommand(new DebugSubcommand("clear_chat", DebugKeybinds.CLEAR_CHAT))
                .registerSubcommand(new DebugSubcommand("creative_spectator", DebugKeybinds.CREATIVE_SPECTATOR))
                .registerSubcommand(new DebugSubcommand("clear_chat", DebugKeybinds.CLEAR_CHAT))
                .registerSubcommand(new DebugSubcommand("help", DebugKeybinds.HELP))
                .registerSubcommand(new DebugSubcommand("dump_textures", DebugKeybinds.DUMP_DYNAMIC_TEXTURES))
                .registerSubcommand(new DebugSubcommand("gamemode", DebugKeybinds.OPEN_GAMEMODE_SWITCHER))
                .registerSubcommand(new DebugSubcommand("clear_chat", DebugKeybinds.CLEAR_CHAT))
                .registerSubcommand(new DebugSubcommand("pause_menuless", DebugKeybinds.PAUSE_WITHOUT_MENU))
                .registerSubcommand(new DebugSubcommand("capture_frustum", DebugKeybinds.CAPTURE_FRUSTUM))
                .registerSubcommand(new DebugSubcommand("chunk_section_path", DebugKeybinds.CHUNK_SECTION_PATH));
    }

}