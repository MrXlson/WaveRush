package me.plugin.waverush.game;

import me.plugin.waverush.WaveRushPlugin;
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

    private final WaveRushPlugin plugin;
    private final Player player;
    private final Arena arena;
    private final Random random = new Random();

    private int wave = 1;

    public GameTask(WaveRushPlugin plugin, Player player, Arena arena) {
        this.plugin = plugin;
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

        int maxWave = plugin.getConfig().getInt("game.max-wave");

        if (wave > maxWave) {
            player.sendMessage("§6Vyhrál jsi arénu!");
            player.sendTitle("§6VÝHRA!", "§7Zvládl jsi všechny vlny!", 10, 60, 10);

            if (plugin.getConfig().getBoolean("reward.enabled")) {
                plugin.getConfig().getMapList("reward.items").forEach(map -> {
                    String material = (String) map.get("material");
                    int amount = (int) map.getOrDefault("amount", 1);

                    player.getInventory().addItem(
                            new org.bukkit.inventory.ItemStack(
                                    org.bukkit.Material.valueOf(material),
                                    amount
                            )
                    );
                });
            }

            cancel();
            return;
        }

        int base = plugin.getConfig().getInt("game.mobs-per-wave-base");
        int radius = plugin.getConfig().getInt("game.spawn-radius");

        int amount = base + wave;

        for (int i = 0; i < amount; i++) {
            Location baseLoc = player.getLocation();

            double x = baseLoc.getX() + (random.nextInt(radius * 2) - radius);
            double z = baseLoc.getZ() + (random.nextInt(radius * 2) - radius);

            Location spawn = new Location(baseLoc.getWorld(), x, baseLoc.getY(), z);

            baseLoc.getWorld().spawnEntity(spawn, EntityType.ZOMBIE);
        }

        player.sendMessage("§eWave " + wave + " §7| " + amount + " mobů");

        player.sendActionBar("§eWave: " + wave + " §7| §aKilly: " + getKills(player));

        wave++;
    }

    public static void addKill(Player player) {
        kills.put(player.getUniqueId(), kills.getOrDefault(player.getUniqueId(), 0) + 1);
    }

    public static int getKills(Player player) {
        return kills.getOrDefault(player.getUniqueId(), 0);
    }
}
