package de.thzockt.thUtils.APIs.InstanceAPI;

import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.APIs.LanguageAPI.LanguageAPI;
import de.thzockt.thUtils.APIs.LobbyAPI.LobbyAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import de.thzockt.thUtils.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class InstanceAPI implements Listener {

    public static HashMap<String, String> playerInstancePositions = new HashMap<>();
    private static List<UUID> activeInstances = new ArrayList<>();
    private static HashMap<World, Boolean> announcements = new HashMap<>();

    public static void checkPlayerPostion(Player player) {
        if (!playerInstancePositions.containsKey(player.getUniqueId().toString())) {
            playerInstancePositions.put(player.getUniqueId().toString(), "lobby");
        }
        LobbyAPI.movePlayerToLobby(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING))
            return;
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("Lobby");
        if (world == null) {
            player.kick(Component.text(MessageAPI.getMessage(MessageID.LOBBY_CURRENTLY_LOADING, LanguageAPI.getPlayerLanguage(player))));
            return;
        }
        checkPlayerPostion(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING))
            return;
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("Lobbby");
        if (world == null)
            return;
        if (playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby"))
            return;
        savePlayerData(UUID.fromString(playerInstancePositions.get(player.getUniqueId().toString())), player);
    }

    public static boolean checkIfNameExists(String name) {
        boolean exists = false;
        for (UUID uuid : InstanceUUID.getUUIDs()) {
            if (getInstanceName(uuid).equals(name)) {
                exists = true;
            }
        }
        return exists;
    }

    public static UUID getInstanceUUID(String name) {
        Config instanceUUIDs = new Config("uuids.yml", new File(Main.getInstance().getDataFolder() + "/instanceData/"));
        if (!instanceUUIDs.toFileConfiguration().contains(name))
            return null;
        return UUID.fromString(instanceUUIDs.toFileConfiguration().getString(name));
    }

    public static String getInstanceName(UUID uuid) {
        Config instanceNames = new Config("names.yml", new File(Main.getInstance().getDataFolder() + "/instanceData/"));
        if (!instanceNames.toFileConfiguration().contains(uuid.toString()))
            return null;
        return instanceNames.toFileConfiguration().getString(uuid.toString());
    }

    public static void createNewInstance(String name, InstanceMode mode, String creatorName) {
        // getting new UUID and defining the creationTimestamp
        UUID instanceUUID = InstanceUUID.getNewUUID();
        long creationTimeStamp = Calendar.getInstance().getTimeInMillis();

        // getting the config
        Config instanceData = new Config("data.yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID.toString() + "/"));
        Config instanceNames = new Config("names.yml", new File(Main.getInstance().getDataFolder() + "/instanceData/"));
        Config instanceUUIDs = new Config("uuids.yml", new File(Main.getInstance().getDataFolder() + "/instanceData/"));

        // adding the data to the config
        instanceData.toFileConfiguration().set("name", name);
        instanceData.toFileConfiguration().set("uuid", instanceUUID.toString());
        instanceData.toFileConfiguration().set("mode", mode.toString());
        instanceData.toFileConfiguration().set("creatorName", creatorName);
        instanceData.toFileConfiguration().set("creationTimestamp", creationTimeStamp);

        // adding name and uuid in index
        InstanceUUID.addUUID(instanceUUID);
        instanceNames.toFileConfiguration().set(instanceUUID.toString(), name);
        instanceUUIDs.toFileConfiguration().set(name, instanceUUID.toString());

        // saving index
        instanceNames.save();
        instanceUUIDs.save();

        // saving the config
        instanceData.save();
    }

    public static void startInstance(UUID instanceUUID) {
        if (!InstanceUUID.exists(instanceUUID))
            return;

        activeInstances.add(instanceUUID);
        loadWorlds(instanceUUID);
    }

    public static void stopInstance(UUID instanceUUID) {
        if (!InstanceUUID.exists(instanceUUID))
            return;

        World world = Bukkit.getWorld("instances/" + instanceUUID.toString() + "/world");
        World nether = Bukkit.getWorld("instances/" + instanceUUID.toString() + "/world_nether");
        World the_end = Bukkit.getWorld("instances/" + instanceUUID.toString() + "/world_the_end");

        if (world == null) {
            return;
        }
        if (nether == null) {
            return;
        }
        if (the_end == null) {
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerInstancePositions.get(player.getUniqueId().toString()).equals(instanceUUID.toString())) {
                LobbyAPI.movePlayerToLobby(player);
            }
        }

        activeInstances.remove(instanceUUID);
        Bukkit.unloadWorld(world, true);
        Bukkit.unloadWorld(nether, true);
        Bukkit.unloadWorld(the_end, true);
    }

    public static void deleteInstance(UUID instanceUUID) {
        if (!InstanceUUID.exists(instanceUUID))
            return;

        World world = Bukkit.getWorld("instances/" + instanceUUID.toString() + "/world");
        World nether = Bukkit.getWorld("instances/" + instanceUUID.toString() + "/world_nether");
        World the_end = Bukkit.getWorld("instances/" + instanceUUID.toString() + "/world_the_end");

        if (world != null) {
            return;
        }
        if (nether != null) {
            return;
        }
        if (the_end != null) {
            return;
        }

        try {
            FileUtils.deleteDirectory(new File("instances/" + instanceUUID));
            FileUtils.deleteDirectory(new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        InstanceUUID.removeUUID(instanceUUID);
    }

    public static void joinInstance(UUID instanceUUID, Player player) {
        if (instanceUUID == null)
            return;

        World world = Bukkit.getWorld("instances/" + instanceUUID + "/world");
        if (world == null)
            return;

        if (!playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
            savePlayerData(UUID.fromString(playerInstancePositions.get(player.getUniqueId().toString())), player);
        }

        playerInstancePositions.put(player.getUniqueId().toString(), instanceUUID.toString());

        mute();

        Location tpLocation = world.getSpawnLocation();
        player.teleport(tpLocation);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            PotionEffectType type = effect.getType();
            player.removePotionEffect(type);
        }

        InstancePlayerData.revokeAllAdvancements(player);

        loadPlayerData(UUID.fromString(playerInstancePositions.get(player.getUniqueId().toString())), player);

        unmute();
    }

    private static void loadWorlds(UUID instanceUUID) {
        WorldCreator instanceCreatorOverworld = new WorldCreator("instances/" + instanceUUID.toString() + "/world");
        instanceCreatorOverworld.generateStructures(true);
        instanceCreatorOverworld.environment(World.Environment.NORMAL);
        instanceCreatorOverworld.createWorld();

        WorldCreator instanceCreatorNether = new WorldCreator("instances/" + instanceUUID + "/world_nether");
        instanceCreatorNether.generateStructures(true);
        instanceCreatorNether.environment(World.Environment.NETHER);
        instanceCreatorNether.createWorld();

        WorldCreator instanceCreatorEnd = new WorldCreator("instances/" + instanceUUID + "/world_the_end");
        instanceCreatorEnd.generateStructures(true);
        instanceCreatorEnd.environment(World.Environment.THE_END);
        instanceCreatorEnd.createWorld();
    }

    public static List<UUID> getActiveInstances() {
        return activeInstances;
    }

    // ----------[ PlayerData Management ]----------

    public static void loadPlayerData(UUID instanceUUID, Player player) {
        UUID playerUUID = player.getUniqueId();
        player.teleport(InstancePlayerData.getPlayerLocation(instanceUUID, playerUUID));
        player.setHealth(InstancePlayerData.getPlayerHealth(instanceUUID, playerUUID));
        player.setFoodLevel(InstancePlayerData.getPlayerFood(instanceUUID, playerUUID));
        player.setSaturation(InstancePlayerData.getPlayerSaturation(instanceUUID, playerUUID));
        InstancePlayerData.getAndSetPlayerAdvancements(instanceUUID, playerUUID);
        player.setLevel(InstancePlayerData.getPlayerLevel(instanceUUID, playerUUID));
        player.setExp(InstancePlayerData.getPlayerLevelProgress(instanceUUID, playerUUID));
        player.setGameMode(InstancePlayerData.getPlayerGamemode(instanceUUID, playerUUID));
        player.setFlying(InstancePlayerData.getPlayerFlying(instanceUUID, playerUUID));
        player.setGliding(InstancePlayerData.getPlayerGliding(instanceUUID, playerUUID));
        InstancePlayerData.getAndSetPlayerEffects(instanceUUID, playerUUID);
        Inventory enderChest = InstancePlayerData.getPlayerEnderChest(instanceUUID, playerUUID);
        for (int i = 0; i < (3*9); i++) {
            player.getEnderChest().setItem(i, enderChest.getItem(i));
        }
        InstancePlayerData.getAndSetPlayerInventory(instanceUUID, playerUUID);
    }

    public static void savePlayerData(UUID instanceUUID, Player player) {
        UUID playerUUID = player.getUniqueId();
        InstancePlayerData.savePlayerLocation(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerHealth(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerFood(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerSaturation(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerAdvancements(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerLevel(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerLevelProgress(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerGamemode(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerFlying(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerGliding(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerEffects(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerEnderChest(instanceUUID, playerUUID);
        InstancePlayerData.savePlayerInventory(instanceUUID, playerUUID);
    }

    @EventHandler
    public static void onWorldSave(WorldSaveEvent event) {
        if (ServerManagerAPI.getCurrentServerMode() != ServerMode.INSTANCING)
            return;

        String name = event.getWorld().getName();
        String id;

        id = name.replaceAll("instances/", "");
        id = id.replaceAll("/world", "");
        id = id.replaceAll("/world_nether", "");
        id = id.replaceAll("/world_the_end", "");

        if (id.contains("_"))
            return;

        if (id.length() > 37)
            return;

        if (id.toLowerCase().contains("world"))
            return;

        if (id.toLowerCase().contains("lobby"))
            return;

        UUID instanceUUID = UUID.fromString(id);

        if (!InstanceUUID.exists(instanceUUID))
            return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerInstancePositions.get(player.getUniqueId().toString()).equals(instanceUUID.toString())) {
                savePlayerData(instanceUUID, player);
            }
        }
    }

    private static void mute() {
        for (World world : Bukkit.getWorlds()) {
            announcements.put(world, world.getGameRuleValue(GameRule.ANNOUNCE_ADVANCEMENTS));
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        }
    }

    private static void unmute() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, announcements.get(world));
        }
    }

}