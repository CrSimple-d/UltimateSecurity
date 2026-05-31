package net.crsimple.usecurity.api;

import net.crsimple.usecurity.ModMain;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public interface SignatureProtected<T extends Signature> extends Protected {
    String SIGNATURE_KEY = ModMain.createKey("signature");

    T getSignature();
    void setSignature(T signature);

    default boolean checkSignature(byte[] bytes) {
        return bytes != null && hasSignature() && getSignature().validate(bytes);
    }

    default boolean hasSignature() {
        return getSignature() != null && getSignature().hasSignature();
    }

    default void saveSignature(NbtCompound nbt) {
        if (hasSignature()) {
            nbt.put(SIGNATURE_KEY, getSignature().saveToNbt(new NbtCompound()));
        }
    }
    default @Nullable T deserializeSignature(NbtCompound nbt) {
        return null;
    }
}
