package de.thzockt.thUtils.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.thzockt.thUtils.APIs.InstanceAPI.InstanceAPI;
import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import de.thzockt.thUtils.APIs.TimerAPI.ColorType;
import de.thzockt.thUtils.APIs.TimerAPI.Style;
import de.thzockt.thUtils.APIs.TimerAPI.TimerAPI;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandTimer {

    public static LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("timer")
            .then(Commands.literal("resume")
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();
                        // checking if sender is a player
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        Player player = (Player) sender;

                        if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                            return Command.SINGLE_SUCCESS;
                        }

                        UUID instanceUUID;
                        instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                        if (!TimerAPI.isPaused(instanceUUID)) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_ALREADY_RESUMED);
                            return Command.SINGLE_SUCCESS;
                        }

                        TimerAPI.setStartingTime(instanceUUID);
                        TimerAPI.setPaused(instanceUUID, false);
                        TimerAPI.setFrozen(instanceUUID, false);

                        MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_RESUMED);
                        return Command.SINGLE_SUCCESS;
                            }))
            .then(Commands.literal("pause")
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();
                        // checking if sender is a player
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        Player player = (Player) sender;

                        if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                            return Command.SINGLE_SUCCESS;
                        }

                        UUID instanceUUID;
                        instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                        if (TimerAPI.isPaused(instanceUUID)) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_ALREADY_PAUSED);
                            return Command.SINGLE_SUCCESS;
                        }

                        TimerAPI.setTime(instanceUUID, TimerAPI.getTime(instanceUUID));
                        TimerAPI.setPaused(instanceUUID, true);

                        MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_PAUSED);
                        return Command.SINGLE_SUCCESS;
                    }))
            .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();
                        // checking if sender is a player
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        Player player = (Player) sender;

                        if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                            return Command.SINGLE_SUCCESS;
                        }

                        UUID instanceUUID;
                        instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                        if (TimerAPI.isPaused(instanceUUID)) {
                            TimerAPI.setStartingTime(instanceUUID);
                            TimerAPI.setPaused(instanceUUID, false);
                            TimerAPI.setFrozen(instanceUUID, false);
                        } else {
                            TimerAPI.setTime(instanceUUID, TimerAPI.getTime(instanceUUID));
                            TimerAPI.setPaused(instanceUUID, true);
                        }

                        MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_TOGGLED);
                        return Command.SINGLE_SUCCESS;
                    }))
            .then(Commands.literal("reset")
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();
                        // checking if sender is a player
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        Player player = (Player) sender;

                        if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                            return Command.SINGLE_SUCCESS;
                        }

                        UUID instanceUUID;
                        instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                        TimerAPI.setTime(instanceUUID, 0);
                        TimerAPI.setPaused(instanceUUID, true);

                        MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_RESET);
                        return Command.SINGLE_SUCCESS;
                    }))
            .then(Commands.literal("reverse")
                    .then(Commands.argument("reverse", BoolArgumentType.bool())
                            .executes(ctx -> {
                                CommandSender sender = ctx.getSource().getSender();
                                // checking if sender is a player
                                if (!(sender instanceof Player)) {
                                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                    return Command.SINGLE_SUCCESS;
                                }

                                Player player = (Player) sender;

                                if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                    return Command.SINGLE_SUCCESS;
                                }

                                UUID instanceUUID;
                                instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                boolean reverse = ctx.getArgument("reverse", boolean.class);

                                if (TimerAPI.getReverse(instanceUUID) == reverse) {
                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_IS_REVERSE);
                                    return Command.SINGLE_SUCCESS;
                                }
                                TimerAPI.setTime(instanceUUID, TimerAPI.getTime(instanceUUID));
                                TimerAPI.setStartingTime(instanceUUID);
                                TimerAPI.setReverse(instanceUUID, reverse);
                                MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_REVERSE);
                                return Command.SINGLE_SUCCESS;
                            })))
            .then(Commands.literal("bold")
                    .then(Commands.argument("bold", BoolArgumentType.bool())
                            .executes(ctx -> {
                                CommandSender sender = ctx.getSource().getSender();
                                // checking if sender is a player
                                if (!(sender instanceof Player)) {
                                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                    return Command.SINGLE_SUCCESS;
                                }

                                Player player = (Player) sender;

                                if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                    return Command.SINGLE_SUCCESS;
                                }

                                UUID instanceUUID;
                                instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                boolean bold = ctx.getArgument("bold", boolean.class);

                                if (TimerAPI.getBold(instanceUUID) == bold) {
                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_IS_BOLD);
                                    return Command.SINGLE_SUCCESS;
                                }
                                TimerAPI.setBold(instanceUUID, bold);
                                MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_BOLD);
                                return Command.SINGLE_SUCCESS;
                            })))
            .then(Commands.literal("style")
                    .then(Commands.argument("style", StringArgumentType.word())
                            .suggests((ctx, builder) -> {
                                // suggestion for the command
                                List<String> styles = new ArrayList<>();
                                for (Style style : Style.values()) {
                                    styles.add(style.toString().toLowerCase());
                                }
                                styles.stream()
                                        .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                        .forEach(builder::suggest);
                                return builder.buildFuture();
                            })
                            .executes(ctx -> {
                                CommandSender sender = ctx.getSource().getSender();
                                // checking if sender is a player
                                if (!(sender instanceof Player)) {
                                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                    return Command.SINGLE_SUCCESS;
                                }

                                Player player = (Player) sender;

                                if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                    return Command.SINGLE_SUCCESS;
                                }

                                UUID instanceUUID;
                                instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                Style style = Style.valueOf(ctx.getArgument("style", String.class).toUpperCase());

                                if (TimerAPI.getStyle(instanceUUID) == style) {
                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_IS_STYLE);
                                    return Command.SINGLE_SUCCESS;
                                }
                                TimerAPI.setStyle(instanceUUID, style);
                                MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_STYLE);
                                return Command.SINGLE_SUCCESS;
                            })))
            .then(Commands.literal("color")
                    .then(Commands.literal("solid")
                            .then(Commands.argument("color", StringArgumentType.word())
                                    .suggests((ctx, builder) -> {
                                        // suggestion for the command
                                        List<String> colors = getColors();
                                        colors.stream()
                                                .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                                .forEach(builder::suggest);
                                        return builder.buildFuture();
                                    })
                                    .executes(ctx -> {
                                        CommandSender sender = ctx.getSource().getSender();
                                        // checking if sender is a player
                                        if (!(sender instanceof Player)) {
                                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                            return Command.SINGLE_SUCCESS;
                                        }

                                        Player player = (Player) sender;

                                        if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                            return Command.SINGLE_SUCCESS;
                                        }

                                        UUID instanceUUID;
                                        instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                        String color = ctx.getArgument("color", String.class);
                                        TimerAPI.setColor(instanceUUID, ColorType.SOLID, color, "null", 0, 0);

                                        MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_COLOR_SOLID);
                                        return Command.SINGLE_SUCCESS;
                                    })))
                    .then(Commands.literal("solid_custom")
                            .then(Commands.argument("red", IntegerArgumentType.integer(0, 255))
                                    .then(Commands.argument("green", IntegerArgumentType.integer(0, 255))
                                            .then(Commands.argument("blue", IntegerArgumentType.integer(0, 255))
                                                    .executes(ctx -> {
                                                        CommandSender sender = ctx.getSource().getSender();
                                                        // checking if sender is a player
                                                        if (!(sender instanceof Player)) {
                                                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                                            return Command.SINGLE_SUCCESS;
                                                        }

                                                        Player player = (Player) sender;

                                                        if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                                            return Command.SINGLE_SUCCESS;
                                                        }

                                                        UUID instanceUUID;
                                                        instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                                        int red = ctx.getArgument("red", Integer.class);
                                                        int green = ctx.getArgument("green", Integer.class);
                                                        int blue = ctx.getArgument("blue", Integer.class);

                                                        String color = red + "," + green + "," + blue;

                                                        TimerAPI.setColor(instanceUUID, ColorType.SOLID_CUSTOM, color, "null", 0, 0);

                                                        MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_COLOR_SOLID_CUSTOM);
                                                        return Command.SINGLE_SUCCESS;
                                                    })))))
                    .then(Commands.literal("rgb_transition")
                            .then(Commands.argument("speed", IntegerArgumentType.integer(1, Integer.MAX_VALUE))
                                    .then(Commands.argument("difference", IntegerArgumentType.integer(Integer.MIN_VALUE, Integer.MAX_VALUE))
                                            .executes(ctx -> {
                                                CommandSender sender = ctx.getSource().getSender();
                                                // checking if sender is a player
                                                if (!(sender instanceof Player)) {
                                                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                                    return Command.SINGLE_SUCCESS;
                                                }

                                                Player player = (Player) sender;

                                                if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                                    return Command.SINGLE_SUCCESS;
                                                }

                                                UUID instanceUUID;
                                                instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                                int speed = ctx.getArgument("speed", Integer.class);
                                                int difference = ctx.getArgument("difference", Integer.class);

                                                TimerAPI.setColor(instanceUUID, ColorType.RGB_TRANSITION, "null", "null", speed, difference);

                                                MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_COLOR_RGB_TRANSITION);
                                                return Command.SINGLE_SUCCESS;
                                            }))))
                    .then(Commands.literal("rgb_fade")
                            .then(Commands.argument("speed", IntegerArgumentType.integer(1, Integer.MAX_VALUE))
                                    .executes(ctx -> {
                                        CommandSender sender = ctx.getSource().getSender();
                                        // checking if sender is a player
                                        if (!(sender instanceof Player)) {
                                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                            return Command.SINGLE_SUCCESS;
                                        }

                                        Player player = (Player) sender;

                                        if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                            return Command.SINGLE_SUCCESS;
                                        }

                                        UUID instanceUUID;
                                        instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                        int speed = ctx.getArgument("speed", Integer.class);

                                        TimerAPI.setColor(instanceUUID, ColorType.RGB_FADE, "null", "null", speed, 0);

                                        MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_COLOR_RGB_FADE);
                                        return Command.SINGLE_SUCCESS;
                                    }))))
            .then(Commands.literal("time")
                    .then(Commands.argument("set|add", StringArgumentType.word())
                            .suggests((ctx, builder) -> {
                                // suggestion for the command
                                List<String> setAndAdd = new ArrayList<>();
                                setAndAdd.add("set");
                                setAndAdd.add("add");
                                setAndAdd.stream()
                                        .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                        .forEach(builder::suggest);
                                return builder.buildFuture();
                            })
                            .then(Commands.argument("number1", LongArgumentType.longArg(0, Long.MAX_VALUE))
                                    .executes(ctx -> {
                                        CommandSender sender = ctx.getSource().getSender();
                                        // checking if sender is a player
                                        if (!(sender instanceof Player)) {
                                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                            return Command.SINGLE_SUCCESS;
                                        }

                                        Player player = (Player) sender;

                                        if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                            MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                            return Command.SINGLE_SUCCESS;
                                        }

                                        UUID instanceUUID;
                                        instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                        String setOrAdd = ctx.getArgument("set|add", String.class);
                                        long number1 = ctx.getArgument("number1", Long.class);

                                        long time = number1 * 1000;

                                        String timeString = number1 + "s";

                                        finish(setOrAdd, instanceUUID, time, player, timeString);
                                        return Command.SINGLE_SUCCESS;
                                    })
                                    .then(Commands.argument("multiplier1", StringArgumentType.word())
                                            .suggests((ctx, builder) -> {
                                                // suggestion for the command
                                                List<String> multipliers = getMultipliers();
                                                multipliers.stream()
                                                        .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                                        .forEach(builder::suggest);
                                                return builder.buildFuture();
                                            })
                                            .executes(ctx -> {
                                                CommandSender sender = ctx.getSource().getSender();
                                                // checking if sender is a player
                                                if (!(sender instanceof Player)) {
                                                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                                    return Command.SINGLE_SUCCESS;
                                                }

                                                Player player = (Player) sender;

                                                if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                                    return Command.SINGLE_SUCCESS;
                                                }

                                                UUID instanceUUID;
                                                instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                                String setOrAdd = ctx.getArgument("set|add", String.class);
                                                long number1 = ctx.getArgument("number1", Long.class);
                                                String multiplier1 = ctx.getArgument("multiplier1", String.class);

                                                long time = getTimeByMultiplier(number1, multiplier1);

                                                String timeString = number1 + multiplier1;

                                                finish(setOrAdd, instanceUUID, time, player, timeString);
                                                return Command.SINGLE_SUCCESS;
                                            })
                                            .then(Commands.argument("number2", LongArgumentType.longArg(0, Long.MAX_VALUE))
                                                    .then(Commands.argument("multiplier2", StringArgumentType.word())
                                                            .suggests((ctx, builder) -> {
                                                                // suggestion for the command
                                                                List<String> multipliers = getMultipliers();
                                                                multipliers.stream()
                                                                        .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                                                        .forEach(builder::suggest);
                                                                return builder.buildFuture();
                                                            })
                                                            .executes(ctx -> {
                                                                CommandSender sender = ctx.getSource().getSender();
                                                                // checking if sender is a player
                                                                if (!(sender instanceof Player)) {
                                                                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                                                    return Command.SINGLE_SUCCESS;
                                                                }

                                                                Player player = (Player) sender;

                                                                if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                                                    return Command.SINGLE_SUCCESS;
                                                                }

                                                                UUID instanceUUID;
                                                                instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                                                String setOrAdd = ctx.getArgument("set|add", String.class);
                                                                long number1 = ctx.getArgument("number1", Long.class);
                                                                String multiplier1 = ctx.getArgument("multiplier1", String.class);
                                                                long number2 = ctx.getArgument("number2", Long.class);
                                                                String multiplier2 = ctx.getArgument("multiplier2", String.class);

                                                                long time = getTimeByMultiplier(number1, multiplier1) + getTimeByMultiplier(number2, multiplier2);

                                                                String timeString = number1 + multiplier1 + " " + number2 + multiplier2;

                                                                finish(setOrAdd, instanceUUID, time, player, timeString);
                                                                return Command.SINGLE_SUCCESS;
                                                            })
                                                            .then(Commands.argument("number3", LongArgumentType.longArg(0, Long.MAX_VALUE))
                                                                    .then(Commands.argument("multiplier3", StringArgumentType.word())
                                                                            .suggests((ctx, builder) -> {
                                                                                // suggestion for the command
                                                                                List<String> multipliers = getMultipliers();
                                                                                multipliers.stream()
                                                                                        .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                                                                        .forEach(builder::suggest);
                                                                                return builder.buildFuture();
                                                                            })
                                                                            .executes(ctx -> {
                                                                                CommandSender sender = ctx.getSource().getSender();
                                                                                // checking if sender is a player
                                                                                if (!(sender instanceof Player)) {
                                                                                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                                                                    return Command.SINGLE_SUCCESS;
                                                                                }

                                                                                Player player = (Player) sender;

                                                                                if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                                                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                                                                    return Command.SINGLE_SUCCESS;
                                                                                }

                                                                                UUID instanceUUID;
                                                                                instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                                                                String setOrAdd = ctx.getArgument("set|add", String.class);
                                                                                long number1 = ctx.getArgument("number1", Long.class);
                                                                                String multiplier1 = ctx.getArgument("multiplier1", String.class);
                                                                                long number2 = ctx.getArgument("number2", Long.class);
                                                                                String multiplier2 = ctx.getArgument("multiplier2", String.class);
                                                                                long number3 = ctx.getArgument("number3", Long.class);
                                                                                String multiplier3 = ctx.getArgument("multiplier3", String.class);

                                                                                long time = getTimeByMultiplier(number1, multiplier1) + getTimeByMultiplier(number2, multiplier2) + getTimeByMultiplier(number3, multiplier3);

                                                                                String timeString = number1 + multiplier1 + " " + number2 + multiplier2 + " " + number3 + multiplier3;

                                                                                finish(setOrAdd, instanceUUID, time, player, timeString);
                                                                                return Command.SINGLE_SUCCESS;
                                                                            })
                                                                            .then(Commands.argument("number4", LongArgumentType.longArg(0, Long.MAX_VALUE))
                                                                                    .then(Commands.argument("multiplier4", StringArgumentType.word())
                                                                                            .suggests((ctx, builder) -> {
                                                                                                // suggestion for the command
                                                                                                List<String> multipliers = getMultipliers();
                                                                                                multipliers.stream()
                                                                                                        .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                                                                                        .forEach(builder::suggest);
                                                                                                return builder.buildFuture();
                                                                                            })
                                                                                            .executes(ctx -> {
                                                                                                CommandSender sender = ctx.getSource().getSender();
                                                                                                // checking if sender is a player
                                                                                                if (!(sender instanceof Player)) {
                                                                                                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                                                                                    return Command.SINGLE_SUCCESS;
                                                                                                }

                                                                                                Player player = (Player) sender;

                                                                                                if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                                                                                                    MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_NOT_IN_LOBBY);
                                                                                                    return Command.SINGLE_SUCCESS;
                                                                                                }

                                                                                                UUID instanceUUID;
                                                                                                instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));

                                                                                                String setOrAdd = ctx.getArgument("set|add", String.class);
                                                                                                long number1 = ctx.getArgument("number1", Long.class);
                                                                                                String multiplier1 = ctx.getArgument("multiplier1", String.class);
                                                                                                long number2 = ctx.getArgument("number2", Long.class);
                                                                                                String multiplier2 = ctx.getArgument("multiplier2", String.class);
                                                                                                long number3 = ctx.getArgument("number3", Long.class);
                                                                                                String multiplier3 = ctx.getArgument("multiplier3", String.class);
                                                                                                long number4 = ctx.getArgument("number4", Long.class);
                                                                                                String multiplier4 = ctx.getArgument("multiplier4", String.class);

                                                                                                long time = getTimeByMultiplier(number1, multiplier1) + getTimeByMultiplier(number2, multiplier2) + getTimeByMultiplier(number3, multiplier3) + getTimeByMultiplier(number4, multiplier4);

                                                                                                String timeString = number1 + multiplier1 + " " + number2 + multiplier2 + " " + number3 + multiplier3 + " " + number4 + multiplier4;

                                                                                                finish(setOrAdd, instanceUUID, time, player, timeString);
                                                                                                return Command.SINGLE_SUCCESS;
                                                                                            })))))))))))

            ;

    @NotNull
    private static List<String> getMultipliers() {
        List<String> multipliers = new ArrayList<>();
        multipliers.add("d");
        multipliers.add("h");
        multipliers.add("m");
        multipliers.add("s");
        return multipliers;
    }

    @NotNull
    private static List<String> getColors() {
        List<String> colors = new ArrayList<>();
        colors.add("1");
        colors.add("2");
        colors.add("3");
        colors.add("4");
        colors.add("5");
        colors.add("6");
        colors.add("7");
        colors.add("8");
        colors.add("9");
        colors.add("0");
        colors.add("a");
        colors.add("b");
        colors.add("c");
        colors.add("d");
        colors.add("e");
        colors.add("f");
        return colors;
    }

    public static void finish(String setOrAdd, UUID instanceUUID, long time, Player player, String timeString) {
        switch (setOrAdd) {
            case "set" -> {
                TimerAPI.setTime(instanceUUID, time);
                TimerAPI.setStartingTime(instanceUUID);
                MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_SET, new String[] {timeString});
            }
            case "add" -> {
                TimerAPI.setTime(instanceUUID, TimerAPI.getTime(instanceUUID) + time);
                TimerAPI.setStartingTime(instanceUUID);
                MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_TIMER_SUCCESSFULLY_ADD, new String[] {timeString});
            }
            default -> {
                MessageAPI.sendMessage(player, MessageID.COMMAND_TIMER_ONLY_SET_OR_ADD);
            }
        }
    }

    private static long getTimeByMultiplier(long rawTime, String multiplier) {
        switch (multiplier) {
            case "d" -> {
                return rawTime * 24 * 60 * 60 * 1000;
            }
            case "h" -> {
                return rawTime * 60 * 60 * 1000;
            }
            case "m" -> {
                return rawTime * 60 * 1000;
            }
            case "s" -> {
                return rawTime * 1000;
            }
            default -> {
                return 0;
            }
        }
    }

}