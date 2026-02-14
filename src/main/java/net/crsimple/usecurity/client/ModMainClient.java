package net.crsimple.usecurity.client;

import net.fabricmc.api.ClientModInitializer;

public class ModMainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModRenderer.init();
    }
}
