package net.crsimple.usecurity.api;

import net.crsimple.usecurity.ModMain;
import net.minecraft.nbt.NbtCompound;

public interface Signature {
    String CODE_KEY = ModMain.createKey("code");

    byte[] code();

    boolean equals(Object other);

    default int size() {
        return code().length;
    }

    default boolean hasSignature() {
        return size() > 0;
    }

    default NbtCompound saveToNbt(NbtCompound nbt) {
        nbt.putByteArray(CODE_KEY,code());
        return nbt;
    }
}
