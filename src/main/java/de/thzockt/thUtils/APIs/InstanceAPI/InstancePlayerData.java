package de.thzockt.thUtils.APIs.InstanceAPI;

import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.APIs.PortalAPI.PortalAPI;
import de.thzockt.thUtils.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstancePlayerData {

    public static Location getPlayerLocation(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("location")) {
            playerData.toFileConfiguration().set("location", PortalAPI.getSpawn(instanceUUID));
            playerData.save();
        }
        return playerData.toFileConfiguration().getLocation("location");
    }

    public static double getPlayerHealth(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("health")) {
            playerData.toFileConfiguration().set("health", 20);
            playerData.save();
        }
        return playerData.toFileConfiguration().getDouble("health");
    }

    public static int getPlayerFood(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("food")) {
            playerData.toFileConfiguration().set("food", 20);
            playerData.save();
        }
        return playerData.toFileConfiguration().getInt("food");
    }

    public static float getPlayerSaturation(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("saturation")) {
            playerData.toFileConfiguration().set("saturation", 5);
            playerData.save();
        }
        return (float) playerData.toFileConfiguration().getDouble("saturation");
    }

    public static int getPlayerLevel(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("level")) {
            playerData.toFileConfiguration().set("level", 0);
            playerData.save();
        }
        return playerData.toFileConfiguration().getInt("level");
    }

    public static float getPlayerLevelProgress(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("levelProgress")) {
            playerData.toFileConfiguration().set("levelProgress", 0);
            playerData.save();
        }
        return (float) playerData.toFileConfiguration().getDouble("levelProgress");
    }

    public static GameMode getPlayerGamemode(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("gamemode")) {
            playerData.toFileConfiguration().set("gamemode", GameMode.SURVIVAL.toString());
            playerData.save();
        }
        return GameMode.valueOf(playerData.toFileConfiguration().getString("gamemode"));
    }

    public static boolean getPlayerFlying(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("flying")) {
            playerData.toFileConfiguration().set("flying", false);
            playerData.save();
        }
        return playerData.toFileConfiguration().getBoolean("flying");
    }

    public static boolean getPlayerGliding(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("gliding")) {
            playerData.toFileConfiguration().set("gliding", false);
            playerData.save();
        }
        return playerData.toFileConfiguration().getBoolean("gliding");
    }

    public static void getAndSetPlayerEffects(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        if (!playerData.toFileConfiguration().contains("effects")) {
            for (PotionEffectType type : PotionEffectType.values()) {
                String typeSTRING = type.getKey().toString();
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".duration", 0);
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".amplifier", 0);
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".ambient", false);
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".particles", false);
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".icon", false);
                playerData.save();
            }
        }

        for (PotionEffectType type : PotionEffectType.values()) {
            String typeSTRING = type.getKey().toString();
            int duration = playerData.toFileConfiguration().getInt("effects." + typeSTRING + ".duration");
            int amplifier = playerData.toFileConfiguration().getInt("effects." + typeSTRING + ".amplifier");
            boolean ambient = playerData.toFileConfiguration().getBoolean("effects." + typeSTRING + ".ambient");
            boolean particles = playerData.toFileConfiguration().getBoolean("effects." + typeSTRING + ".particles");
            boolean icon = playerData.toFileConfiguration().getBoolean("effects." + typeSTRING + ".icon");
            if (duration != 0) {
                player.addPotionEffect(new PotionEffect(type, duration, amplifier, ambient, particles, icon));
            }
        }
    }

    public static Inventory getPlayerEnderChest(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        if (!playerData.toFileConfiguration().contains("enderchest")) {
            ItemStack empty = new ItemStack(Material.AIR);
            for (int i = 0; i < (3*9); i++) {
                playerData.toFileConfiguration().set("enderchest.slot_" + i, empty);
            }
        }

        Inventory enderChest = Bukkit.createInventory(null, 3*9);
        for (int i = 0; i < (3*9); i++)
            enderChest.setItem(i, playerData.toFileConfiguration().getItemStack("enderchest.slot_" + i));

        return enderChest;
    }

    public static void getAndSetPlayerInventory(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));
        ItemStack empty = new ItemStack(Material.AIR);
        if (!playerData.toFileConfiguration().contains("inventory")) {
            playerData.toFileConfiguration().set("inventory.head", empty);
            playerData.toFileConfiguration().set("inventory.chest", empty);
            playerData.toFileConfiguration().set("inventory.legs", empty);
            playerData.toFileConfiguration().set("inventory.feet", empty);
            playerData.toFileConfiguration().set("inventory.offHand", empty);
            for (int i = 0; i < (4*9); i++) {
                playerData.toFileConfiguration().set("inventory.slot_" + i, empty);
            }
            playerData.save();
        }

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        PlayerInventory playerInventory = player.getInventory();
        playerInventory.setHelmet(playerData.toFileConfiguration().getItemStack("inventory.head"));
        playerInventory.setChestplate(playerData.toFileConfiguration().getItemStack("inventory.chest"));
        playerInventory.setLeggings(playerData.toFileConfiguration().getItemStack("inventory.legs"));
        playerInventory.setBoots(playerData.toFileConfiguration().getItemStack("inventory.feet"));
        playerInventory.setItemInOffHand(playerData.toFileConfiguration().getItemStack("inventory.offHand"));
        for (int i = 0; i < (4*9); i++) {
            playerInventory.setItem(i, playerData.toFileConfiguration().getItemStack("inventory.slot_" + i));
        }
    }

    public static void savePlayerLocation(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("location", player.getLocation());
        playerData.save();
    }

    public static void savePlayerHealth(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("health", player.getHealth());
        playerData.save();
    }

    public static void savePlayerFood(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("food", player.getFoodLevel());
        playerData.save();
    }

    public static void savePlayerSaturation(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("saturation", player.getSaturation());
        playerData.save();

    }

    public static void savePlayerLevel(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("level", player.getLevel());
        playerData.save();
    }

    public static void savePlayerLevelProgress(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("levelProgress", player.getExp());
        playerData.save();
    }

    public static void savePlayerGamemode(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("gamemode", player.getGameMode().toString());
        playerData.save();
    }

    public static void savePlayerFlying(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("flying", player.isFlying());
        playerData.save();
    }

    public static void savePlayerGliding(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("gliding", player.isGliding());
        playerData.save();
    }

    public static void savePlayerEffects(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        List<PotionEffectType> playerEffects = new ArrayList<>();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            String typeSTRING = effect.getType().getKey().toString();
            playerData.toFileConfiguration().set("effects." + typeSTRING + ".duration", effect.getDuration());
            playerData.toFileConfiguration().set("effects." + typeSTRING + ".amplifier", effect.getAmplifier());
            playerData.toFileConfiguration().set("effects." + typeSTRING + ".ambient", effect.isAmbient());
            playerData.toFileConfiguration().set("effects." + typeSTRING + ".particles", effect.hasParticles());
            playerData.toFileConfiguration().set("effects." + typeSTRING + ".icon", effect.hasIcon());
            playerData.save();
            playerEffects.add(effect.getType());
        }

        for (PotionEffectType type : PotionEffectType.values()) {
            if (!playerEffects.contains(type)) {
                String typeSTRING = type.getKey().toString();
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".duration", 0);
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".amplifier", 0);
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".ambient", false);
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".particles", false);
                playerData.toFileConfiguration().set("effects." + typeSTRING + ".icon", false);
                playerData.save();
            }
        }
    }

    public static void savePlayerEnderChest(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        for (int i = 0; i < (3*9); i++) {
            playerData.toFileConfiguration().set("enderchest.slot_" + i, player.getEnderChest().getItem(i));
        }
        playerData.save();
    }

    public static void savePlayerInventory(UUID instanceUUID, UUID playerUUID) {
        Config playerData = new Config(playerUUID.toString() + ".yml", new File(Main.getInstance().getDataFolder() + "/instanceData/" + instanceUUID + "/playerData/"));

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null)
            return;

        playerData.toFileConfiguration().set("inventory.head", player.getInventory().getHelmet());
        playerData.toFileConfiguration().set("inventory.chest", player.getInventory().getChestplate());
        playerData.toFileConfiguration().set("inventory.legs", player.getInventory().getLeggings());
        playerData.toFileConfiguration().set("inventory.feet", player.getInventory().getBoots());
        playerData.toFileConfiguration().set("inventory.offHand", player.getInventory().getItemInOffHand());
        for (int i = 0; i < (4*9); i++) {
            playerData.toFileConfiguration().set("inventory.slot_" + i, player.getInventory().getItem(i));
        }
        playerData.save();
    }

}