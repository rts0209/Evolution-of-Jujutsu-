package com.rts0209.eoj.cursedenergy;

import com.rts0209.eoj.EvolutionOfJujutsu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = EvolutionOfJujutsu.MOD_ID, value = Dist.CLIENT)
public class CursedEnergyHud {

    private static final ResourceLocation BAR_BORDER =
            ResourceLocation.fromNamespaceAndPath(EvolutionOfJujutsu.MOD_ID, "textures/gui/ce_bar_border.png");

    private static final ResourceLocation BAR_FILL =
            ResourceLocation.fromNamespaceAndPath(EvolutionOfJujutsu.MOD_ID, "textures/gui/ce_bar_fill.png");

    private static final int INNER_WIDTH = 81;
    private static final int INNER_HEIGHT = 9;
    private static final int BORDER_WIDTH = 89;
    private static final int BORDER_HEIGHT = 17;
    private static final int FILL_OFFSET_X = 4;
    private static final int FILL_OFFSET_Y = 4;

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);
        GuiGraphics gui = event.getGuiGraphics();

        int energy = cursedEnergyData.getEnergy();
        int maxEnergy = cursedEnergyData.getMaxEnergy();
        float fillRatio = maxEnergy <= 0 ? 0.0F : Math.min(1.0F, energy / (float) maxEnergy);

        float fillRatio = maxEnergy <= 0 ? 0.0F : Math.min(1.0F, energy / (float) maxEnergy);

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int borderX = screenWidth / 2 - 95;
        int borderY = screenHeight - 67;

        int fillX = borderX + FILL_OFFSET_X;
        int fillY = borderY + FILL_OFFSET_Y;

        int filledWidth = Math.round(fillRatio * INNER_WIDTH);
        if (filledWidth > 0) {
            gui.blit(BAR_FILL, fillX, fillY, 0, 0, filledWidth, INNER_HEIGHT, INNER_WIDTH, INNER_HEIGHT);
        }

        gui.blit(BAR_BORDER, borderX, borderY, 0, 0, BORDER_WIDTH, BORDER_HEIGHT, BORDER_WIDTH, BORDER_HEIGHT);

        Font font = mc.font;
        String energyText = energy + " / " + maxEnergy;
        int textX = borderX + BORDER_WIDTH + 6;
        int textY = borderY + (BORDER_HEIGHT - font.lineHeight) / 2;
        gui.drawString(font, energyText, textX, textY, 0xC7D7FF, true);
    }
}
