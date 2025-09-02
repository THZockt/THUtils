package de.thzockt.thUtils.APIs.ServerManagerAPI;

import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.Main;

import java.io.File;

public class ServerManagerAPI {

    // the "serverMode" is a value that decides if the server runs normal or has the instancing system
    private static ServerMode currentServerMode;
    // the "hasCreatedLobby" value checks if the lobby has been created in the instancing mode
    public static boolean hasCreatedLobby;

    public static void loadDataFromConfig() {
        // getting config
        Config serverManagerData = new Config("serverData.yml", new File(Main.getInstance().getDataFolder() + "/serverData/"));

        // checking if the "serverMode" exists in the config
        if (!serverManagerData.toFileConfiguration().contains("serverMode")) {
            serverManagerData.toFileConfiguration().set("serverMode", String.valueOf(ServerMode.NORMAL));
            serverManagerData.save();
        }
        // checking if the value "hasCreatedLobby" exists in the config
        if (!serverManagerData.toFileConfiguration().contains("hasCreatedLobby")) {
            serverManagerData.toFileConfiguration().set("hasCreatedLobby", false);
            serverManagerData.save();
        }

        // getting the "serverMode" value from the config
        currentServerMode = ServerMode.valueOf(serverManagerData.toFileConfiguration().getString("serverMode"));
        // getting the "hasCreatedLobby" value from the config
        hasCreatedLobby = serverManagerData.toFileConfiguration().getBoolean("hasCreatedLobby");
    }

    public static void setServerMode(ServerMode serverMode) {
        Config serverManagerData = new Config("serverData.yml", new File(Main.getInstance().getDataFolder() + "/serverData/"));
        serverManagerData.toFileConfiguration().set("serverMode", String.valueOf(serverMode));
        serverManagerData.save();
    }

    public static void setHasCreatedLobby(boolean hasCreated) {
        Config serverManagerData = new Config("serverData.yml", new File(Main.getInstance().getDataFolder() + "/serverData/"));
        serverManagerData.toFileConfiguration().set("hasCreatedLobby", hasCreated);
        serverManagerData.save();
        hasCreatedLobby = hasCreated;
    }

    public static ServerMode getCurrentServerMode() {
        return currentServerMode;
    }

}