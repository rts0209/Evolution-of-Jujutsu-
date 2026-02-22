package com.rts0209.eoj.cursedenergy;

import com.rts0209.eoj.EvolutionOfJujutsu;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, EvolutionOfJujutsu.MOD_ID);

    public static final Supplier<AttachmentType<CursedEnergyData>> CURSED_ENERGY =
            ATTACHMENTS.register("cursed_energy",
                    () -> AttachmentType.builder(CursedEnergyData::new).build()
            );

    public static final Supplier<AttachmentType<SorcererProgressData>> SORCERER_PROGRESS =
            ATTACHMENTS.register("sorcerer_progress",
                    () -> AttachmentType.builder(SorcererProgressData::new).build()
            );
}
