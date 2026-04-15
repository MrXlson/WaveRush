package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class GameTask extends BukkitRunnable {

    private final Arena arena;
    private int wave = 1;
    private int mobsLeft = 0;

    private final Map<Player, Integer> kills = new HashMap<>();

    public GameTask(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {

        if (arena.getPlayers().isEmpty()) {
            cancel();
            return;
        }

        startWave();
    }

    private void startWave() {
        mobsLeft = wave * 3; // jednoduchý scaling

        Bukkit.broadcastMessage("§a[WaveRush] Wave " + wave + " start! Mobů: " + mobsLeft);
    }

    // 🔥 VOLÁ SE PŘI KILLU MOBKY
    public void mobKilled() {
        mobsLeft--;

        if (mobsLeft <= 0) {
            wave++;
            startWave();
        }
    }

    // 🔥 KILLS
    public void addKill(Player player) {
        kills.put(player, kills.getOrDefault(player, 0) + 1);
    }

    public int getKills(Player player) {
        return kills.getOrDefault(player, 0);
    }

    public int getWave() {
        return wave;
    }
}
