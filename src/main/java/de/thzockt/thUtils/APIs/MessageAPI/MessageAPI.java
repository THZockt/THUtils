package de.thzockt.thUtils.APIs.MessageAPI;

import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.LanguageAPI.LanguageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.Messages.MessagesEnglish;
import de.thzockt.thUtils.APIs.MessageAPI.Messages.MessagesGerman;
import de.thzockt.thUtils.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Random;

public class MessageAPI {
    public static String getMessage(MessageID messageID, Language language) {
        Config languageMessages = new Config(language.toString().toLowerCase() + "Messages" + ".yml", new File(Main.getInstance().getDataFolder() + "/serverData/messages/"));
        if (languageMessages.toFileConfiguration().contains(messageID.toString())) {
            return languageMessages.toFileConfiguration().getString(messageID.toString());
        } else {
            return language + "#" + messageID;
        }
    }
    public static String addVariables(String message, String[] variables) {
        String finalMessage = message;
        for (int i = 0; i < variables.length; i++) {
            finalMessage = finalMessage.replaceAll("\\{" + i + "\\}", variables[i]);
        }
        return finalMessage;
    }
    public static void sendMessage(Player player, MessageID messageID) {
        player.sendMessage(
                Component.text(
                        getMessage(messageID, LanguageAPI.getPlayerLanguage(player)
                        )
                )
        );
    }

    public static char getRandomChar() {
        Random random = new Random();
        String possibleChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int charID = random.nextInt(possibleChars.length());
        return possibleChars.charAt(charID);
    }
    public static String getMessageWithVariables(MessageID messageID, Language language, String[] variables) {
        return addVariables(
                getMessage(messageID, language),
                variables
        );
    }
    public static void sendMessageWithVariables(Player player, MessageID messageID, String[] variables) {
        player.sendMessage(Component.text(getMessageWithVariables(
                messageID,
                LanguageAPI.getPlayerLanguage(player),
                variables
        )));
    }
    public static void iniAddMessage(MessageID messageID, Language language, String message) {
        Config languageMessages = new Config(language.toString().toLowerCase() + "Messages" + ".yml", new File(Main.getInstance().getDataFolder() + "/serverData/messages/"));
        if (!languageMessages.toFileConfiguration().contains(messageID.toString())) {
            languageMessages.toFileConfiguration().set(messageID.toString(), message);
            languageMessages.save();
        }
    }
    public static void initialiseMessages() {
        MessagesEnglish messagesEnglish = new MessagesEnglish();
        messagesEnglish.iniAddEnglishMessages();
        MessagesGerman messagesGerman = new MessagesGerman();
        messagesGerman.iniAddGermanMessages();
    }
}