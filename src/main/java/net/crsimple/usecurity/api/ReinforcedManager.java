package net.crsimple.usecurity.api;

import net.crsimple.usecurity.api.reinforced.Reinforced;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReinforcedManager {
    public static final String REINFORCED_PREFIX = "reinforced_";
    private static List<Block> REINFORCED_BLOCKS = new ArrayList<>();


    public static Block fromReinforced(Reinforced reinforced) {
        Identifier id = Registries.BLOCK.getId((Block) reinforced);
        return Registries.BLOCK.get(id.withPath(id.getPath().replaceFirst(REINFORCED_PREFIX,"")));
    }
    public static @Nullable Reinforced toReinforced(Block block) {
        Identifier id = Registries.BLOCK.getId(block);
        Block reinforced = Registries.BLOCK.get(id.withPath(REINFORCED_PREFIX + id.getPath()));
        return reinforced instanceof Reinforced r?r:null;
    }
    public static Identifier normalizeId(Identifier id) {
        if (id.getPath().startsWith(ReinforcedManager.REINFORCED_PREFIX)) {
            return id.withPath(id.getPath().replaceFirst(ReinforcedManager.REINFORCED_PREFIX, ""));
        }
        return id;
    }

    public static boolean isReinforced(Identifier id) {
        return isReinforced(Registries.BLOCK.get(Identifier.of(id.getNamespace(),id.getPath())));
    }
    public static boolean isReinforced(Block block) {
        return block instanceof Reinforced;
    }

    public static List<Block> getReinforcedBlocks() {
        return REINFORCED_BLOCKS;
    }

    public static void setReinforcedBlocks(List<Block> reinforcedBlocks) {
        REINFORCED_BLOCKS = List.copyOf(reinforcedBlocks);
    }
}
