package net.crsimple.usecurity.common.screen;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.keycard.KeycardBlockEntity;
import net.crsimple.usecurity.api.keycard.LevelMode;
import net.crsimple.usecurity.common.registry.ModItems;
import net.crsimple.usecurity.common.screen.container.KeycardReaderMenu;
import net.crsimple.usecurity.networking.UpdateKeycardC2SPacket;
import net.crsimple.usecurity.api.SignatureImpl;
import net.crsimple.usecurity.networking.UpdateKeycardReaderC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeycardReaderScreen extends HandledScreen<KeycardReaderMenu> {
    private static final Identifier TEXTURE = ModMain.id("textures/gui/keycard_reader.png");
    private static final Identifier CONFIRM_SPRITE = new Identifier("container/beacon/confirm");
    private static final Identifier CANCEL_SPRITE = new Identifier("container/beacon/cancel");
    private static final Identifier RANDOM_SPRITE = ModMain.id("textures/gui/widget/random.png");
    private static final Identifier RANDOM_INACTIVE_SPRITE = ModMain.id("widget/random_inactive");
    private static final Identifier RESET_SPRITE = ModMain.id("textures/gui/widget/reset.png");
    private static final Identifier RESET_INACTIVE_SPRITE = ModMain.id("widget/reset_inactive");
    private static final Identifier RETURN_SPRITE = ModMain.id("widget/return");
    private static final Identifier RETURN_INACTIVE_SPRITE = ModMain.id("widget/return_inactive");
    private static final Identifier WARNING_HIGHLIGHTED_SPRITE = new Identifier("world_list/warning_highlighted");
    protected char[] allowedChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\u0008', '\u001B'};
    public static final int imageHeight = 256;
    public static final int imageWeight = 256;
    private final KeycardBlockEntity be;
    private SignatureImpl previous;
    public LevelMode mode;
    private final List<ButtonWidget> minusButtons = new ArrayList<>();
    private EditBoxWidget signatureBox;
    private EditBoxWidget accessBox;
    private EditBoxWidget usesBox;
    private EditBoxWidget levelBox;
//    private TexturedButtonWidget setUsesButton;
    private ButtonWidget changeModeButton;
    private ButtonWidget linkButton;

    public KeycardReaderScreen(KeycardReaderMenu handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
        this.be = handler.be;
        this.previous = be.getSignature();
        this.mode = be.getLevelMode();
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    protected void init() {
        super.init();
        signatureBox = addDrawable(new EditBoxWidget(textRenderer,x + 96,y + 21,40,12,Text.empty(),Text.empty()));
        signatureBox.setMaxLength(6);
        signatureBox.setText(previous.toString());

        ButtonWidget minusThree = ButtonWidget.builder(Text.literal("---"),b -> addToSignature(-100))
                .dimensions(x+22,y+35,24,13).build();
        ButtonWidget minusTwo = ButtonWidget.builder(Text.literal("--"),b -> addToSignature(-10))
                .dimensions(x+48,y+35,18,13).build();
        ButtonWidget minusOne = ButtonWidget.builder(Text.literal("-"),b -> addToSignature(-1))
                .dimensions(x+68,y+35,12,13).build();

        TexturedButtonWidget reset = new TexturedButtonWidget(x+82,y+35,12,13,10,10,1,RESET_SPRITE,10,10,
                        b -> signatureBox.setText(previous.toString()));

        ButtonWidget plusThree = ButtonWidget.builder(Text.literal("+++"),b -> addToSignature(100))
                .dimensions(x+130,y+35,24,13).build();
        ButtonWidget plusTwo = ButtonWidget.builder(Text.literal("++"),b -> addToSignature(10))
                .dimensions(x+110,y+35,18,13).build();
        ButtonWidget plusOne = ButtonWidget.builder(Text.literal("+"),b -> addToSignature(1))
                .dimensions(x+9,y+35,12,13).build();

        TexturedButtonWidget randomize = new TexturedButtonWidget(x+156,y+35,12,13,10,10,1,RANDOM_SPRITE,10,10,b -> {
            int i = client.world.random.nextBetween(0, SignatureImpl.MAX);
            signatureBox.setText("0".repeat(SignatureImpl.SIZE - (i + "").length()) + i);
        });
        randomize.setTooltip(Tooltip.of(Text.translatable("tooltip.usecuity.keycard_reader.random")));

        accessBox = addDrawable(new EditBoxWidget(textRenderer,x + 8,y + 66,70,15,Text.empty(),Text.empty()));
        accessBox.setTooltip(Tooltip.of(Text.translatable("tooltip.usecurity.keycard_reader.players")));

        usesBox = addDrawable(new EditBoxWidget(textRenderer,x + 28,y + 107,30,15,Text.empty(),Text.empty()));
        usesBox.setTooltip(Tooltip.of(Text.translatable("tooltip.usecurity.keycard_reader.uses")));
        usesBox.setMaxLength(3);

        linkButton = ButtonWidget.builder(Text.translatable("gui.usecurity.keycard_reader.link_button"), b -> {
            SignatureImpl signature = new SignatureImpl(signatureBox.getText().getBytes());
            previous = signature;
            setSignature(signature);
            if (handler.slot.hasStack()) {
                sync(updateKeycard(handler.slot.getStack().copy()));
            }
        }).dimensions(x+8,y+126,70,20).build();

//        setUsesButton = new TexturedButtonWidget(x+62,y+106,16,17,10,10,2,RETURN_SPRITE,14,14,
//                        b -> {});
//        setUsesButton.active = false;

        changeModeButton = ButtonWidget.builder(Text.literal(mode.toString()), b -> changeMode())
                .dimensions(x+135,y+67,18,18).build();

        levelBox = addDrawable(new EditBoxWidget(textRenderer,x + 100,y + 67,30,15,Text.empty(),Text.empty()));
        levelBox.setMaxLength(2);
        levelBox.setMessage(Text.literal(be.getMinLevel()+""));

        minusButtons.addAll(List.of(minusOne,minusTwo,minusThree));
        addDrawableChilds(minusThree,minusTwo,minusOne,reset,plusOne,plusTwo,plusThree,randomize, linkButton,changeModeButton,accessBox,usesBox,levelBox);
        addDrawable(signatureBox);
    }

    private ItemStack updateKeycard(ItemStack copy) {
        List<String> players = Arrays.stream(accessBox.getText().split(", ")).toList();
        ModItems.KEYCARD.setValidPlayers(copy,players);
        ModItems.KEYCARD.setLevel(copy,Integer.parseInt(levelBox.getText()));
        ModItems.KEYCARD.setSignature(copy,SignatureImpl.fromString(signatureBox.getText()));
        ModItems.KEYCARD.setUses(copy,Integer.parseInt(usesBox.getText()));
        return copy;
    }

    private void sync(ItemStack stack) {
        ClientPlayNetworking.send(new UpdateKeycardC2SPacket(stack,be.getPos()));
        ClientPlayNetworking.send(new UpdateKeycardReaderC2SPacket(be.getPos(),SignatureImpl.fromString(signatureBox.getText()),mode,Integer.parseInt(usesBox.getText())));
    }

    @SafeVarargs
    private <T extends Element & Drawable & Selectable> void addDrawableChilds(T... ts) {
        for (T t : ts) {
            addDrawableChild(t);
        }
    }
    private void addDrawables(Drawable... drawables) {
        for (Drawable d : drawables) {
            addDrawable(d);
        }
    }

    @Override
    protected void handledScreenTick() {
//        setUsesButton.active = !usesBox.getText().isEmpty();
        if (changeModeButton != null) {
            changeModeButton.setMessage(Text.literal(mode.toString()));
            minusButtons.forEach(b -> b.active = Integer.parseInt(signatureBox.getText()) > 0);
        }
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (isValidChar(chr)) {
            levelBox.charTyped(chr,modifiers);
            usesBox.charTyped(chr,modifiers);
            client.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.15F, 1.0F);
        }
        signatureBox.charTyped(chr, modifiers);
        accessBox.charTyped(chr, modifiers);
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

    private void changeMode() {
        LevelMode[] values = LevelMode.values();
        mode = values[mode.ordinal()==values.length-1?0:mode.ordinal()+1];
    }

    @Override
    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        ctx.drawTexture(TEXTURE,x,y,0,0,imageWeight,imageHeight);
    }

    public void addToSignature(int i) {
        int j = Math.max(Math.min(Integer.parseInt(signatureBox.getText()) + i, SignatureImpl.MAX),0);
        signatureBox.setText("0".repeat(SignatureImpl.SIZE - (Math.abs(j) + "").length()) + j);
    }
    public void addToBox(EditBoxWidget widget,int i) {
        widget.setText((Integer.parseInt(widget.getText())+i)+"");
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
    public void setSignature(SignatureImpl signature) {
        signatureBox.setText(signature.toString());
    }
}
