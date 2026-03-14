package net.crsimple.usecurity.api.keycard;

import net.crsimple.usecurity.api.ProtectedBlock;

public abstract class KeycardBlock extends ProtectedBlock<KeycardBlockEntity> {
    public KeycardBlock(Settings settings) {
        super(settings);
    }
}
