package com.rts0209.eoj.network;

import com.rts0209.eoj.EvolutionOfJujutsu;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = EvolutionOfJujutsu.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetworking {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                CursedEnergySyncPayload.TYPE,
                CursedEnergySyncPayload.STREAM_CODEC,
                CursedEnergySyncPayload::handle
        );
    }
}
