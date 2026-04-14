package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class GameTask extends BukkitRunnable {

    private static final HashMap<UUID, Integer> kills = new HashMap<>();

    private final Player player;
    private final Arena arena;
    private final Random random = new Random();

    private int wave = 1;

    public GameTask(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
        kills.put(player.getUniqueId(), 0);
    }

    @Override
    public void run() {

        if (!player.isOnline()) {
            cancel();
            return;
        }

        int amount = 2 + wave; // čím vyšší wave, tím víc mobů

        for (int i = 0; i < amount; i++) {
            Location base = player.getLocation();

            double x = base.getX() + (random.nextInt(6) - 3);
            double z = base.getZ() + (random.nextInt(6) - 3);

            Location spawn = new Location(base.getWorld(), x, base.getY(), z);

            base.getWorld().spawnEntity(spawn, EntityType.ZOMBIE);
        }

        player.sendMessage("§eWave " + wave + " §7| Spawnuje se " + amount + " mobů");

        wave++;
    }

    // 🔥 kill count
    public static void addKill(Player player) {
        UUID uuid = player.getUniqueId();
        kills.put(uuid, kills.getOrDefault(uuid, 0) + 1);
    }

    public static int getKills(Player player) {
        return kills.getOrDefault(player.getUniqueId(), 0);
    }
}
