package com.rts0209.eoj.client;

import com.rts0209.eoj.EvolutionOfJujutsu;
import com.rts0209.eoj.client.screen.SorcererMenuScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = EvolutionOfJujutsu.MOD_ID, value = Dist.CLIENT)
public class ModKeyMappings {

    public static final KeyMapping MENU_KEY = new KeyMapping(
            "key.evolution_of_jujutsu.menu",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_M,
            "key.categories.evolution_of_jujutsu"
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(MENU_KEY);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        while (MENU_KEY.consumeClick()) {
            if (minecraft.player == null || minecraft.screen != null) {
                continue;
            }

            minecraft.setScreen(new SorcererMenuScreen());
        }
    }
}
