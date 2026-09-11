package net.crsimple.usecurity.api;

import at.favre.lib.crypto.bcrypt.BCrypt;
import net.minecraft.nbt.NbtCompound;

import java.nio.charset.StandardCharsets;

public class Passcode implements Signature {
    private final byte[] hash;
    private final int size;

    private Passcode(byte[] hash, int size) {
        this.hash = hash;
        this.size = size;
    }

    public Passcode(byte[] code) {
        this(encrypt(new String(code)),code.length);
    }

    @Override
    public String toString() {
        return new String(hash);
    }

    public static Passcode fromNbt(NbtCompound nbt) {
        byte[] hash = nbt.getByteArray(CODE_KEY);
        int size = nbt.getInt(SIZE_KEY);
        return hash != null?new Passcode(hash,size):null;
    }

    private static byte[] encrypt(String code) {
        return BCrypt.withDefaults().hash(12,code.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validate(byte[] code) {
        return BCrypt.verifyer().verify(code,hash).verified;
    }

    @Override
    public byte[] asBytes() {
        return hash;
    }
}
