package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.api.screen.AbstractPasscodeScreen;
import net.minecraft.client.gui.EditBox;
import net.minecraft.client.gui.widget.EditBoxWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EditBoxWidget.class)
public abstract class EditBoxWidgetMixin {
    @SuppressWarnings("ConstantValue")
    @Redirect(method = "renderContents",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/EditBox;getText()Ljava/lang/String;"))
    private String renderContents(EditBox instance) {
        if ((Object)this instanceof AbstractPasscodeScreen.CensoringEditBox censorBox && censorBox.isShouldCensor()) {
            return "*".repeat(instance.getText().length());
        }
        return instance.getText();
    }
}
