package de.thzockt.thUtils.APIs.LobbyAPI;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class LobbyChunkGenerator {

    public static ChunkGenerator lobbyGenerator = new ChunkGenerator() {
        @Override
        public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {

        }
    };

}