package com.rts0209.eoj.cursedenergy;

import com.rts0209.eoj.EvolutionOfJujutsu;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = EvolutionOfJujutsu.MOD_ID)
public class CursedEnergyEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        SorcererProgressData progressData = player.getData(ModAttachments.SORCERER_PROGRESS);
        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);

        cursedEnergyData.setMaxEnergy(getMaxEnergyForLevel(progressData.getLevel()));

        if (cursedEnergyData.isFull()) {
            return;
        }

        int regenInterval = getRegenIntervalTicksForLevel(progressData.getLevel());
        if (player.tickCount % regenInterval == 0) {
            cursedEnergyData.addEnergy(getRegenAmountForLevel(progressData.getLevel()));
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }

        Player original = event.getOriginal();
        Player clone = event.getEntity();

        SorcererProgressData originalProgress = original.getData(ModAttachments.SORCERER_PROGRESS);
        SorcererProgressData cloneProgress = clone.getData(ModAttachments.SORCERER_PROGRESS);
        cloneProgress.setLevel(originalProgress.getLevel());
        cloneProgress.addExperience(originalProgress.getExperience());

        CursedEnergyData originalEnergy = original.getData(ModAttachments.CURSED_ENERGY);
        CursedEnergyData cloneEnergy = clone.getData(ModAttachments.CURSED_ENERGY);
        cloneEnergy.setMaxEnergy(originalEnergy.getMaxEnergy());
        cloneEnergy.setEnergy(originalEnergy.getEnergy());
    }

    public static int getMaxEnergyForLevel(int level) {
        int clampedLevel = Math.max(1, level);
        if (clampedLevel <= 5) {
            return 100 + (clampedLevel - 1) * 150;
        }
        return 700 + (clampedLevel - 5) * 150;
    }

    public static int getRegenAmountForLevel(int level) {
        int clampedLevel = Math.max(1, level);
        if (clampedLevel <= 1) {
            return 5;
        }
        if (clampedLevel >= 5) {
            return 12 + (clampedLevel - 5) * 2;
        }
        return Math.round(5 + (clampedLevel - 1) * (7F / 4F));
    }

    public static int getRegenIntervalTicksForLevel(int level) {
        int clampedLevel = Math.max(1, level);
        if (clampedLevel <= 1) {
            return 40;
        }
        if (clampedLevel >= 5) {
            return 20;
        }
        return Math.max(20, Math.round(40 - (clampedLevel - 1) * (20F / 4F)));
    }
}
