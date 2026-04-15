package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
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

        if (arena.getPlayers().isEmpty()) {
            stopGame();
            return;
        }

        if (mobsAlive > 0) return;

        wave++;
        mobsAlive = wave * 3;

        for (Player p : arena.getPlayers()) {
            p.sendMessage("§6Vlna §e" + wave);
            p.sendActionBar("§eVlna: " + wave + " §7| §cMobů: " + mobsAlive);
        }

        Location spawn = arena.getSpawn();

        for (int i = 0; i < mobsAlive; i++) {
            spawn.getWorld().spawnEntity(spawn, EntityType.ZOMBIE);
        }
    }

    public void mobKilled() {
        mobsAlive--;
    }

    private void stopGame() {
        for (Player p : arena.getPlayers()) {
            p.sendMessage("§cHra ukončena!");
        }
        cancel();
    }
}
