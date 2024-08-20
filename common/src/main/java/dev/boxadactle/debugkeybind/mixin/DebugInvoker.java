package dev.boxadactle.debugkeybind.mixin;

import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(KeyboardHandler.class)
public interface DebugInvoker {

    @Invoker("handleDebugKeys")
    boolean invokeHandleDebugKeys(int keyCode);

}
