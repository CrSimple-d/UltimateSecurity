package net.crsimple.usecurity.common.items;

import net.crsimple.usecurity.common.registry.ModFluids;
import net.minecraft.item.BucketItem;

public class FakeWaterBucket extends BucketItem {
    public FakeWaterBucket(Settings settings) {
        super(ModFluids.STILL_FAKE_WATER, settings);
    }
}
