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

    private static final ResourceLocation BAR =
            ResourceLocation.fromNamespaceAndPath(
                    EvolutionOfJujutsu.MOD_ID,
                    "textures/gui/ce_bar.png"
            );

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);

        GuiGraphics gui = event.getGuiGraphics();

        int energy = cursedEnergyData.getEnergy();
        int maxEnergy = cursedEnergyData.getMaxEnergy();

        int width = 81;
        int height = 9;

        float fillRatio = maxEnergy <= 0 ? 0.0F : Math.min(1.0F, energy / (float) maxEnergy);

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x = screenWidth / 2 - 91;
        int y = screenHeight - 59;

        gui.blit(BAR, x, y, 0, 0, width, height);

        int filled = Math.round(fillRatio * width);
        if (filled > 0) {
            gui.blit(BAR, x, y, 0, 9, filled, height);
        }

        Font font = mc.font;
        String energyText = energy + " / " + maxEnergy;
        int textX = x + width + 6;
        int textY = y + (height - font.lineHeight) / 2;
        gui.drawString(font, energyText, textX, textY, 0xC7D7FF, true);
    }
}
