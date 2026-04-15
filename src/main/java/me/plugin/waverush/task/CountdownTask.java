package me.plugin.waverush.task;

import me.plugin.waverush.model.Arena;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;

public class CountdownTask extends BukkitRunnable {

    private int time = 5;
    private final Arena arena;

    public CountdownTask(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {

        if (time == 0) {
            arena.startGame();
            cancel();
            return;
        }

        for (Player p : arena.getPlayers()) {
            p.sendMessage("§eStart za §6" + time);
        }

        time--;
    }
}
