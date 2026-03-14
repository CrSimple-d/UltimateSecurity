package net.crsimple.usecurity.api.keycard;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public enum ValidationResult {
    SUCCESS("message.usecurity.keycard.success"),
    SIGNATURE_ERROR("message.usecurity.keycard.signature_error"),
    INVALID_KEYCARD("message.usecurity.keycard.invalid"),
    INVALID_LEVEL("message.usecurity.keycard.invalid_level"),
    INVALID_PLAYER("message.usecurity.keycard.invalid_player"),
    EXPIRED("message.usecurity.keycard.expired");

    public final String translationKey;

    ValidationResult(String translationKey) {
        this.translationKey = translationKey;
    }

    public MutableText getMessage() {
        return Text.translatable(translationKey);
    }
    public boolean asBool() {
        return this == SUCCESS;
    }
    public ValidationResult orSuccess(boolean b) {
        return b?SUCCESS:this;
    }
}
