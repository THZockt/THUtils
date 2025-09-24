package de.thzockt.thUtils.APIs.MessageAPI.Messages;

import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;

public class MessagesGerman {

    public void iniAddGermanMessages() {
        Language language = Language.GERMAN;

        //MessageAPI.iniAddMessage(MessageID., language, "");
        MessageAPI.iniAddMessage(MessageID.LANGUAGE_NOT_SET, language, "§6⚠ §cWARNUNG! Du hast deine Sprache noch nicht festgelegt! Keine sorge, alles wird trotzdem einwandfrei funktionieren aber es wird immer wenn du joinst diese Nachricht auftauchen...\nAlso wenn du deine Sprache ändern möchtest (und diese Nachricht entfernen möchtest), dann Tippe §6\"/language [language]\"§c ein um deine Sprache zu ändern!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_LANGUAGE_SET, language, "§aDu hast deine Sprache erfolgreich auf §f{0}§a gesetzt!");
        MessageAPI.iniAddMessage(MessageID.LOBBY_CURRENTLY_LOADING, language, "§c§lEs tut mir leid, aber die Lobby lädt gerade noch!\n§r§7Bitte warte einen Moment und komme gleich wieder...");
        MessageAPI.iniAddMessage(MessageID.INSTANCE_MODE_NOT_ACTIVATED, language, "§cDu kannst das nicht machen! Der Instanz Modus ist nicht aktiviert!");
        MessageAPI.iniAddMessage(MessageID.ONLY_IN_MINECRAFT, language, "§cDu kannst das nur in Minecraft machen!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_USAGE, language, "§cBitte benutze §6\"/instance [create,start,stop,join,delete]\"§c!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_USAGE_CREATE_NAME, language, "§cBitte benutze §6\"/instance create [name] [normal,minigame]\"§c! Du kannst den Name übrigens auch in \"Gänsefüßchen\" schreiben um Leertasten zu verwenden!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_USAGE_CREATE_MODE, language, "§cBitte benutze §6\"/instance create [name] [normal,minigame]\"§c um eine Instanz mit einem Benutzerdefinierten Namen und einem Instanz-Typen zu erstellen!");
        MessageAPI.iniAddMessage(MessageID.NAME_ALREADY_EXISTS, language, "§cDer Name §6{0}§c existiert bereits!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_NOT_EXISTING, language, "§cDie Instanz §6{0}§c existiert nicht!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_WORLD_CURRENTLY_LOADED, language, "§cDu kannst diese Instanz nicht löschen! Die Instanz §6{0}§c ist gerade noch geladen!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_CREATED_SUCCESSFUL, language, "§aDu hast erfolgreich die Instanz §f\"{0}\"§a erstellt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_ALREADY_RUNNING, language, "§cDie Instanz §6{0}§c läuft bereits!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_ALREADY_STOPPED, language, "§cDie Instanz §6{0}§c ist bereits gestoppt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_START, language, "§7Starte Instanz \"{0}\"...");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_STOP, language, "§7Stoppe Instanz \"{0}\"...");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_DELETE, language, "§7Entferne Instanz \"{0}\"...");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_JOIN, language, "§7Trete Instanz \"{0}\" bei...");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_START_SUCCESS, language, "§aDie Instanz §f\"{0}\"§a wurde erfolgreich gestartet!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_STOP_SUCCESS, language, "§aDie Instanz §f\"{0}\"§a wurde erfolgreich gestoppt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_DELETE_SUCCESS, language, "§aDie Instanz §f\"{0}\"§a wurde erfolgreich entfernt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_JOIN_SUCCESS, language, "§aDu bist der Instanz §f\"{0}\"§a erfolgreich beigetreten!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_ALREADY_CODE, language, "§cDu hast schon einen Bestätigungscode!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_CODE_SET, language, "§6⚠ §cWARNUNG! Wenn du den Servermodus ändern möchtest, kann es sein, dass alle deine Serverdaten dadurch verloren gehen, wenn du es wieder zurückstellen möchtest!\nWenn du dir sicher bist, dass du den Servermodus ändern möchtest, dann musst du deinen 16-Stelligen Bestätigungscode eingeben: §f{0}\n§cBitte führe den command nochmal mit deinem Code dahinter aus wie im folgendem Beispiel: §6\"/servermode {1} {0}\"§c!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_MISSING_CODE, language, "§cDu hast noch keinen Code! Führe den Befehl ohne deinen eingegebenen Code aus!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_WRONG_CODE, language, "§cDas ist der Falsche Code! Dein Code ist: §f{0}§c!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_MODE_NOT_EXISTING, language, "§cDer Modus §6\"{0}\"§c existiert nicht!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_ALREADY_MODE, language, "§cDieser Modus ist bereits auf diesem Server ausgewählt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_SERVERMODE_REQUIRED_RESTART, language, "§aDu hast erfolgreich den Servermodus zu §f\"{0}\"§a umgeändert! Um diese Änderung vollständig anzunehmen, musst du den Server neustarten!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_INSTANCE_JOIN_FAIL, language, "§cDie Instanz §6\"{0}\"§c ist zurzeit nicht online!");
        MessageAPI.iniAddMessage(MessageID.STYLES_DAYS, language, "Tage");
        MessageAPI.iniAddMessage(MessageID.STYLES_DAY, language, "Tag");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_ALREADY_RESUMED, language, "§cDer Timer ist bereits Fortgesetzt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_ALREADY_PAUSED, language, "§cDer Timer ist bereits Pausiert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_RESUMED, language, "§aDu hast den Timer erfolgreich fortgesetzt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_PAUSED, language, "§aDu hast den Timer erfolgreich pausiert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_TOGGLED, language, "§aDu hast den Timer erfolgreich umgeschaltet!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_RESET, language, "§aDu hast den Timer erfolgreich zurückgesetzt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_SET, language, "§aDu hast den Timer erfolgreich auf §f{0}§a gesetzt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_ADD, language, "§aDu hast erfolgreich §f{0}§a zum Timer hinzugefügt!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_ONLY_SET_OR_ADD, language, "§cDu kannst nur \"set\" oder \"add\" auswählen!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_IS_REVERSE, language, "§cDer Timer läuft schon in diese Richtung!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_REVERSE, language, "§aDu hast erfolgreich die Richtung des Timers geändert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_IS_BOLD, language, "§cDie Schriftart des Timers ist bereits in diesem zustand!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_BOLD, language, "§aDu hast erfolgreich die Schriftart des Timers geändert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_IS_STYLE, language, "§cDer Timer ist bereits in diesem Stil!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_SUCCESSFULLY_STYLE, language, "§aDu hast erfolgreich den Stil des Timers geändert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_NOT_IN_LOBBY, language, "§cDu kannst diesen Command nicht in der Lobby ausführen!");
        MessageAPI.iniAddMessage(MessageID.TIMER_PAUSED, language, "§7Der Timer ist pausiert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_COLOR_SOLID, language, "§aDu hast die Timerfarbe erfolgreich zu einer soliden Farbe geändert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_COLOR_SOLID_CUSTOM, language, "§aDu hast die Timerfarbe zu einer benutzerdefinierten soliden Farbe geändert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_COLOR_RGB_FADE, language, "§aDu hast die Timerfarbe erfolgreich zu einem RGB-Verwischen geändert!");
        MessageAPI.iniAddMessage(MessageID.COMMAND_TIMER_COLOR_RGB_TRANSITION, language, "§aDu hast die Timerfarbe erfolgreich zu einem RGB-Übergang geändert!");
    }

}