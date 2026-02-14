package net.crsimple.usecurity.api.owner;

import net.crsimple.usecurity.ModMain;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public interface OwnerProvider {
    String OWNER_UUID_KEY = ModMain.createKey("owner_uuid");
    String OWNER_NAME_KEY = ModMain.createKey("owner_name");

    String ownerName();
    UUID ownerId();
    void changeOwner(UUID id,String name);

    default boolean hasOwner() {
        return ownerId() != null && ownerName() != null;
    }
    default NbtCompound saveOwner(NbtCompound nbt) {
        if (hasOwner()) {
            nbt.putUuid(OWNER_UUID_KEY, ownerId());
            nbt.putString(OWNER_NAME_KEY, ownerName());
        }
        return nbt;
    }
}
