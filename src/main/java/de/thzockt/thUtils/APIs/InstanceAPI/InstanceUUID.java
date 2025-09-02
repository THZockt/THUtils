package de.thzockt.thUtils.APIs.InstanceAPI;

import de.thzockt.thUtils.APIs.ConfigAPI.Config;
import de.thzockt.thUtils.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstanceUUID {

    public static UUID getNewUUID() {
        UUID newUUID = UUID.randomUUID();
        while (!getUUIDs().contains(newUUID)) {
            return newUUID;
        }
        return null;
    }

    public static void addUUID(UUID uuid) {
        // gets config
        Config instanceIndex = new Config("index.yml", new File(Main.getInstance().getDataFolder() + "/instanceData/"));

        // gets list and adds new uuid
        List<UUID> list = getUUIDs();
        list.add(uuid);

        // prepare list for config
        List<String> forConfig = new ArrayList<>();
        for (UUID id : list) {
            forConfig.add(id.toString());
        }

        // save list in config
        instanceIndex.toFileConfiguration().set("UUIDs", forConfig);
        instanceIndex.save();
    }

    public static void removeUUID(UUID uuid) {
        // gets config
        Config instanceIndex = new Config("index.yml", new File(Main.getInstance().getDataFolder() + "/instanceData/"));

        // gets list and removes uuid
        List<UUID> list = getUUIDs();
        list.remove(uuid);

        // prepare list for config
        List<String> forConfig = new ArrayList<>();
        for (UUID id : list) {
            forConfig.add(id.toString());
        }

        // save list in config
        instanceIndex.toFileConfiguration().set("UUIDs", forConfig);
        instanceIndex.save();
    }

    public static boolean exists(UUID uuid) {
        List<UUID> uuids = getUUIDs();
        return uuids.contains(uuid);
    }

    public static List<UUID> getUUIDs() {
        // get config
        Config instanceIndex = new Config("index.yml", new File(Main.getInstance().getDataFolder() + "/instanceData/"));

        // check if list exists
        List<String> emptyList = new ArrayList<>();
        if (!instanceIndex.toFileConfiguration().contains("UUIDs")) {
            instanceIndex.toFileConfiguration().set("UUIDs", emptyList);
            instanceIndex.save();
        }

        // getting and converting the stringList
        List<String> uuidListSTRING = instanceIndex.toFileConfiguration().getStringList("UUIDs");
        List<UUID> uuidList = new ArrayList<>();
        for (String uuid : uuidListSTRING) {
            uuidList.add(UUID.fromString(uuid));
        }

        // returning the list
        return uuidList;
    }

}