package de.thzockt.thUtils.APIs.LanguageAPI;

import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import de.thzockt.thUtils.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class LanguageAPI implements Listener {
    public static Language getPlayerLanguage(Player player) {
        Config playerData = new Config(player.getUniqueId() + ".yml", new File(Main.getInstance().getDataFolder() + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("language")) {
            playerData.toFileConfiguration().set("language", Language.ENGLISH.toString());
            playerData.save();
        }
        String rawLanguage = playerData.toFileConfiguration().getString("language");
        return Language.valueOf(rawLanguage);
    }

    public static boolean hasPlayerSetLanguage(Player player) {
        Config playerData = new Config(player.getUniqueId() + ".yml", new File(Main.getInstance().getDataFolder() + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("hasSetLanguage")) {
            playerData.toFileConfiguration().set("hasSetLanguage", false);
            playerData.save();
        }
        return playerData.toFileConfiguration().getBoolean("hasSetLanguage");
    }

    public static void notifyPlayer(Player player) {
        MessageAPI.sendMessage(player, MessageID.LANGUAGE_NOT_SET);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!hasPlayerSetLanguage(player)) {
            Main.getScheduler().runTaskLater(Main.getPlugin(), task -> {
                notifyPlayer(player);
            }, 20 * 5);
        }

    }
}