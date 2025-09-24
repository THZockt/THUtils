package de.thzockt.thUtils.APIs.TimerAPI;

import de.thzockt.thUtils.APIs.InstanceAPI.InstanceAPI;
import de.thzockt.thUtils.APIs.InstanceAPI.InstanceUUID;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class TimerInstanceManagement {

    public static List<UUID> frozenInstances = new ArrayList<>();
    public static UUID normalUUID;

    public static boolean checkForTimer(UUID instanceUUID) {
        return TimerAPI.getStartingTime(instanceUUID) != -1;
    }

    public static void validateInstances() {
        for (UUID instanceUUID : InstanceAPI.getActiveInstances()) {
            if (!checkForTimer(instanceUUID)) {
                TimerAPI.setStartingTime(instanceUUID);
            }
        }
    }

    public static void runTimer() {
        if (!ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING)) {
            if (normalUUID == null) {
                normalUUID = InstanceUUID.getNewUUID();
            }
            if (TimerAPI.getStartingTimes().isEmpty()) {
                TimerAPI.setStartingTime(normalUUID);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                InstanceAPI.playerInstancePositions.put(player.getUniqueId().toString(), normalUUID.toString());
            }
        }

        TimerAPI.getStartingTimes().forEach((instanceUUID, startingTime) -> {
            if (!TimerAPI.isActive(instanceUUID)) {
                return;
            }

            if (TimerAPI.isFrozen(instanceUUID)) {
                return;
            }

            if (TimerAPI.isPaused(instanceUUID)) {
                return;
            }

            Calendar calendar = Calendar.getInstance();

            if (TimerAPI.getReverse(instanceUUID)) {
                if (TimerAPI.getTime(instanceUUID) <= 0) {
                    TimerAPI.setTime(instanceUUID, 0);
                    TimerAPI.setPaused(instanceUUID, true);
                }
            }

            if (TimerAPI.getTime(instanceUUID) < 0) {
                TimerAPI.setTime(instanceUUID, 0);
                TimerAPI.setStartingTime(instanceUUID);
            }

            if (TimerAPI.getReverse(instanceUUID)) {
                if ((calendar.getTimeInMillis() - startingTime) < (TimerAPI.saveIntervalInSeconds * 1000L)) {
                    TimerAPI.renewTime(instanceUUID);
                }
            } else {
                if ((calendar.getTimeInMillis() - startingTime) > (TimerAPI.saveIntervalInSeconds * 1000L)) {
                    TimerAPI.renewTime(instanceUUID);
                }
            }
        });
    }

}