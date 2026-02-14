package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes {
    public static RegistryKey<DamageType> FAKE_WATER = reg("fake_water");
    public static RegistryKey<DamageType> ELECTRICITY = reg("electricity");

    private static RegistryKey<DamageType> reg(String id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(ModMain.ID,id));
    }
    public static DamageSource of(World world, RegistryKey<DamageType> type) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(type));
    }

    public static void init() {
    }
}
