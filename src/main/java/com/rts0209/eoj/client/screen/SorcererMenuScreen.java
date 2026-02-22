package com.rts0209.eoj.client.screen;

import com.rts0209.eoj.cursedenergy.ModAttachments;
import com.rts0209.eoj.cursedenergy.SorcererProgressData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SorcererMenuScreen extends Screen {

    private static final int MENU_WIDTH = 260;
    private static final int MENU_HEIGHT = 150;

    public SorcererMenuScreen() {
        super(Component.translatable("screen.evolution_of_jujutsu.menu.title"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        Font font = Minecraft.getInstance().font;

        int left = (width - MENU_WIDTH) / 2;
        int top = (height - MENU_HEIGHT) / 2;
        int right = left + MENU_WIDTH;
        int bottom = top + MENU_HEIGHT;

        int leftPanelWidth = 88;
        int rightPanelX = left + leftPanelWidth + 8;

        guiGraphics.fill(left, top, right, bottom, 0xCC121212);
        guiGraphics.fill(left, top, right, top + 2, 0xFFFFFFFF);
        guiGraphics.fill(left, bottom - 2, right, bottom, 0xFFFFFFFF);
        guiGraphics.fill(left, top, left + 2, bottom, 0xFFFFFFFF);
        guiGraphics.fill(right - 2, top, right, bottom, 0xFFFFFFFF);

        guiGraphics.fill(left + leftPanelWidth, top + 2, left + leftPanelWidth + 2, bottom - 2, 0x66FFFFFF);

        String username = minecraft != null && minecraft.player != null ? minecraft.player.getName().getString() : "Unknown";
        guiGraphics.drawString(font, username, left + 10, top + 8, 0xFFFFFF, false);

        int viewerX = left + 10;
        int viewerY = top + 24;
        int viewerW = 66;
        int viewerH = 78;
        guiGraphics.fill(viewerX, viewerY, viewerX + viewerW, viewerY + viewerH, 0xFF2A2A2A);
        guiGraphics.fill(viewerX, viewerY, viewerX + viewerW, viewerY + 2, 0xFFFFFFFF);
        guiGraphics.fill(viewerX, viewerY + viewerH - 2, viewerX + viewerW, viewerY + viewerH, 0xFFFFFFFF);
        guiGraphics.fill(viewerX, viewerY, viewerX + 2, viewerY + viewerH, 0xFFFFFFFF);
        guiGraphics.fill(viewerX + viewerW - 2, viewerY, viewerX + viewerW, viewerY + viewerH, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, Component.translatable("screen.evolution_of_jujutsu.menu.player_viewer"), viewerX + viewerW / 2, viewerY + viewerH / 2 - 4, 0xE0E0E0);

        int gradeIconX = left + 28;
        int gradeIconY = top + 110;
        int gradeIconSize = 18;
        guiGraphics.fill(gradeIconX, gradeIconY, gradeIconX + gradeIconSize, gradeIconY + gradeIconSize, 0xFF303030);
        guiGraphics.fill(gradeIconX, gradeIconY, gradeIconX + gradeIconSize, gradeIconY + 2, 0xFFFFFFFF);
        guiGraphics.fill(gradeIconX, gradeIconY + gradeIconSize - 2, gradeIconX + gradeIconSize, gradeIconY + gradeIconSize, 0xFFFFFFFF);
        guiGraphics.fill(gradeIconX, gradeIconY, gradeIconX + 2, gradeIconY + gradeIconSize, 0xFFFFFFFF);
        guiGraphics.fill(gradeIconX + gradeIconSize - 2, gradeIconY, gradeIconX + gradeIconSize, gradeIconY + gradeIconSize, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, Component.translatable("screen.evolution_of_jujutsu.menu.grade"), left + 43, gradeIconY + 24, 0xFFFFFF);

        SorcererProgressData progressData = minecraft != null && minecraft.player != null
                ? minecraft.player.getData(ModAttachments.SORCERER_PROGRESS)
                : new SorcererProgressData();

        String techniqueName = Component.translatable("screen.evolution_of_jujutsu.menu.technique_name_value").getString();
        String techniqueLevel = Component.translatable("screen.evolution_of_jujutsu.menu.technique_level_value", progressData.getLevel()).getString();

        guiGraphics.drawString(font, Component.translatable("screen.evolution_of_jujutsu.menu.technique_name_label"), rightPanelX, top + 10, 0xD0D0D0, false);
        guiGraphics.fill(rightPanelX, top + 22, right - 10, top + 40, 0xFF202020);
        guiGraphics.drawString(font, techniqueName, rightPanelX + 6, top + 28, 0xFFFFFF, false);

        int techniqueIconX = rightPanelX;
        int techniqueIconY = top + 48;
        int techniqueIconSize = 56;
        guiGraphics.fill(techniqueIconX, techniqueIconY, techniqueIconX + techniqueIconSize, techniqueIconY + techniqueIconSize, 0xFF252525);
        guiGraphics.fill(techniqueIconX, techniqueIconY, techniqueIconX + techniqueIconSize, techniqueIconY + 2, 0xFFFFFFFF);
        guiGraphics.fill(techniqueIconX, techniqueIconY + techniqueIconSize - 2, techniqueIconX + techniqueIconSize, techniqueIconY + techniqueIconSize, 0xFFFFFFFF);
        guiGraphics.fill(techniqueIconX, techniqueIconY, techniqueIconX + 2, techniqueIconY + techniqueIconSize, 0xFFFFFFFF);
        guiGraphics.fill(techniqueIconX + techniqueIconSize - 2, techniqueIconY, techniqueIconX + techniqueIconSize, techniqueIconY + techniqueIconSize, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, Component.translatable("screen.evolution_of_jujutsu.menu.technique_icon"), techniqueIconX + techniqueIconSize / 2, techniqueIconY + techniqueIconSize / 2 - 4, 0xE0E0E0);

        guiGraphics.drawString(font, Component.translatable("screen.evolution_of_jujutsu.menu.technique_level_label"), rightPanelX + 66, top + 86, 0xD0D0D0, false);
        guiGraphics.fill(rightPanelX + 66, top + 96, right - 10, top + 108, 0xFF202020);
        guiGraphics.drawString(font, techniqueLevel, rightPanelX + 70, top + 98, 0xFFFFFF, false);

        guiGraphics.fill(rightPanelX, bottom - 34, right - 10, bottom - 8, 0xFF202020);
        guiGraphics.drawString(font, Component.translatable("screen.evolution_of_jujutsu.menu.technique_description"), rightPanelX + 6, bottom - 28, 0xFFFFFF, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
