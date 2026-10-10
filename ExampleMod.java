package com.example;

import com.example.gui.SmartBaritoneScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ExampleMod implements ClientModInitializer {

    private static KeyBinding openMenuKeyBinding;

    @Override
    public void onInitializeClient() {
        openMenuKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.smartbaritone.open_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.smartbaritone.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKeyBinding.wasPressed()) {
                if (client.player != null && client.currentScreen == null) {
                    client.setScreen(new SmartBaritoneScreen());
                }
            }

            SmartBaritoneController.tick(client);
        });
    }
}

