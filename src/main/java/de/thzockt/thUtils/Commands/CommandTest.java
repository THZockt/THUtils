package de.thzockt.thUtils.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.advancement.Advancement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CommandTest {
    public static LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("th-test")
            //.requires(sender -> sender instanceof Player player && player.getName().equals("THZockt"))
            .then(Commands.literal("hin")
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();

                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        Player player = (Player) sender;

                        for (@NotNull Iterator<Advancement> it = player.getServer().advancementIterator(); it.hasNext(); ) {
                            Advancement advancement = it.next();
                            Collection<String> awarded = player.getAdvancementProgress(advancement).getAwardedCriteria();
                            Collection<String> remaining = player.getAdvancementProgress(advancement).getRemainingCriteria();
                            List<String> all = new ArrayList<>();
                            all.addAll(awarded);
                            all.addAll(remaining);
                            for (String criteria : all) {
                                player.getAdvancementProgress(advancement).awardCriteria(criteria);
                            }
                        }
                        player.sendMessage("alles gegeben");
                        return Command.SINGLE_SUCCESS;
                    })
            ).then(Commands.literal("weg")
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();

                        if (!(sender instanceof Player)) {
                            sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                            return Command.SINGLE_SUCCESS;
                        }

                        Player player = (Player) sender;

                        for (@NotNull Iterator<Advancement> it = player.getServer().advancementIterator(); it.hasNext(); ) {
                            Advancement advancement = it.next();
                            Collection<String> awarded = player.getAdvancementProgress(advancement).getAwardedCriteria();
                            Collection<String> remaining = player.getAdvancementProgress(advancement).getRemainingCriteria();
                            List<String> all = new ArrayList<>();
                            all.addAll(awarded);
                            all.addAll(remaining);
                            for (String criteria : all) {
                                player.getAdvancementProgress(advancement).revokeCriteria(criteria);
                            }
                        }
                        player.sendMessage("alles genommen");
                        return Command.SINGLE_SUCCESS;
                    })
            );
}