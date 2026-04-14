package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final Arena arena;
    private int wave = 1;

    public GameTask(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {

        for (var loc : arena.mobSpawns) {
            for (int i = 0; i < wave; i++) {
                loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
            }
        }

        wave++;
    }
}
