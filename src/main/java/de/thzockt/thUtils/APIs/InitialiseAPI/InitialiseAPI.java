package de.thzockt.thUtils.APIs.InitialiseAPI;

import de.thzockt.thUtils.APIs.InstanceAPI.InstanceAPI;
import de.thzockt.thUtils.APIs.LanguageAPI.LanguageAPI;
import de.thzockt.thUtils.APIs.LobbyAPI.LobbyAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.PortalAPI.PortalAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.TimerAPI.TimerCore;
import de.thzockt.thUtils.Commands.*;
import de.thzockt.thUtils.Main;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public class InitialiseAPI {

    private static PluginManager pluginManager = Main.getPlugin().getServer().getPluginManager();

    public static void loadPlugin() {
        // Loading Plugin stuff on the onLoad Method from the Main class
        loadAPIs();
    }

    public static void initialisePlugin() {
        // Initialising Plugin stuff on the onLoad Method from the Main class
        initialiseAPIs();
        initialiseCommands();
        initialiseTimer();
    }



    private static void initialiseAPIs() {
        // Initialising APIs from the Plugin
        initialiseLobbyAPI();
        initialiseMessageAPI();
    }
    private static void initialiseCommands() {
        Main.getInstance().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(CommandInstance.root.build());
            commands.registrar().register(CommandLobby.root.build(), List.of("lobby", "hub", "l"));
            commands.registrar().register(CommandLanguage.root.build());
            commands.registrar().register(CommandServermode.root.build());
            commands.registrar().register(CommandTest.root.build());
            commands.registrar().register(CommandTimer.root.build());
        });
    }

    private static void initialiseLobbyAPI() {
        // Initialising the Lobby API
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            LobbyAPI.createLobby();
        }, 60);
        pluginManager.registerEvents(new InstanceAPI(), Main.getPlugin());
        pluginManager.registerEvents(new PortalAPI(), Main.getPlugin());
        pluginManager.registerEvents(new LanguageAPI(), Main.getPlugin());
    }

    private static void initialiseMessageAPI() {
        // Initialising the Message API
        MessageAPI.initialiseMessages();
    }

    private static void loadAPIs() {
        // Loading APIs from the Plugin
        loadServerManagerAPI();
    }


    private static void loadServerManagerAPI() {
        // Loading the ServerManagerAPI's Config Data
        ServerManagerAPI.loadDataFromConfig();
    }

    private static void initialiseTimer() {
        TimerCore.run();
    }

}