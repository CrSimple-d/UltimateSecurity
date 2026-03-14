package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ModMain;
import net.crsimple.usecurity.common.screen.KeycardReaderScreen;
import net.crsimple.usecurity.common.screen.container.KeycardReaderMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {

    public static ScreenHandlerType<KeycardReaderMenu> KEYCARD_READER = regMenu("keycard_reader",KeycardReaderMenu::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> regMenu(String id, ScreenHandlerRegistry.ExtendedClientHandlerFactory<T> factory) {
        return ScreenHandlerRegistry.registerExtended(Identifier.of(ModMain.ID,id),factory);
    }

    public static void init() {
        HandledScreens.register(KEYCARD_READER, KeycardReaderScreen::new);
    }
}
