package de.thzockt.thUtils.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import de.thzockt.thUtils.Main;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandLanguage {

    public static LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("language")
            .then(Commands.argument("language", StringArgumentType.greedyString())
                    .suggests((ctx, builder) -> {
                        // suggestion for the command
                        List<String> languages = new ArrayList<>();
                        for (Language language : Language.values()) {
                            languages.add(language.toString());
                        }
                        languages.stream()
                                .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
                                .forEach(builder::suggest);
                        return builder.buildFuture();
                    })
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();

                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        Player player = (Player) sender;

                        Config playerData = new Config(player.getUniqueId() + ".yml", new File(Main.getInstance().getDataFolder() + "/playerData/"));
                        if (!playerData.toFileConfiguration().contains("language")) {
                            playerData.toFileConfiguration().set("language", Language.ENGLISH.toString());
                            playerData.save();
                        }
                        if (!playerData.toFileConfiguration().contains("hasSetLanguage")) {
                            playerData.toFileConfiguration().set("hasSetLanguage", false);
                            playerData.save();
                        }

                        String language = ctx.getArgument("language", String.class);
                        playerData.toFileConfiguration().set("language", language);
                        playerData.toFileConfiguration().set("hasSetLanguage", true);
                        playerData.save();

                        Language newLanguage = Language.valueOf(language);

                        MessageAPI.sendMessageWithVariables(player, MessageID.COMMAND_LANGUAGE_SET, new String[] {newLanguage.getName()});

                        return Command.SINGLE_SUCCESS;
                    }
                    ));

}