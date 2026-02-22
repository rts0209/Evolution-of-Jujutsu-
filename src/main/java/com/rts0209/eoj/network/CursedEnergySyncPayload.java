package com.rts0209.eoj.network;

import com.rts0209.eoj.EvolutionOfJujutsu;
import com.rts0209.eoj.cursedenergy.CursedEnergyData;
import com.rts0209.eoj.cursedenergy.ModAttachments;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CursedEnergySyncPayload(int energy, int maxEnergy) implements CustomPacketPayload {

    public static final Type<CursedEnergySyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(EvolutionOfJujutsu.MOD_ID, "cursed_energy_sync"));

    public static final StreamCodec<FriendlyByteBuf, CursedEnergySyncPayload> STREAM_CODEC =
            StreamCodec.of(CursedEnergySyncPayload::encode, CursedEnergySyncPayload::decode);

    private static void encode(FriendlyByteBuf buf, CursedEnergySyncPayload payload) {
        buf.writeVarInt(payload.energy);
        buf.writeVarInt(payload.maxEnergy);
    }

    private static CursedEnergySyncPayload decode(FriendlyByteBuf buf) {
        return new CursedEnergySyncPayload(buf.readVarInt(), buf.readVarInt());
    }

    public static void handle(CursedEnergySyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player == null) {
                return;
            }

            CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);
            cursedEnergyData.setMaxEnergy(payload.maxEnergy());
            cursedEnergyData.setEnergy(payload.energy());
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
