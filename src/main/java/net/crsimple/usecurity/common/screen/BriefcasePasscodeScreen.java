package net.crsimple.usecurity.common.screen;

import net.crsimple.usecurity.ModMain;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

public abstract class BriefcasePasscodeScreen extends Screen {
    public static final Text UP_ARROW = Text.literal("\u2191");
    public static final Text RIGHT_ARROW = Text.literal("\u2192");
    public static final Text DOWN_ARROW = Text.literal("\u2193");
    public static final Identifier TEXTURE = ModMain.id("textures/gui/blank.png");
    public int imageWidth = 176;
    public int imageHeight = 166;
    protected int leftPos;
    protected int topPos;
    protected final int slot;
    protected final byte[] code = {0,0,0,0};
    protected TextWidget[] texts = new TextWidget[4];

    protected BriefcasePasscodeScreen(int slot, Text title) {
        super(title);
        this.slot = slot;
    }

    @Override
    protected void init() {
        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;

        for (int i = 0; i < 4; i++) {
            final int id = i;
            ButtonWidget btnUp = ButtonWidget.builder(UP_ARROW,b -> code[id]+=1)
                    .dimensions(width / 2 - 40 + (i * 20), height / 2 - 52, 20, 20).build();
            ButtonWidget btnDown = ButtonWidget.builder(DOWN_ARROW,b -> code[id]-=1)
                    .dimensions(width / 2 - 40 + (i * 20), height / 2, 20, 20).build();
            TextWidget text = new TextWidget((width / 2 - 37) + (i * 20), height / 2 - 22, 14, 12,Text.literal("0"),textRenderer);
            texts[i] = text;

            addDrawable(text);
            addDrawableChild(btnUp);
            addDrawableChild(btnDown);
        }

        ButtonWidget btn = ButtonWidget.builder(RIGHT_ARROW, b -> confirmCode(code))
                .dimensions((width / 2 + 42), height / 2 - 26, 20, 20).build();
        addDrawableChild(btn);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW_KEY_ENTER) {
            confirmCode(code);
            return true;
        }
        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public void tick() {
        for (int i = 0; i < texts.length; i++) {
            texts[i].setMessage(Text.literal(code[i]+""));
        }
    }

    protected abstract void confirmCode(byte[] code);

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(DrawContext ctx) {
        super.renderBackground(ctx);
        ctx.drawTexture(TEXTURE,leftPos,topPos,0f,0f,imageWidth,imageHeight,256,256);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
