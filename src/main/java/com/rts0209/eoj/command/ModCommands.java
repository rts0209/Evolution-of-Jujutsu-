package com.rts0209.eoj.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.rts0209.eoj.EvolutionOfJujutsu;
import com.rts0209.eoj.cursedenergy.CursedEnergyData;
import com.rts0209.eoj.cursedenergy.CursedEnergyEvents;
import com.rts0209.eoj.cursedenergy.ModAttachments;
import com.rts0209.eoj.cursedenergy.SorcererProgressData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = EvolutionOfJujutsu.MOD_ID)
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("eoj")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("level")
                                .then(Commands.literal("get")
                                        .executes(ModCommands::getLevel)
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("value", IntegerArgumentType.integer(1))
                                                .executes(context -> setLevel(context, IntegerArgumentType.getInteger(context, "value")))
                                        )
                                )
                                .then(Commands.literal("add")
                                        .then(Commands.argument("value", IntegerArgumentType.integer(1))
                                                .executes(context -> addLevel(context, IntegerArgumentType.getInteger(context, "value")))
                                        )
                                )
                        )
                        .then(Commands.literal("xp")
                                .then(Commands.literal("get")
                                        .executes(ModCommands::getXp)
                                )
                                .then(Commands.literal("add")
                                        .then(Commands.argument("value", IntegerArgumentType.integer(1))
                                                .executes(context -> addXp(context, IntegerArgumentType.getInteger(context, "value")))
                                        )
                                )
                        )
                        .then(Commands.literal("ce")
                                .then(Commands.literal("get")
                                        .executes(ModCommands::getCursedEnergy)
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("value", IntegerArgumentType.integer(0))
                                                .executes(context -> setCursedEnergy(context, IntegerArgumentType.getInteger(context, "value")))
                                        )
                                )
                                .then(Commands.literal("add")
                                        .then(Commands.argument("value", IntegerArgumentType.integer(0))
                                                .executes(context -> addCursedEnergy(context, IntegerArgumentType.getInteger(context, "value")))
                                        )
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("value", IntegerArgumentType.integer(0))
                                                .executes(context -> removeCursedEnergy(context, IntegerArgumentType.getInteger(context, "value")))
                                        )
                                )
                        )
        );
    }

    private static int getLevel(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        SorcererProgressData progressData = player.getData(ModAttachments.SORCERER_PROGRESS);
        context.getSource().sendSuccess(() -> Component.literal("EOJ Level: " + progressData.getLevel()), false);
        return progressData.getLevel();
    }

    private static int setLevel(CommandContext<CommandSourceStack> context, int level) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        SorcererProgressData progressData = player.getData(ModAttachments.SORCERER_PROGRESS);
        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);

        progressData.setLevel(level);
        cursedEnergyData.setMaxEnergy(CursedEnergyEvents.getMaxEnergyForLevel(level));

        CursedEnergyEvents.syncToClient(player, cursedEnergyData);

        context.getSource().sendSuccess(() -> Component.literal("EOJ Level set to " + progressData.getLevel()), true);
        return progressData.getLevel();
    }

    private static int addLevel(CommandContext<CommandSourceStack> context, int amount) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        SorcererProgressData progressData = player.getData(ModAttachments.SORCERER_PROGRESS);
        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);

        progressData.addLevels(amount);
        cursedEnergyData.setMaxEnergy(CursedEnergyEvents.getMaxEnergyForLevel(progressData.getLevel()));

        CursedEnergyEvents.syncToClient(player, cursedEnergyData);

        context.getSource().sendSuccess(() -> Component.literal("EOJ Level is now " + progressData.getLevel()), true);
        return progressData.getLevel();
    }

    private static int getXp(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        SorcererProgressData progressData = player.getData(ModAttachments.SORCERER_PROGRESS);
        int xpForNextLevel = progressData.getExperienceForNextLevel();
        context.getSource().sendSuccess(() -> Component.literal("EOJ XP: " + progressData.getExperience() + "/" + xpForNextLevel), false);
        return progressData.getExperience();
    }

    private static int addXp(CommandContext<CommandSourceStack> context, int amount) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        SorcererProgressData progressData = player.getData(ModAttachments.SORCERER_PROGRESS);
        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);

        progressData.addExperience(amount);
        cursedEnergyData.setMaxEnergy(CursedEnergyEvents.getMaxEnergyForLevel(progressData.getLevel()));

        CursedEnergyEvents.syncToClient(player, cursedEnergyData);

        context.getSource().sendSuccess(() -> Component.literal("EOJ XP is now " + progressData.getExperience() + ", level " + progressData.getLevel()), true);
        return progressData.getExperience();
    }

    private static int getCursedEnergy(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);
        context.getSource().sendSuccess(() -> Component.literal("Cursed Energy: " + cursedEnergyData.getEnergy() + "/" + cursedEnergyData.getMaxEnergy()), false);
        return cursedEnergyData.getEnergy();
    }

    private static int setCursedEnergy(CommandContext<CommandSourceStack> context, int value) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);

        cursedEnergyData.setEnergy(value);
        CursedEnergyEvents.syncToClient(player, cursedEnergyData);

        context.getSource().sendSuccess(() -> Component.literal("Cursed Energy set to " + cursedEnergyData.getEnergy()), true);
        return cursedEnergyData.getEnergy();
    }

    private static int addCursedEnergy(CommandContext<CommandSourceStack> context, int value) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);

        cursedEnergyData.addEnergy(value);
        CursedEnergyEvents.syncToClient(player, cursedEnergyData);

        context.getSource().sendSuccess(() -> Component.literal("Cursed Energy is now " + cursedEnergyData.getEnergy()), true);
        return cursedEnergyData.getEnergy();
    }

    private static int removeCursedEnergy(CommandContext<CommandSourceStack> context, int value) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        CursedEnergyData cursedEnergyData = player.getData(ModAttachments.CURSED_ENERGY);

        cursedEnergyData.removeEnergy(value);
        CursedEnergyEvents.syncToClient(player, cursedEnergyData);

        context.getSource().sendSuccess(() -> Component.literal("Cursed Energy is now " + cursedEnergyData.getEnergy()), true);
        return cursedEnergyData.getEnergy();
    }
}
