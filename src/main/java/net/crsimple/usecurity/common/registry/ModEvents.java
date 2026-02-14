package net.crsimple.usecurity.common.registry;

import net.crsimple.usecurity.ServerCore;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class ModEvents {
    public static void init() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, be) ->
                player.isCreative() || ServerCore.shouldBreak(world,pos,player));
    }
}
