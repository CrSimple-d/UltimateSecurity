package net.crsimple.usecurity.api.reinforced;

import net.crsimple.usecurity.api.SecurityBlockEntity;
import net.crsimple.usecurity.api.owner.OwnableBlock;

public interface Reinforced extends OwnableBlock {
    default boolean shouldInteract(SecurityBlockEntity be) {
        return false;
    }
    default boolean shouldBreak(SecurityBlockEntity be) {
        return false;
    }
}
