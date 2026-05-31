package net.crsimple.usecurity.api.reinforced;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.api.owner.OwnableBlock;
import net.minecraft.entity.player.PlayerEntity;

public interface Reinforced extends OwnableBlock {
    default boolean shouldInteract(SecurityBlockEntity be, PlayerEntity p) {
        return false;
    }
    default boolean shouldBreak(SecurityBlockEntity be, PlayerEntity p) {
        return false;
    }
}
