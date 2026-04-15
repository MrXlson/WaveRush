package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameTask extends BukkitRunnable {

    private final Arena arena;

    private int wave = 0;
    private final List<LivingEntity> mobs = new ArrayList<>();

    private final Map<Player, Integer> kills = new HashMap<>();

    public GameTask(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {

        // pokud nejsou hráči → stop
        if (arena.getPlayers().isEmpty()) {
            cancel();
            return;
        }

        // pokud nejsou mobové → další wave
        if (mobs.isEmpty()) {
            nextWave();
            return;
        }

        // vyčisti mrtvé moby
        mobs.removeIf(mob -> mob == null || mob.isDead());
    }

    private void nextWave() {
        wave++;

        int amount = wave * 2;

        for (int i = 0; i < amount; i++) {
            LivingEntity mob = arena.spawnMob();
            if (mob != null) {
                mobs.add(mob);
            }
        }

        // info hráčům
        for (Player p : arena.getPlayers()) {
            p.sendMessage("§aWave §e" + wave + " §aspawnuta!");
        }
    }

    // ========================
    // KILLS
    // ========================

    public void addKill(Player player) {
        kills.put(player, kills.getOrDefault(player, 0) + 1);
    }

    public int getKills(Player player) {
        return kills.getOrDefault(player, 0);
    }

    public void mobKilled(LivingEntity mob) {
        mobs.remove(mob);
    }

    // ========================
    // INFO
    // ========================

    public int getWave() {
        return wave;
    }

    public int getRemainingMobs() {
        return mobs.size();
    }
}
