package com.example.gui;

import com.example.SmartBaritoneController;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class SmartBaritoneScreen extends Screen {

    public SmartBaritoneScreen() {
        super(Text.literal("Smart Baritone Control"));
    }

    @Override
    protected void init() {
        int btnWidth = 140;
        int btnHeight = 20;
        int cX = this.width / 2;
        int cY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("⛏️ Bắt đầu Auto Mine"), 
            button -> {
                SmartBaritoneController.startMining();
                this.close();
            })
            .dimensions(cX - 145, cY - 30, btnWidth, btnHeight)
            .build()
        );

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("💰 Bán nhanh (/sell all)"), 
            button -> {
                if (this.client != null && this.client.player != null) {
                    this.client.player.networkHandler.sendChatMessage("/sell all");
                }
                this.close();
            })
            .dimensions(cX + 5, cY - 30, btnWidth, btnHeight)
            .build()
        );

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🛑 Dừng Baritone (STOP)"), 
            button -> {
                SmartBaritoneController.stopAll();
                this.close();
            })
            .dimensions(cX - 145, cY, btnWidth, btnHeight)
            .build()
        );

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🏠 Về /home"), 
            button -> {
                if (this.client != null && this.client.player != null) {
                    this.client.player.networkHandler.sendChatMessage("/home");
                }
                this.close();
            })
            .dimensions(cX + 5, cY, btnWidth, btnHeight)
            .build()
        );

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Thoát Menu"), 
            button -> this.close())
            .dimensions(cX - 70, cY + 40, 140, btnHeight)
            .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(
            this.textRenderer, 
            "=== MATEO SMART BARITONE ===", 
            this.width / 2, 
            this.height / 2 - 60, 
            0x00FF88
        );

        String statusText = "Trạng thái: " + SmartBaritoneController.getCurrentState().name();
        context.drawCenteredTextWithShadow(
            this.textRenderer, 
            statusText, 
            this.width / 2, 
            this.height / 2 + 25, 
            0xAAAAAA
        );

        super.render(context, mouseX, mouseY, delta);
    }
}

