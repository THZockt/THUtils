package de.thzockt.thUtils.APIs.TimerAPI;

import de.thzockt.thUtils.Main;

public class TimerCore {

    public static void run() {
        Main.getScheduler().runTaskTimer(Main.getPlugin(),
                task -> {

            TimerInstanceManagement.validateInstances();
            TimerInstanceManagement.runTimer();
            TimerAPI.sendToAllPlayers();

            },
                0,1
        );
    }

}