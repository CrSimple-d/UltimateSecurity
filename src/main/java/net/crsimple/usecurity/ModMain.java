package net.crsimple.usecurity;

import net.crsimple.usecurity.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModMain implements ModInitializer {
    public static final String ID = "usecurity";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);

    @Override
    public void onInitialize() {
        ModGroups.init();
        ModFluids.init();
        ModItems.init();
        ModItemTags.init();
        ModBlocks.init();
        ModBlockEntities.init();
        ModSounds.init();
        ModEvents.init();
        ModDamageTypes.init();
        ModNetworking.init();
        LOGGER.info("SecurityCraft: Refabricated Initialized");
    }

    public static Identifier id(String res) {
        return Identifier.of(ID,res);
    }
    public static String createKey(String key) {
        return String.format("%s:%s",ID,key);
    }
}
