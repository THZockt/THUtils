package de.thzockt.thUtils.APIs.PortalAPI;

import de.thzockt.thUtils.APIs.InstanceAPI.InstanceAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerManagerAPI;
import de.thzockt.thUtils.APIs.ServerManagerAPI.ServerMode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.PortalType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Random;
import java.util.UUID;

public class PortalAPI implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (!ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING))
            return;
        World from = event.getFrom().getWorld();
        World to = event.getFrom().getWorld();
        Location toLocation = event.getTo();
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            if (to.getName().toLowerCase().contains("_nether")) {
                World newTo = Bukkit.getWorld(from.getName().replace("_nether", ""));
                event.setTo(new Location(newTo, toLocation.x(), toLocation.y(), toLocation.z(), toLocation.getYaw(), toLocation.getPitch()));
                return;
            }
            World newTo = Bukkit.getWorld(from.getName() + "_nether");
            event.setTo(new Location(newTo, toLocation.x(), toLocation.y(), toLocation.z(), toLocation.getYaw(), toLocation.getPitch()));
        }
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
            World newTo = Bukkit.getWorld(from.getName() + "_the_end");
            event.setTo(new Location(newTo, toLocation.x(), toLocation.y(), toLocation.z(), 90, 0));
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING))
            return;
        Player player = event.getPlayer();
        if (event.getRespawnReason().equals(PlayerRespawnEvent.RespawnReason.END_PORTAL)) {
            if (player.getRespawnLocation() == null) {
                Location spawn = getSpawn(UUID.fromString(InstanceAPI.playerInstancePositions.get(player.getUniqueId().toString())));
                if (spawn == null)
                    return;
                event.setRespawnLocation(spawn);
            }
        }
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        if (!ServerManagerAPI.getCurrentServerMode().equals(ServerMode.INSTANCING))
            return;
        Location from = event.getFrom();
        World fromWorld = from.getWorld();
        Location to = event.getTo();
        World toWorld = to.getWorld();
        if (event.getPortalType().equals(PortalType.NETHER)) {
            if (toWorld.getName().contains("_nether")) {
                event.setTo(new Location(Bukkit.getWorld(fromWorld.getName() + "_nether"), to.x(), to.y(), to.z(), to.getYaw(), to.getPitch()));
            } else {
                event.setTo(new Location(Bukkit.getWorld(fromWorld.getName().replace("_nether", "")), to.x(), to.y(), to.z(), to.getYaw(), to.getPitch()));
            }
        }
        if (event.getPortalType().equals(PortalType.ENDER)) {
            if (toWorld.getName().contains("_the_end")) {
                event.setTo(new Location(Bukkit.getWorld(fromWorld.getName() + "_the_end"), to.x(), to.y(), to.z(), to.getYaw(), to.getPitch()));
            } else {
                event.setTo(new Location(Bukkit.getWorld(fromWorld.getName().replace("_the_end", "")), to.x(), to.y(), to.z(), to.getYaw(), to.getPitch()));
            }
        }
    }

    public static Location getSpawn(UUID instanceUUID) {
        World instance = Bukkit.getWorld("instances/" + instanceUUID + "/world");

        if (instance == null)
            return null;

        Location spawnMiddle = instance.getSpawnLocation();
        Random random = new Random();

        int X = random.nextInt(Bukkit.getSpawnRadius() + 1);
        int Z = random.nextInt(Bukkit.getSpawnRadius() + 1);

        int xRaw = X - (Bukkit.getSpawnRadius() / 2);
        int zRaw = Z - (Bukkit.getSpawnRadius() / 2);

        double x = spawnMiddle.getBlockX() + xRaw + 0.5;
        double z = spawnMiddle.getBlockZ() + zRaw + 0.5;

        Location spawn = new Location(instance, x, instance.getHighestBlockYAt(spawnMiddle.getBlockX() + xRaw, spawnMiddle.getBlockZ() + zRaw) + 1, z);
        return spawn;
    }

}