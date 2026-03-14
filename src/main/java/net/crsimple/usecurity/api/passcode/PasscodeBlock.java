package net.crsimple.usecurity.api.passcode;

import net.crsimple.usecurity.api.ProtectedBlock;

public abstract class PasscodeBlock extends ProtectedBlock<BlockEntityWithCode> {
    public PasscodeBlock(Settings settings) {
        super(settings);
    }
}
