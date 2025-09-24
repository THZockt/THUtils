package de.thzockt.thUtils.APIs.TimerAPI;

import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.APIs.InstanceAPI.InstanceAPI;
import de.thzockt.thUtils.APIs.LanguageAPI.LanguageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import de.thzockt.thUtils.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class TimerAPI {

    private static HashMap<UUID, Long> startingTimes = new HashMap<>();
    private static HashMap<UUID, Config> configs = new HashMap<>();
    public static int saveIntervalInSeconds = 60;

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

    public static boolean isFrozen(UUID instanceUUID) {
        return TimerInstanceManagement.frozenInstances.contains(instanceUUID);
    }

    public static void setFrozen(UUID instanceUUID, boolean frozen) {
        if (frozen) {
            if (!isFrozen(instanceUUID))
                TimerInstanceManagement.frozenInstances.add(instanceUUID);
        } else {
            TimerInstanceManagement.frozenInstances.remove(instanceUUID);
        }
    }

    public static long getStartingTime(UUID instanceUUID) {
        if (startingTimes.containsKey(instanceUUID)) {
            return startingTimes.get(instanceUUID);
        } else {
            return -1;
        }
    }

    public static void renewTime(UUID instanceUUID) {
        setTime(instanceUUID, getTime(instanceUUID));
        setStartingTime(instanceUUID);
    }

    public static void setStartingTime(UUID instanceUUID) {
        Calendar calendar = Calendar.getInstance();
        startingTimes.put(instanceUUID, calendar.getTimeInMillis());
    }

    public static HashMap<UUID, Long> getStartingTimes() {
        return startingTimes;
    }

    public static long getTime(UUID instanceUUID) {
        Calendar calendar = Calendar.getInstance();
        if (getReverse(instanceUUID)) {
            return (getStartingTime(instanceUUID) + getTimeToAdd(instanceUUID)) - calendar.getTimeInMillis();
        } else {
            return (calendar.getTimeInMillis() - getStartingTime(instanceUUID)) + getTimeToAdd(instanceUUID);
        }
    }

    public static long getTimeToAdd(UUID instanceUUID) {
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

    public static void setReverse(UUID instanceUUID, boolean reverse) {
        timerData(instanceUUID).toFileConfiguration().set("reverse", reverse);
        timerData(instanceUUID).save();
    }

    public static boolean getReverse(UUID instanceUUID) {
        if (!timerData(instanceUUID).toFileConfiguration().contains("reverse")) {
            timerData(instanceUUID).toFileConfiguration().set("reverse", false);
            timerData(instanceUUID).save();
        }
        return timerData(instanceUUID).toFileConfiguration().getBoolean("reverse");
    }

    public static void setBold(UUID instanceUUID, boolean bold) {
        timerData(instanceUUID).toFileConfiguration().set("style.bold", bold);
        timerData(instanceUUID).save();
    }

    public static boolean getBold(UUID instanceUUID) {
        if (!timerData(instanceUUID).toFileConfiguration().contains("style.bold")) {
            timerData(instanceUUID).toFileConfiguration().set("style.bold", true);
            timerData(instanceUUID).save();
        }
        return timerData(instanceUUID).toFileConfiguration().getBoolean("style.bold");
    }

    public static void setStyle(UUID instanceUUID, Style style) {
        timerData(instanceUUID).toFileConfiguration().set("style.style", style.toString());
        timerData(instanceUUID).save();
    }

    public static Style getStyle(UUID instanceUUID) {
        if (!timerData(instanceUUID).toFileConfiguration().contains("style.style")) {
            timerData(instanceUUID).toFileConfiguration().set("style.style", Style.STANDARD2.toString());
            timerData(instanceUUID).save();
        }
        return Style.valueOf(timerData(instanceUUID).toFileConfiguration().getString("style.style"));
    }

    public static void setColor(UUID instanceUUID, ColorType colorType, String color1, String color2, int speed, int difference) {
        timerData(instanceUUID).toFileConfiguration().set("style.color.type", colorType.toString());
        timerData(instanceUUID).toFileConfiguration().set("style.color.color1", color1);
        timerData(instanceUUID).toFileConfiguration().set("style.color.color2", color2);
        timerData(instanceUUID).toFileConfiguration().set("style.color.speed", speed);
        timerData(instanceUUID).toFileConfiguration().set("style.color.difference", difference);
        timerData(instanceUUID).save();
    }

    public static String[] getColor(UUID instanceUUID) {
        if (!timerData(instanceUUID).toFileConfiguration().contains("style.color.type")) {
            timerData(instanceUUID).toFileConfiguration().set("style.color.type", ColorType.SOLID.toString());
            timerData(instanceUUID).save();
        }
        if (!timerData(instanceUUID).toFileConfiguration().contains("style.color.color1")) {
            timerData(instanceUUID).toFileConfiguration().set("style.color.color1", "6");
            timerData(instanceUUID).save();
        }
        if (!timerData(instanceUUID).toFileConfiguration().contains("style.color.color2")) {
            timerData(instanceUUID).toFileConfiguration().set("style.color.color2", "null");
            timerData(instanceUUID).save();
        }
        if (!timerData(instanceUUID).toFileConfiguration().contains("style.color.speed")) {
            timerData(instanceUUID).toFileConfiguration().set("style.color.speed", 0);
            timerData(instanceUUID).save();
        }
        if (!timerData(instanceUUID).toFileConfiguration().contains("style.color.difference")) {
            timerData(instanceUUID).toFileConfiguration().set("style.color.difference", 0);
            timerData(instanceUUID).save();
        }
        String colorType = timerData(instanceUUID).toFileConfiguration().getString("style.color.type");
        String color1 = timerData(instanceUUID).toFileConfiguration().getString("style.color.color1");
        String color2 = timerData(instanceUUID).toFileConfiguration().getString("style.color.color2");
        String speed = String.valueOf(timerData(instanceUUID).toFileConfiguration().getInt("style.color.speed"));
        String difference = String.valueOf(timerData(instanceUUID).toFileConfiguration().getInt("style.color.difference"));
        return new String[] {colorType, color1, color2, speed, difference};
    }

    public static void sendToAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!InstanceAPI.playerInstancePositions.containsKey(player.getUniqueId().toString())) {
                return;
            }
            if (InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
                return;
            }
            UUID instanceUUID;
            instanceUUID = UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()));
            sendTimer(player, instanceUUID);
        }
    }

    public static void sendTimer(Player player, UUID instanceUUID) {
        String timer = "69";
        String base = TimerStyles.getTimer(instanceUUID, getStyle(instanceUUID), getTime(instanceUUID), LanguageAPI.getPlayerLanguage(player));
        if (!isActive(instanceUUID)) {
            return;
        }
        if (isFrozen(instanceUUID)) {
            timer = "§o§b*§r§o" + base;
        }
        if (isPaused(instanceUUID)) {
            timer = MessageAPI.getMessage(MessageID.TIMER_PAUSED, LanguageAPI.getPlayerLanguage(player));
        }

        if (timer.equals("69")) {
            String[] colorData = getColor(instanceUUID);
            ColorType colorType = ColorType.valueOf(colorData[0]);
            String color1 = colorData[1];
            String color2 = colorData[2];
            int speed = Integer.parseInt(colorData[3]);
            int difference = Integer.parseInt(colorData[4]);

            Component colorized = TimerColorAPI.colorize(base, colorType, color1, color2, instanceUUID, speed, difference);
            Component finalTimer = TimerStyles.bold(colorized, instanceUUID);

            player.sendActionBar(finalTimer);
        } else {
            player.sendActionBar(timer);
        }
    }

    public static Config timerData(UUID instanceUUID) {
        if (!configs.containsKey(instanceUUID)) {
            String path;
            if (ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING)) {
                path = "/instanceData/" + instanceUUID + "/";
            } else {
                path = "";
            }
            Config config = new Config("timerData.yml", new File(Main.getInstance().getDataFolder() + path));
            configs.put(instanceUUID, config);
        }
        return configs.get(instanceUUID);
    }

}