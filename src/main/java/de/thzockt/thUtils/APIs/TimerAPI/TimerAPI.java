package de.thzockt.thUtils.APIs.TimerAPI;

import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import de.thzockt.thUtils.Main;

import java.io.File;
import java.util.UUID;

public class TimerAPI {

    public static boolean isActive(UUID instanceUUID) {
        if (!timerData(instanceUUID).toFileConfiguration().contains("active")) {
            timerData(instanceUUID).toFileConfiguration().set("active", true);
            timerData(instanceUUID).save();
        }
        return timerData(instanceUUID).toFileConfiguration().getBoolean("active");
    }

    public static void setActive(UUID instanceUUID, boolean active) {
        timerData(instanceUUID).toFileConfiguration().set("active", active);
        timerData(instanceUUID).save();
    }

    public static boolean isPaused(UUID instanceUUID) {
        if (!timerData(instanceUUID).toFileConfiguration().contains("paused")) {
            timerData(instanceUUID).toFileConfiguration().set("paused", true);
            timerData(instanceUUID).save();
        }
        return timerData(instanceUUID).toFileConfiguration().getBoolean("paused");
    }

    public static void setPaused(UUID instanceUUID, boolean paused) {
        timerData(instanceUUID).toFileConfiguration().set("paused", paused);
        timerData(instanceUUID).save();
    }

    public static long getTime(UUID instanceUUID) {
        if (!timerData(instanceUUID).toFileConfiguration().contains("time")) {
            timerData(instanceUUID).toFileConfiguration().set("time", 0);
            timerData(instanceUUID).save();
        }
        return timerData(instanceUUID).toFileConfiguration().getLong("time");
    }

    public static void setTime(UUID instanceUUID, long time) {
        timerData(instanceUUID).toFileConfiguration().set("time", time);
        timerData(instanceUUID).save();
    }

    private static Config timerData(UUID instanceUUID) {
        String path;
        if (ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING)) {
            path = "/instanceData/" + instanceUUID + "/";
        } else {
            path = "";
        }
        return new Config("timerData.yml", new File(Main.getInstance().getDataFolder() + path));
    }

}