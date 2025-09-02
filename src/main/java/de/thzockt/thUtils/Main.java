package de.thzockt.thUtils;

import de.thzockt.thUtils.APIs.InitialiseAPI.InitialiseAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Main extends JavaPlugin {

    private static Main plugin;
    public static Main getPlugin() {
        return plugin;
    }
    private static Main instance;
    private static BukkitScheduler scheduler;

    @Override
    public void onLoad() {
        // Plugin loading logic

        instance = this;
        plugin = this;

        // Loading all plugin stuff
        InitialiseAPI.loadPlugin();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        // initialising all plugin stuff
        InitialiseAPI.initialisePlugin();
        scheduler = this.getServer().getScheduler();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
    public static BukkitScheduler getScheduler() {
        return scheduler;
    }
}
