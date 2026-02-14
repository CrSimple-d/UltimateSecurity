package net.crsimple.usecurity.common.items;

import net.crsimple.usecurity.common.registry.ModFluids;
import net.minecraft.item.BucketItem;

public class FakeLavaBucket extends BucketItem {
    public FakeLavaBucket(Settings settings) {
        super(ModFluids.STILL_FAKE_LAVA, settings);
    }
}
