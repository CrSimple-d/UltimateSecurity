package net.crsimple.usecurity;

import net.crsimple.usecurity.api.reflection.ReflectionApi;
import net.crsimple.usecurity.common.registry.*;
import net.crsimple.usecurity.compat.ModCompats;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModMain implements ModInitializer {
    public static final String ID = "usecurity";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);
    public static final boolean LOADING_EXCEPTIONS = false;

    @Override
    public void onInitialize() {
        ModGroups.init();
        ModFluids.init();
        ModItems.init();
        ModTags.init();
        ModBlocks.init();
        ModBlockEntities.init();
        ModScreens.init();
        ModSounds.init();
        ModEvents.init();
        ModDamageTypes.init();
        ModNetworking.init();
        ModCompats.init();
        ReflectionApi.init();
        LOGGER.info("SecurityCraft: Refabricated Initialized");
    }

    public static Identifier id(String res) {
        return Identifier.of(ID,res);
    }
    public static String createKey(String key) {
        return String.format("%s:%s",ID,key);
    }
}
