package net.crsimple.usecurity.api.keycard;

import net.crsimple.usecurity.common.registry.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import static net.crsimple.usecurity.api.keycard.ValidationResult.*;

public interface KeycardPredicate {
    ValidationResult test(KeycardProtected kp, PlayerEntity p, ItemStack s);

    static KeycardPredicate checkKeycardValid() {
        return (kp, p, s) -> INVALID_KEYCARD.orSuccess(ModItems.KEYCARD.isKeycardValid(s));
    }
    static KeycardPredicate checkPlayersValid() {
        return (kp, p, s) -> INVALID_PLAYER.orSuccess(ModItems.KEYCARD.getValidPlayers(s).contains(p.getName().getString()));
    }
    static KeycardPredicate checkSignature() {
        return (kp, p, s) -> SIGNATURE_ERROR.orSuccess(kp.checkSignature(ModItems.KEYCARD.getSignature(s)));
    }
    static KeycardPredicate checkLevel() {
        return (kp, p, s) -> INVALID_LEVEL.orSuccess(kp.getLevelMode().test(ModItems.KEYCARD.getLevel(s),kp.getMinLevel()));
    }
    static KeycardPredicate checkUses() {
        return (kp, p, s) -> EXPIRED.orSuccess(ModItems.KEYCARD.hasUses(s));
    }
}
