package de.thzockt.thUtils.APIs.MessageAPI.Messages;

import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;

public class MessagesEnglish {

    public void iniAddEnglishMessages() {
        Language language = Language.ENGLISH;

        //MessageAPI.iniAddMessage(MessageID., language, "");
        MessageAPI.iniAddMessage(MessageID.LANGUAGE_NOT_SET, language, "§6⚠ §cWARNING! You haven't set your language yet! Don't worry, everything will be working just fine but every time you join, this friendly message will pop up :)...\nSo if you want to change your language (and remove this message), you have to type §6\"/language [language]\"§c to change it!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_LANGUAGE_SET, language, "§aYou have successfully changed your language to §f{0}§a!");
        MessageAPI.iniAddMessage(MessageID.LOBBY_CURRENTLY_LOADING, language, "§c§lIm sorry but the lobby is currently loading!\n§r§7Please wait a little bit and come back later...");
        MessageAPI.iniAddMessage(MessageID.INSTANCE_MODE_NOT_ACTIVATED, language, "§cYou cannot do this! The instance mode is not activated!");
        MessageAPI.iniAddMessage(MessageID.ONLY_IN_MINECRAFT, language, "§cYou can only do this in Minecraft!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_USAGE, language, "§cPlease use §6\"/instance [create,start,stop,join,delete]\"§c!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_USAGE_CREATE_NAME, language, "§cPlease use §c\"/instance create [name] [normal,minigame]\"§c! You can also put the name in \"quotation marks\" to use spaces!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_USAGE_CREATE_MODE, language, "§cPlease use §c\"/instance create [name] [normal,minigame]\"§c to create an instance with a custom name and instance type!");
        MessageAPI.iniAddMessage(MessageID.NAME_ALREADY_EXISTS, language, "§cThe name §6{0}§c already exists!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_NOT_EXISTING, language, "§cThe instance §6{0}§c does not exist!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_WORLD_CURRENTLY_LOADED, language, "§cYou cannot delete this instance! The instance §6{0}§c is currently loaded!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_CREATED_SUCCESSFUL, language, "§aYou have successfully created the instance §f\"{0}\"§a!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_ALREADY_RUNNING, language, "§cThe instance §6{0}§c is already running!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_ALREADY_STOPPED, language, "§cThe instance §6{0}§c is already stopped!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_START, language, "§7Starting instance \"{0}\"...");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_STOP, language, "§7Stopping instance \"{0}\"...");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_DELETE, language, "§7Deleting instance \"{0}\"...");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_JOIN, language, "§7Joining instance \"{0}\"...");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_START_SUCCESS, language, "§aThe instance §f\"{0}\"§a is successfully started!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_STOP_SUCCESS, language, "§aThe instance §f\"{0}\"§a is successfully stopped!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_DELETE_SUCCESS, language, "§aThe instance §f\"{0}\"§a is successfully deleted!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_JOIN_SUCCESS, language, "§aYou have successfully joined the instance §f\"{0}\"§a!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_ALREADY_CODE, language, "§cYou already have a confirmation code!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_CODE_SET, language, "§6⚠ §cWARNING! If you want to change the server mode, than this could break you entire server data if you want to change it back later!\nIf you are shure to change the servermode, then you must type your 16-digit confirmation code: §f{0}\n§cPlease run the command again with your confirmation code behind like this: §6\"/servermode {1} {0}\"§c!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_MISSING_CODE, language, "§cYou don't have a code yet! Please run the command without the code you entered!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_WRONG_CODE, language, "§cThat's the wrong code! Your code is §f{0}§c!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_MODE_NOT_EXISTING, language, "§cThe mode §6\"{0}\"§c does not exist!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_ALREADY_MODE, language, "§cThis mode is already selected on this server!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_REQUIRED_RESTART, language, "§aYou have successfully changed the servermode to §f\"{0}\"§a! To fully adapt this change, you have to restart the server!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_JOIN_FAIL, language, "§cThe instance §6\"{0}\"§c is currently not online!");
        MessageAPI.iniAddMessage(MessageID.STYLES_DAYS, language, "Days");
        MessageAPI.iniAddMessage(MessageID.STYLES_DAY, language, "Day");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_ALREADY_RESUMED, language, "§cThe timer is already resumed!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_ALREADY_PAUSED, language, "§cThe timer is already paused!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_RESUMED, language, "§aYou have successfully resumed the timer!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_PAUSED, language, "§aYou have successfully paused the timer!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_TOGGLED, language, "§aYou have successfully toggled the timer!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_RESET, language, "§aYou have successfully reset the timer!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_SET, language, "§aYou have successfully set the timer to §f{0}§a!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_ADD, language, "§aYou have successfully added §f{0}§a to the timer!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_ONLY_SET_OR_ADD, language, "§cYou can only type \"set\" or \"add\"!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_IS_REVERSE, language, "§cThe timer is already in this direction!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_REVERSE, language, "§aYou have successfully changed the direction of the timer!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_IS_BOLD, language, "§cThe timer is already in this font!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_BOLD, language, "§aYou have successfully changed the font type of the timer!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_IS_STYLE, language, "§cThis timer style is already selected!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_STYLE, language, "§aYou have successfully changed the timer style!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_NOT_IN_LOBBY, language, "§cYou cannot run this command in the lobby!");
        MessageAPI.iniAddMessage(MessageID.TIMER_PAUSED, language, "§7The timer is paused!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_COLOR_SOLID, language, "§aYou have successfully changed the timer to a solid color!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_COLOR_SOLID_CUSTOM, language, "§aYou have successfully changed the timer to a custom solid color!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_COLOR_RGB_FADE, language, "§aYou have successfully changed the timer to an RGB fade!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_COLOR_RGB_TRANSITION, language, "§aYou have successfully changed the timer to an RGB transition!");
    }

}