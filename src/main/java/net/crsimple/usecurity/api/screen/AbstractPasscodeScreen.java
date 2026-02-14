package net.crsimple.usecurity.api.screen;

import net.crsimple.usecurity.api.passcode.PasscodeProtected;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public abstract class AbstractPasscodeScreen extends Screen {
    protected MinecraftClient client;
    protected final PasscodeProtected passcodeProtected;
    protected int boxMaxLength = 10;
    protected char[] allowedChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\u0008', '\u001B'};
    protected CensoringEditBox censoredBox;

    protected AbstractPasscodeScreen(PasscodeProtected passcodeProtected,Text title) {
        super(title);
        this.passcodeProtected = passcodeProtected;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        censoredBox = addDrawableChild(new CensoringEditBox(textRenderer,width / 2 - 37, height / 2 - 60, 77, 12,Text.empty(),Text.empty()));
        censoredBox.setMaxLength(boxMaxLength);
        setInitialFocus(censoredBox);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (isValidChar(chr)) {
            censoredBox.charTyped(chr,modifiers);
            client.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.15F, 1.0F);
        }
        return true;
    }

    public boolean isValidChar(char c) {
        for(char valid : allowedChars) {
            if (c == valid) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public static class CensoringEditBox extends EditBoxWidget {
        private boolean shouldCensor;

        public CensoringEditBox(TextRenderer textRenderer, int x, int y, int width, int height, Text placeholder, Text message) {
            super(textRenderer, x, y, width, height, placeholder, message);
            this.shouldCensor = true;
        }

        public void deleteLastChar() {
            if (!getText().isEmpty()) {
                setText(getText().substring(0, getText().length() - 1));
            }
        }
        public void appendChar(char c) {
            setText(getText()+c);
        }

        public boolean isShouldCensor() {
            return shouldCensor;
        }

        public void setShouldCensor(boolean shouldCensor) {
            this.shouldCensor = shouldCensor;
        }
    }
}
