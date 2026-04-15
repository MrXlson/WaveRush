package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GameTask extends BukkitRunnable {

    private final Arena arena;
    private final JavaPlugin plugin;

    private int wave = 0;
    private int mobsAlive = 0;

    private final List<Entity> spawnedMobs = new ArrayList<>();

    public GameTask(Arena arena, JavaPlugin plugin) {
        this.arena = arena;
        this.plugin = plugin;
    }

    @Override
    public void run() {

        // Pokud nikdo není v aréně → stop
        if (arena.getPlayers().isEmpty()) {
            cancel();
            return;
        }

        // Pokud nejsou mobové → další wave
        if (mobsAlive <= 0) {
            startNextWave();
        }
    }

    private void startNextWave() {
        wave++;

        int amount = 3 + (wave * 2); // scaling

        mobsAlive = amount;

        arena.broadcast("§aVlna §e" + wave + " §a(" + amount + " mobů)");

        for (int i = 0; i < amount; i++) {
            LivingEntity mob = arena.spawnMob(); // MUSÍŠ mít v Arena.java
            if (mob != null) {
                spawnedMobs.add(mob);
            }
        }
    }

    // 🔥 TOTO TI CHYBĚLO
    public void mobKilled() {
        mobsAlive--;

        if (mobsAlive <= 0) {
            arena.broadcast("§aVlna dokončena!");
        }
    }

    public int getWave() {
        return wave;
    }

    public int getMobsAlive() {
        return mobsAlive;
    }

    public List<Entity> getSpawnedMobs() {
        return spawnedMobs;
    }
}
