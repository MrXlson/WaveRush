package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import me.plugin.waverush.util.GameMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final Arena arena;

    private int wave = 0;
    private int mobsAlive = 0;

    public GameTask(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {

        // ❌ žádní hráči → stop
        if (arena.getPlayers().isEmpty()) {
            cancel();
            return;
        }

        // 🔥 nová vlna
        if (mobsAlive <= 0) {
            wave++;
            mobsAlive = wave * 5;

            GameMessageUtil.sendWaveStart(arena, wave, mobsAlive);

            // TODO: sem pak přidáme spawn mobů
        }

        // 📊 průběh (každých 5 sekund)
        if (wave % 5 == 0) {
            for (Player p : arena.getPlayers()) {
                GameMessageUtil.sendProgress(p, wave, mobsAlive);
            }
        }
    }

    // 🔥 zavolá se při killu
    public void mobKilled(Player killer) {
        mobsAlive--;

        if (killer != null) {
            arena.addKill(killer);
            GameMessageUtil.sendKill(killer, arena.getKills(killer));
        }
    }

    public int getWave() {
        return wave;
    }

    public int getMobsAlive() {
        return mobsAlive;
    }
}
