package de.thzockt.thUtils.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.LobbyAPI.LobbyAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLobby {

    public static LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("lobby")
            .requires(sender -> ServerManagerAPI.getCurrentServerMode() == ServerMode.INSTANCING)
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();

                if (!(sender instanceof Player)) {
                    sender.sendMessage(MessageAPI.getMessage(MessageID.ONLY_IN_MINECRAFT, Language.ENGLISH));
                    return Command.SINGLE_SUCCESS;
                }

                Player player = (Player) sender;

                LobbyAPI.movePlayerToLobby(player);
                return Command.SINGLE_SUCCESS;
            });
}