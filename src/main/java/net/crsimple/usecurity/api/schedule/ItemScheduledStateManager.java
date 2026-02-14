package net.crsimple.usecurity.api.schedule;

import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class ItemScheduledStateManager extends Thread {
    public static final String STATE = "CustomModelData";
    private final MinecraftServer server;
    private final ItemStack stack;
    private final List<ScheduleValue> schedule;

    private ItemScheduledStateManager(MinecraftServer server, ItemStack stack, List<ScheduleValue> schedule) {
        this.server = server;
        this.stack = stack;
        this.schedule = schedule;
    }

    @Override
    public void run() {
        for (ScheduleValue val : schedule) {
            try {
                server.execute(() -> val.apply(stack));
                synchronized (this) {
                    this.wait(val.millis);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Builder builder(ServerWorld world, ItemStack stack) {
        return builder(world.getServer(),stack);
    }
    public static Builder builder(MinecraftServer server, ItemStack stack) {
        return new Builder(stack,server);
    }

    public record ScheduleValue(int state, long millis) {
        public void apply(ItemStack stack) {
            stack.getOrCreateNbt().putInt(STATE, state);
        }
    }

    public static class Builder {
        private final ItemStack stack;
        private final MinecraftServer server;
        private final List<ScheduleValue> schedule;

        public Builder(ItemStack stack,MinecraftServer server) {
            this.server = server;
            this.stack = stack;
            this.schedule = new ArrayList<>();
        }

        public Builder schedule(int state,long millis) {
            schedule.add(new ScheduleValue(state,millis));
            return this;
        }
        public ItemScheduledStateManager build() {
            return new ItemScheduledStateManager(server,stack,schedule);
        }
    }
}
