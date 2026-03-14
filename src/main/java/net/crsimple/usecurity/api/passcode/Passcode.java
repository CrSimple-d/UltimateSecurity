package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.api.Signature;
import net.minecraft.nbt.NbtCompound;

import java.util.Arrays;

public record Passcode(byte[] code, int size) implements Signature {

    public Passcode(byte[] code) {
        this(code,code.length);
    }

    public boolean validate(byte[] other) {
        return code.length == other.length && Arrays.equals(code,other);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Passcode passcode && validate(passcode.code()));
    }

    @Override
    public String toString() {
        return Arrays.toString(code);
    }

    public static Passcode fromNbt(NbtCompound nbt) {
        byte[] code = nbt.getByteArray(CODE_KEY);
        return code != null?new Passcode(code,code.length):null;
    }
}
