package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.api.ReinforcedManager;
import net.crsimple.usecurity.api.SecurityManager;
import net.crsimple.usecurity.api.reinforced.Reinforced;
import net.crsimple.usecurity.api.wrapper.SecurityBlockWrapper;
import net.crsimple.usecurity.common.registry.ModTags;
import net.crsimple.usecurity.compat.ModCompats;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("RawUseOfParameterized")
@Mixin(RegistryEntry.Reference.class)
public abstract class RegistryEntry$ReferenceMixin {
    @Shadow @Nullable private RegistryKey registryKey;

    @Shadow abstract void setTags(Collection<TagKey> tags);

    @Inject(method = "setTags",at = @At("HEAD"),cancellable = true)
    private void setTags(Collection<TagKey> tags, CallbackInfo ci) {
        if (Registries.BLOCK.get(registryKey.getValue()) instanceof SecurityBlockWrapper wrapper) {
            List<TagKey<Block>> list = wrapper.getWrappedBlock().getRegistryEntry().streamTags().toList();
            if (!tags.containsAll(list)) {
                tags.addAll(list);
                tags.add(ModTags.BlockTags.REINFORCED);
                if (ModCompats.getCarryonBlacklist() != null) {
                    tags.add(ModCompats.getCarryonBlacklist());
                }
                setTags(tags);
                ci.cancel();
            }
        }
    }
}
