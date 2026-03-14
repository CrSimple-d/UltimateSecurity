package net.crsimple.usecurity.compat;

import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import tschipp.carryon.Constants;

public class ModCompats {
    private static TagKey<Block> CARRYON_BLACKLIST;

    public static void init() {
        if (FabricLoaderImpl.INSTANCE.isModLoaded("carryon")) {
            CARRYON_BLACKLIST = TagKey.of(RegistryKeys.BLOCK, new Identifier(Constants.MOD_ID, "block_blacklist"));
        }
    }

    public static @Nullable TagKey<Block> getCarryonBlacklist() {
        return CARRYON_BLACKLIST;
    }
}
