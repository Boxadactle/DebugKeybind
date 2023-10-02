package dev.boxadactle.debugkeybind.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.debugkeybind.DebugKeybindMain;
import dev.boxadactle.debugkeybind.keybind.DebugKeybind;
import dev.boxadactle.debugkeybind.keybind.DebugKeybinds;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

// this class created by modifying minecraft code
public class DebugKeybindsScreen extends OptionsSubScreen {

    @Nullable
    public DebugKeybind selectedKey;
    public long lastKeySelection;
    private DebugKeybindsList keyBindsList;
    private Button resetButton;

    public DebugKeybindsScreen(Screen screen) {
        super(screen, ClientUtils.getOptions(), Component.translatable("controls.keybinds.debug.title"));
    }

    protected void init() {
        this.keyBindsList = new DebugKeybindsList(this, this.minecraft);
        this.addWidget(this.keyBindsList);
        this.resetButton = this.addRenderableWidget(Button.builder(Component.translatable("controls.resetAll"), (button) -> {
            DebugKeybind[] var2 = DebugKeybinds.toArray();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                DebugKeybind keyMapping = var2[var4];
                keyMapping.setToDefault();
            }

            this.keyBindsList.resetMappingAndUpdateButtons();
        }).bounds(this.width / 2 - 155, this.height - 29, 150, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            this.onClose();
        }).bounds(this.width / 2 - 155 + 160, this.height - 29, 150, 20).build());
    }

    public boolean keyPressed(int i, int j, int k) {
        if (this.selectedKey != null) {
            if (i == 256) {
                this.selectedKey.setKey(InputConstants.UNKNOWN);
            } else {
                this.selectedKey.setKey(i);
            }

            this.selectedKey = null;
            this.lastKeySelection = Util.getMillis();
            this.keyBindsList.resetMappingAndUpdateButtons();
            return true;
        } else {
            return super.keyPressed(i, j, k);
        }
    }

    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        this.keyBindsList.render(poseStack, i, j, f);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);
        boolean bl = false;
        DebugKeybind[] var6 = DebugKeybinds.toArray();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            DebugKeybind keyMapping = var6[var8];
            if (!keyMapping.isDefault()) {
                bl = true;
                break;
            }
        }

        this.resetButton.active = bl;
        super.render(poseStack, i, j, f);
    }

    @Override
    public void onClose() {
        super.onClose();

        DebugKeybindMain.CONFIG.save();
    }
}
