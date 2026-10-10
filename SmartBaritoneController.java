package com.example;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;

public class SmartBaritoneController {
    public enum State {
        IDLE,
        MINING,
        SELLING
    }

    private static State currentState = State.IDLE;

    public static State getCurrentState() {
        return currentState;
    }

    public static void startMining() {
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        baritone.getMineProcess().mine(
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.GOLD_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.ANCIENT_DEBRIS
        );
        currentState = State.MINING;
    }

    public static void stopAll() {
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        baritone.getPathingBehavior().cancel();
        baritone.getMineProcess().cancel();
        currentState = State.IDLE;
    }

    public static void tick(MinecraftClient client) {
        if (client.player == null) return;

        if (currentState == State.MINING && client.player.getInventory().getEmptySlot() == -1) {
            stopAll();
            currentState = State.SELLING;

            if (client.player.networkHandler != null) {
                client.player.networkHandler.sendChatMessage("/sell all");
            }
            
            currentState = State.IDLE;
        }
    }
}

