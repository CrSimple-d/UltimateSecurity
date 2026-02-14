package net.crsimple.usecurity.common.screen;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.passcode.PasscodeProtected;
import net.crsimple.usecurity.api.screen.AbstractPasscodeScreen;
import net.crsimple.usecurity.networking.CheckPasscodeC2SPacket;
import net.crsimple.usecurity.networking.SetPasscodeC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

import static org.lwjgl.glfw.GLFW.*;

public class SetPasscodeScreen extends AbstractPasscodeScreen {
    public static final Identifier TEXTURE = ModMain.id("textures/gui/blank.png");
    public static final int imageWidth = 176;
    public static final int imageHeight = 166;
    private final Text combined;
    private ButtonWidget saveBtn;
    private int leftPos;
    private int topPos;

    public SetPasscodeScreen(PasscodeProtected passcodeProtected,Text title) {
        super(passcodeProtected, title);
        this.combined = title.copy().append(" ").append(Text.translatable("gui.usecurity.passcode.setup"));
    }

    @Override
    protected void init() {
        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;

        saveBtn = addDrawableChild(ButtonWidget.builder(Text.translatable("button.usecurity.save"), b -> saveCode(censoredBox.getText()))
                .dimensions(width / 2 - 48, height / 2 + 30 + 10, 100, 20).build()
        );
        saveBtn.active = false;
        addDrawableChild(new CheckboxWidget(width / 2 - 37, height / 2 - 35, 16, 20,Text.translatable("gui.usecurity.passcode.show_code"),false,true) {
            @Override
            public void onPress() {
                super.onPress();
                censoredBox.setShouldCensor(!isChecked());
            }
        });
        super.init();
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawText(textRenderer,"CODE:", width / 2 - 67, height / 2 - 57, Colors.GRAY,false);
        ctx.drawText(textRenderer,combined, width / 2 - textRenderer.getWidth(combined) / 2, topPos + 6, Colors.GRAY, false);
    }

    @Override
    public void tick() {
        saveBtn.active = !censoredBox.getText().isEmpty();
    }

    @Override
    public void renderBackground(DrawContext ctx) {
        super.renderBackground(ctx);
        ctx.drawTexture(TEXTURE,leftPos,topPos,0f, 0f, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW_KEY_ENTER) {
            this.saveCode(censoredBox.getText());
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void saveCode(String code) {
        if(!code.isEmpty()) {
            ClientPlayNetworking.send(new SetPasscodeC2SPacket(((BlockEntity)passcodeProtected).getPos(),code));
        }
        close();
    }
}
