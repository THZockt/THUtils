package de.thzockt.thUtils.APIs.LobbyAPI;

import de.thzockt.thUtils.APIs.InstanceAPI.InstanceAPI;
import de.thzockt.thUtils.APIs.InstanceAPI.InstancePlayerData;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class LobbyAPI {

    public static void createLobby() {
        // check if servermode is "instancing"
        if (!ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING))
            return;

        // creating the lobby
        WorldCreator worldCreator = new WorldCreator("Lobby");
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generator(LobbyChunkGenerator.lobbyGenerator);
        worldCreator.generateStructures(false);
        worldCreator.createWorld();

        // pasting the lobby-structure
        pasteLobbyStructure();
    }

    public static void movePlayerToLobby(Player player) {
        if (!InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString()).equals("lobby")) {
            InstanceAPI.savePlayerData(UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString())), player);
        }

        InstanceAPI.playerInstancePositions.put(player.getUniqueId().toString(), "lobby");
        World world = Bukkit.getWorld("Lobby");
        player.teleport(new Location(world, 0.5, 101, 0.5));
        player.setHealth(20);
        player.setSaturation(20);
        player.setLevel(0);
        player.setExp(0);
        player.setGameMode(GameMode.CREATIVE);
        player.getEnderChest().clear();
        player.getInventory().clear();
        for (PotionEffect effect : player.getActivePotionEffects()) {
            PotionEffectType type = effect.getType();
            player.removePotionEffect(type);
        }
        InstancePlayerData.revokeAllAdvancements(player);
    }

    private static void pasteLobbyStructure() {
        // checks is the lobby-structure has been created
        if (ServerManagerAPI.hasCreatedLobby)
            return;

        // pasting the structure
        World world = Bukkit.getWorld("Lobby");
        for (int x = -2; x < 3; x++) {
            for (int z = -2; z < 3; z++) {
                world.setType(x, 100, z, Material.STONE);
            }
        }
        world.setType(0, 100, 0, Material.BEDROCK);
        world.setType(0, 101, 0, Material.TORCH);

        // resetting the value
        ServerManagerAPI.setHasCreatedLobby(true);
    }

}