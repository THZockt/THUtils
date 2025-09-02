package de.thzockt.thUtils.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CommandServermode {

    private static HashMap<UUID, String> playerValidationCodes = new HashMap<>();

    public static LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("servermode")
            .requires(Commands.restricted(source -> source.getSender().hasPermission("commands.servermode")))
            .then(Commands.argument("mode", StringArgumentType.word())
                    .suggests((ctx, builder) -> {
                        // suggestion for the command
                        List<String> modes = new ArrayList<>();
                        for (ServerMode serverMode : ServerMode.values()) {
                            modes.add(serverMode.toString().toLowerCase());
                        }
                        modes.stream()
                                .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                .forEach(builder::suggest);
                        return builder.buildFuture();
                    })
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();
                        String mode = ctx.getArgument("mode", String.class);

                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        ServerMode newMode = ServerMode.valueOf(mode.toUpperCase());
                        Player player = (Player) sender;

                        if (newMode == null) {
                            MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_SERVERMODE_MODE_NOT_EXISTING, new String[] {mode});
                            return Command.SINGLE_SUCCESS;
                        }

                        if (newMode == ServerManagerAPI.getCurrentServerMode()) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_SERVERMODE_ALREADY_MODE);
                            return Command.SINGLE_SUCCESS;
                        }

                        if (playerValidationCodes.containsKey(player.getUniqueId())) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_SERVERMODE_ALREADY_CODE);
                            return Command.SINGLE_SUCCESS;
                        }

                        String code = "";

                        for (int i = 0; i < 4; i++) {
                            code = code + MessageAPI.getRandomChar();
                        }

                        for (int t = 0; t < 3; t++) {
                            code = code + "-";
                            for (int i = 0; i < 4; i++) {
                                code = code + MessageAPI.getRandomChar();
                            }
                        }

                        playerValidationCodes.put(player.getUniqueId(), code);
                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_SERVERMODE_CODE_SET, new String[] {code, mode});

                        return Command.SINGLE_SUCCESS;
                    })
                    .then(Commands.argument("code", StringArgumentType.word())
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();
                        String mode = ctx.getArgument("mode", String.class);

                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        Player player = (Player) sender;
                        String code = ctx.getArgument("code", String.class);
                        ServerMode newMode = ServerMode.valueOf(mode.toUpperCase());

                        if (!playerValidationCodes.containsKey(player.getUniqueId())) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_SERVERMODE_MISSING_CODE);
                            return Command.SINGLE_SUCCESS;
                        }

                        if (newMode == null) {
                            MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_SERVERMODE_MODE_NOT_EXISTING, new String[] {mode});
                            return Command.SINGLE_SUCCESS;
                        }

                        if (newMode == ServerManagerAPI.getCurrentServerMode()) {
                            MessageAPI.sendMessage(player, MessageID.COMMAND_SERVERMODE_ALREADY_MODE);
                            return Command.SINGLE_SUCCESS;
                        }

                        if (!playerValidationCodes.get(player.getUniqueId()).equals(code)) {
                            MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_SERVERMODE_WRONG_CODE, new String[] {playerValidationCodes.get(player.getUniqueId())});
                            return Command.SINGLE_SUCCESS;
                        }

                        playerValidationCodes.remove(player.getUniqueId());

                        ServerManagerAPI.setServerMode(newMode);
                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_SERVERMODE_REQUIRED_RESTART, new String[] {mode});

                        return Command.SINGLE_SUCCESS;
                    })
                    ));
}