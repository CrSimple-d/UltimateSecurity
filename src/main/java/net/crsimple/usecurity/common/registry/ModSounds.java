package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent ELECTRIFIED = reg("electrified");

    public static SoundEvent reg(String id) {
        Identifier sound = ModMain.id(id);
        return Registry.register(Registries.SOUND_EVENT,sound,SoundEvent.of(sound));
    }

    public static void init() {
    }
}
