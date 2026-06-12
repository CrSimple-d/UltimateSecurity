package net.crsimple.usecurity.mixin;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.api.SecurityManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Deprecated
@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Redirect(method = "getDroppedStacks",at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/loot/context/LootContextParameterSet$Builder;)Ljava/util/List;"))
    private List<ItemStack> getDroppedStacks(Block instance, BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> original = instance.getDroppedStacks(state, builder);
        if (!SecurityManager.isSecurity(instance) && SecurityManager.isReinforced(instance)) {
            original.add(instance.asItem().getDefaultStack());
            ModMain.LOGGER.info("loaded loot table of reinforced resource: {}", Registries.BLOCK.getId(instance));
        }
        return original;
    }
}
