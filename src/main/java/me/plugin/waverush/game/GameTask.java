package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class GameTask extends BukkitRunnable {

    private final Player player;
    private final Arena arena;
    private final Random random = new Random();

    public GameTask(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
    }

    @Override
    public void run() {

        if (!player.isOnline()) {
            cancel();
            return;
        }

        // spawn 3 zombie kolem hráče
        for (int i = 0; i < 3; i++) {
            Location base = player.getLocation();

            double x = base.getX() + (random.nextInt(6) - 3);
            double z = base.getZ() + (random.nextInt(6) - 3);

            Location spawn = new Location(base.getWorld(), x, base.getY(), z);

            base.getWorld().spawnEntity(spawn, EntityType.ZOMBIE);
        }
    }
}
