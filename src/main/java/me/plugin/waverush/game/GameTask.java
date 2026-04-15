package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class GameTask extends BukkitRunnable {

    private final Arena arena;

    private int wave = 0;
    private int mobsToKill = 0;
    private int mobsKilled = 0;

    private final Map<Player, Integer> kills = new HashMap<>();

    public GameTask(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {

        // Pokud arena nemá hráče → stop
        if (arena.getPlayers().isEmpty()) {
            cancel();
            return;
        }

        // Pokud ještě nezačala vlna → start
        if (mobsToKill == 0) {
            startNextWave();
            return;
        }

        // Kontrola dokončení vlny
        if (mobsKilled >= mobsToKill) {
            startNextWave();
        }
    }

    // ========================
    // WAVE START
    // ========================

    private void startNextWave() {
        wave++;
        mobsKilled = 0;
        mobsToKill = wave * 5; // každá vlna těžší

        for (Player player : arena.getPlayers()) {
            player.sendMessage("§aZačíná vlna §e" + wave + "§a!");
        }

        spawnWaveMobs();
    }

    // ========================
    // SPAWN MOBŮ
    // ========================

    private void spawnWaveMobs() {
        Location spawn = arena.getSpawn(); // MUSÍŠ mít v Arena

        for (int i = 0; i < mobsToKill; i++) {

            LivingEntity mob = (LivingEntity) spawn.getWorld().spawnEntity(
                    spawn,
                    getRandomMob()
            );

            mob.setCustomName("§cWave Mob");
            mob.setCustomNameVisible(true);
        }
    }

    private EntityType getRandomMob() {
        EntityType[] mobs = {
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.SPIDER
        };

        return mobs[(int) (Math.random() * mobs.length)];
    }

    // ========================
    // KILLS
    // ========================

    public void addKill(Player player) {
        mobsKilled++;

        kills.put(player, kills.getOrDefault(player, 0) + 1);

        // INFO hráči
        player.sendActionBar("§7Zabito: §e" + mobsKilled + "§7/§e" + mobsToKill);
    }

    public int getKills(Player player) {
        return kills.getOrDefault(player, 0);
    }

    public int getWave() {
        return wave;
    }
}
