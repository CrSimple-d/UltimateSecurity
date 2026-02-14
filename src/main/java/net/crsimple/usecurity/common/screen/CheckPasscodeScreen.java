package net.crsimple.usecurity.common.screen;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.passcode.PasscodeProtected;
import net.crsimple.usecurity.api.screen.AbstractPasscodeScreen;
import net.crsimple.usecurity.networking.CheckPasscodeC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

import static org.lwjgl.glfw.GLFW.*;

public class CheckPasscodeScreen extends AbstractPasscodeScreen {
    public static final Identifier TEXTURE = ModMain.id("textures/gui/passcode.png");
    private static final int imageWidth = 176;
    private static final int imageHeight = 186;
    private int leftPos;
    private int topPos;

    public CheckPasscodeScreen(PasscodeProtected passcodeProtected,Text title) {
        super(passcodeProtected, title);
    }

    @Override
    protected void init() {
        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;

        addDrawableChild(new CheckboxWidget(width / 2 - 37, height / 2 + 65, 16, 20,Text.translatable("gui.usecurity.passcode.show_code"),false,true) {
            @Override
            public void onPress() {
                super.onPress();
                censoredBox.setShouldCensor(!isChecked());
            }
        });
        this.initKeypad();
        super.init();
    }

    @Override
    public void renderBackground(DrawContext ctx) {
        super.renderBackground(ctx);
        ctx.drawTexture(TEXTURE,leftPos,topPos,0f, 0f, imageWidth, imageHeight, 256, 256);

    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawText(textRenderer, title, width / 2 - textRenderer.getWidth(title) / 2, (height - imageHeight) / 2 + 6, Colors.GRAY, false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW_KEY_BACKSPACE) {
            client.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.15F, 1.0F);
        } else if (keyCode == GLFW_KEY_ENTER) {
            checkCode(censoredBox.getText());
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void checkCode(String code) {
        if(!code.isEmpty()) {
            ClientPlayNetworking.send(new CheckPasscodeC2SPacket(((BlockEntity)passcodeProtected).getPos(),code));
        }
        close();
    }

    private void initKeypad() {
        int centerX = width / 2;
        int centerY = height / 2;
        int startX = centerX - 33;
        int startY = centerY - 35;
        int size = 20;
        int gap = 5;

        int number = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = startX + col * (size + gap);
                int y = startY + row * (size + gap);
                char digit = (char) ('0' + number);
                addDrawableChild(ButtonWidget.builder(Text.literal(String.valueOf(digit)), b -> censoredBox.appendChar(digit))
                                .dimensions(x, y, size, size).build()
                );
                number++;
            }
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("←"), b -> censoredBox.deleteLastChar())
                        .dimensions(startX, startY + 3 * (size + gap), size, size).build()
        );

        addDrawableChild(ButtonWidget.builder(Text.literal("0"), b -> censoredBox.appendChar('0'))
                        .dimensions(startX + (size + gap), startY + 3 * (size + gap), size, size).build()
        );

        addDrawableChild(ButtonWidget.builder(Text.literal("✔"), b -> checkCode(censoredBox.getText()))
                        .dimensions(startX + 2 * (size + gap), startY + 3 * (size + gap), size, size).build()
        );
    }
}
