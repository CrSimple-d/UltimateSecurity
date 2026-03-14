package net.crsimple.usecurity.api;

import net.minecraft.nbt.NbtCompound;

import java.nio.charset.StandardCharsets;

public class SignatureImpl implements Signature {
    public static final int MAX = 999999;
    public static final int SIZE = 6;
    public static final SignatureImpl EMPTY = new SignatureImpl();
    private final byte[] code = new byte[size()];

    public SignatureImpl(byte[] code) {
        System.arraycopy(code, 0, this.code, 0, Math.min(code.length, this.code.length));
    }

    private SignatureImpl() {
        this("0".repeat(6).getBytes());
    }

    @Override
    public byte[] code() {
        return code;
    }

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public boolean hasSignature() {
        return this != EMPTY;
    }

    @Override
    public String toString() {
        return new String(code, StandardCharsets.UTF_8);
    }

    public static SignatureImpl fromNbt(NbtCompound nbt) {
        byte[] code = nbt.getByteArray(CODE_KEY);
        return code != null ? new SignatureImpl(code) : null;
    }
    public static SignatureImpl fromString(String s) {
        return new SignatureImpl(s.getBytes());
    }
}
