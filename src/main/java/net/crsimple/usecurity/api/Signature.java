package net.crsimple.usecurity.api;

import net.crsimple.usecurity.ModMain;
import net.minecraft.nbt.NbtCompound;

import java.util.Arrays;

public interface Signature {
    String CODE_KEY = ModMain.createKey("hash");
    String SIZE_KEY = ModMain.createKey("size");

    byte[] asBytes();

    default boolean validate(byte[] other) {
        return Arrays.equals(asBytes(),other);
    }

    default int size() {
        return asBytes().length;
    }

    default boolean hasSignature() {
        return size() > 0;
    }

    default NbtCompound saveToNbt(NbtCompound nbt) {
        nbt.putByteArray(CODE_KEY, asBytes());
        nbt.putInt("size",size());
        return nbt;
    }
}
