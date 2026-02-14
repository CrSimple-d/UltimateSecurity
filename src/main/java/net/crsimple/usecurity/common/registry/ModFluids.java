package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.common.fluid.FakeLava;
import net.crsimple.usecurity.common.fluid.FakeWater;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static final FlowableFluid STILL_FAKE_LAVA = (FlowableFluid) reg(new FakeLava.Still(),"fake_lava");
    public static final FlowableFluid FLOWING_FAKE_LAVA = (FlowableFluid) reg(new FakeLava.Flowing(),"flowing_fake_lava");
    public static final FlowableFluid STILL_FAKE_WATER = (FlowableFluid) reg(new FakeWater.Still(),"fake_water");
    public static final FlowableFluid FLOWING_FAKE_WATER = (FlowableFluid) reg(new FakeWater.Flowing(),"flowing_fake_water");

    private static Fluid reg(Fluid fluid, String id) {
        RegistryKey<Fluid> key = RegistryKey.of(RegistryKeys.FLUID, ModMain.id(id));
        return Registry.register(Registries.FLUID,key,fluid);
    }

    public static void init() {
    }
}
