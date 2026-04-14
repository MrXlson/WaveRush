package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
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
    private final int maxWave = 5;

    public GameTask(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
        kills.put(player.getUniqueId(), 0);
    }

    @Override
    public void run() {

        if (!player.isOnline() || player.isDead()) {
            cancel();
            return;
        }

        // 🏆 WIN
        if (wave > maxWave) {
            player.sendMessage("§6Vyhrál jsi arénu!");
            player.sendTitle("§6VÝHRA!", "§7Zvládl jsi všechny vlny!", 10, 60, 10);

            player.getInventory().addItem(
                    new org.bukkit.inventory.ItemStack(org.bukkit.Material.DIAMOND, 3)
            );

            cancel();
            return;
        }

        int amount = 2 + wave;

        for (int i = 0; i < amount; i++) {
            Location base = player.getLocation();

            double x = base.getX() + (random.nextInt(6) - 3);
            double z = base.getZ() + (random.nextInt(6) - 3);

            Location spawn = new Location(base.getWorld(), x, base.getY(), z);

            base.getWorld().spawnEntity(spawn, EntityType.ZOMBIE);
        }

        player.sendMessage("§eWave " + wave + " §7| Spawnuje se " + amount + " mobů");

        // 📊 Actionbar info
        player.sendActionBar("§eWave: " + wave + " §7| §aKilly: " + getKills(player));

        wave++;
    }

    public static void addKill(Player player) {
        UUID uuid = player.getUniqueId();
        kills.put(uuid, kills.getOrDefault(uuid, 0) + 1);
    }

    public static int getKills(Player player) {
        return kills.getOrDefault(player.getUniqueId(), 0);
    }
}
