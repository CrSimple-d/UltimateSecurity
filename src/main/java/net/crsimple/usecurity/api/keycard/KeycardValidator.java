package net.crsimple.usecurity.api.keycard;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static net.crsimple.usecurity.api.keycard.KeycardPredicate.*;

public class KeycardValidator {
    public static final KeycardValidator ALL = new KeycardValidator(List.of(checkKeycardValid(), checkSignature(), checkLevel(), checkPlayersValid(), checkUses()));
    private final List<KeycardPredicate> predicates;

    private KeycardValidator(List<KeycardPredicate> predicates) {
        this.predicates = predicates;
    }

    public ValidationResult validate(KeycardProtected keycardProtected, PlayerEntity player, ItemStack stack) {
        for (KeycardPredicate p : predicates) {
            ValidationResult tested = p.test(keycardProtected, player, stack);
            if(!tested.asBool()) {
                return tested;
            }
        }
        return ValidationResult.SUCCESS;
    }

    public static KeycardValidator.Builder builder() {
        return new Builder();
    }
    public List<KeycardPredicate> getPredicates() {
        return predicates;
    }

    public static class Builder {
        private final List<KeycardPredicate> predicates;

        public Builder() {
            this.predicates = new ArrayList<>();
        }

        public Builder add(KeycardPredicate predicate) {
            predicates.add(predicate);
            return this;
        }

        public KeycardValidator build() {
            if (predicates.isEmpty()) {
                throw new IllegalStateException("predicates is empty");
            }
            return new KeycardValidator(predicates);
        }
    }
}
