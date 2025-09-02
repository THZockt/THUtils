package de.thzockt.thUtils.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.thzockt.thUtils.APIs.InstanceAPI.InstanceAPI;
import de.thzockt.thUtils.APIs.InstanceAPI.InstanceMode;
import de.thzockt.thUtils.APIs.InstanceAPI.InstanceUUID;
import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandInstance {

        public static LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("instance")
                .requires(sender -> ServerManagerAPI.getCurrentServerMode() == ServerMode.INSTANCING)
                .then(Commands.literal("create")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .then(Commands.literal("normal")
                                        .executes(ctx -> {
                                            // getting the "name" argument and getting the sender
                                            String name = ctx.getArgument("name", String.class);
                                            CommandSender sender = ctx.getSource().getSender();
                                            // checking if sender is a player
                                            if (!(sender instanceof Player)) {
                                              sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                              return Command.SINGLE_SUCCESS;
                                            }

                                            Player player = (Player) sender;

                                            // checking if name already exists
                                            if (InstanceAPI.checkIfNameExists(name)) {
                                                MessageAPI.sendMessageWithVariables(player, MessageID.NAME_ALREADY_EXISTS, new String[] {name});
                                                return Command.SINGLE_SUCCESS;
                                            }

                                            // creating the new instance like the command should...
                                            InstanceAPI.createNewInstance(name, InstanceMode.NORMAL, player.getName());

                                            MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_CREATED_SUCCESSFUL, new String[] {name});

                                            return Command.SINGLE_SUCCESS;
                                        }))
                                .then(Commands.literal("minigame")
                                    .then(Commands.literal("minigameArgument"))
                                )
                        )
                )
                .then(Commands.literal("start")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .suggests((ctx, builder) -> {
                                    // suggestion for the command
                                    List<String> names = new ArrayList<>();
                                    for (UUID uuid : InstanceUUID.getUUIDs()) {
                                        names.add(InstanceAPI.getInstanceName(uuid));
                                    }
                                    names.stream()
                                            .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                            .forEach(builder::suggest);
                                    return builder.buildFuture();
                                })
                                .executes(ctx -> {
                                    // getting uuid and sender
                                    String name = ctx.getArgument("name", String.class);
                                    CommandSender sender = ctx.getSource().getSender();
                                    UUID uuid = InstanceAPI.getInstanceUUID(name);

                                    // checking if sender is a player
                                    if (!(sender instanceof Player)) {
                                        sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    Player player = (Player) sender;

                                    // checking if instance exists
                                    if (uuid == null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_NOT_EXISTING, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    // checking if instance is already started
                                    World world = Bukkit.getWorld("instances/" + uuid + "/world");
                                    World nether = Bukkit.getWorld("instances/" + uuid + "/world_nether");
                                    World the_end = Bukkit.getWorld("instances/" + uuid + "/world_the_end");

                                    if (world != null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_ALREADY_RUNNING, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    if (nether != null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_ALREADY_RUNNING, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    if (the_end != null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_ALREADY_RUNNING, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_START, new String[] {name});

                                    // starting the instance
                                    InstanceAPI.startInstance(uuid);

                                    MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_START_SUCCESS, new String[] {name});

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                .then(Commands.literal("stop")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .suggests((ctx, builder) -> {
                                    // suggestion for the command
                                    List<String> names = new ArrayList<>();
                                    for (UUID uuid : InstanceUUID.getUUIDs()) {
                                        names.add(InstanceAPI.getInstanceName(uuid));
                                    }
                                    names.stream()
                                            .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                            .forEach(builder::suggest);
                                    return builder.buildFuture();
                                })
                                .executes(ctx -> {
                                    // getting uuid and sender
                                    String name = ctx.getArgument("name", String.class);
                                    CommandSender sender = ctx.getSource().getSender();
                                    UUID uuid = InstanceAPI.getInstanceUUID(name);

                                    // checking if sender is a player
                                    if (!(sender instanceof Player)) {
                                        sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    Player player = (Player) sender;

                                    // checking if instance exists
                                    if (uuid == null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_NOT_EXISTING, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    // checking if instance is already stopped
                                    World world = Bukkit.getWorld("instances/" + uuid + "/world");
                                    World nether = Bukkit.getWorld("instances/" + uuid + "/world_nether");
                                    World the_end = Bukkit.getWorld("instances/" + uuid + "/world_the_end");

                                    if (world == null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_ALREADY_STOPPED, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    if (nether == null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_ALREADY_STOPPED, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    if (the_end == null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_ALREADY_STOPPED, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_STOP, new String[] {name});

                                    // stopping the instance
                                    InstanceAPI.stopInstance(uuid);

                                    MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_STOP_SUCCESS, new String[] {name});

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                .then(Commands.literal("delete")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .suggests((ctx, builder) -> {
                                    // suggestion for the command
                                    List<String> names = new ArrayList<>();
                                    for (UUID uuid : InstanceUUID.getUUIDs()) {
                                        names.add(InstanceAPI.getInstanceName(uuid));
                                    }
                                    names.stream()
                                            .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                            .forEach(builder::suggest);
                                    return builder.buildFuture();
                                })
                                .executes(ctx -> {
                                    // getting uuid and sender
                                    String name = ctx.getArgument("name", String.class);
                                    CommandSender sender = ctx.getSource().getSender();
                                    UUID uuid = InstanceAPI.getInstanceUUID(name);

                                    // checking if sender is a player
                                    if (!(sender instanceof Player)) {
                                        sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    Player player = (Player) sender;

                                    // checking if instance exists
                                    if (uuid == null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_NOT_EXISTING, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    World world = Bukkit.getWorld("instances/" + uuid + "/world");
                                    World nether = Bukkit.getWorld("instances/" + uuid + "/world_nether");
                                    World the_end = Bukkit.getWorld("instances/" + uuid + "/world_the_end");

                                    if (world != null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_WORLD_CURRENTLY_LOADED, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    if (nether != null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_WORLD_CURRENTLY_LOADED, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }
                                    if (the_end != null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_WORLD_CURRENTLY_LOADED, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_DELETE, new String[] {name});

                                    // deleting the instance
                                    InstanceAPI.deleteInstance(uuid);

                                    MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_DELETE_SUCCESS, new String[] {name});

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                .then(Commands.literal("join")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .suggests((ctx, builder) -> {
                                    // suggestion for the command
                                    List<String> names = new ArrayList<>();
                                    for (UUID uuid : InstanceUUID.getUUIDs()) {
                                        names.add(InstanceAPI.getInstanceName(uuid));
                                    }
                                    names.stream()
                                            .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                            .forEach(builder::suggest);
                                    return builder.buildFuture();
                                })
                                .executes(ctx -> {
                                    // getting uuid and sender
                                    String name = ctx.getArgument("name", String.class);
                                    CommandSender sender = ctx.getSource().getSender();
                                    UUID uuid = InstanceAPI.getInstanceUUID(name);

                                    // checking if sender is a player
                                    if (!(sender instanceof Player)) {
                                        sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    Player player = (Player) sender;

                                    // checking if instance exists
                                    if (uuid == null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_NOT_EXISTING, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    World world = Bukkit.getWorld("instances/" + uuid + "/world");
                                    if (world == null) {
                                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_JOIN_FAIL, new String[] {name});
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_JOIN, new String[] {name});

                                    // sending the player in the instance
                                    InstanceAPI.joinInstance(uuid, player);

                                    MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_INSTANCE_JOIN_SUCCESS, new String[] {name});

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );

}