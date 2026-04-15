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

    // 🔥 KILLY
    private final Map<Player, Integer> kills = new HashMap<>();

    public GameTask(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {

        // ❌ pokud není dost hráčů → stop
        if (arena.getPlayers().isEmpty()) {
            cancel();
            return;
        }

        // 🔥 DEBUG (zatím)
        Bukkit.broadcastMessage("§a[WaveRush] Wave " + wave + " začíná!");

        // 👉 TODO: tady později spawn mobů

        wave++;
    }

    // ========================
    // 🔥 KILLS SYSTEM
    // ========================

    public void addKill(Player player) {
        kills.put(player, kills.getOrDefault(player, 0) + 1);
    }

    public int getKills(Player player) {
        return kills.getOrDefault(player, 0);
    }

    // ========================
    // 🔥 GETTERY
    // ========================

    public int getWave() {
        return wave;
    }
}
