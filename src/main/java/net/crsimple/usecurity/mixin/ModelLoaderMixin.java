package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.ModMain;
import net.minecraft.client.render.model.ModelLoader;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Redirect(method = "method_21604",at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    public void loadModel(Logger instance, String string, Object o1, Object o2) {
        if (ModMain.LOADING_EXCEPTIONS) {
            instance.warn(string,o1,o2);
        }
    }
    @Redirect(method = "getOrLoadModel",at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;[Ljava/lang/Object;)V"))
    public void loadModel(Logger instance, String string, Object[] objects) {
        if (ModMain.LOADING_EXCEPTIONS) {
            instance.warn(string,objects);
        }
    }
}
