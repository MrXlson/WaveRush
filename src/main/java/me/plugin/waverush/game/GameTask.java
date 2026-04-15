package me.plugin.waverush.game;

import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameTask extends BukkitRunnable {

    private final Arena arena;

    private int wave = 0;
    private int mobsToSpawn = 0;
    private int mobsAlive = 0;

    private final Random random = new Random();

    public GameTask(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {

        // ❌ žádní hráči → konec
        if (arena.getPlayers().isEmpty()) {
            cancel();
            return;
        }

        // 🔁 pokud není žádný mob → nová vlna
        if (mobsAlive <= 0) {
            startNextWave();
        }
    }

    private void startNextWave() {
        wave++;

        mobsToSpawn = 5 + (wave * 2);
        mobsAlive = mobsToSpawn;

        // 📢 info hráčům
        for (Player player : arena.getPlayers()) {
            player.sendMessage("§6§lWave " + wave + " §7(" + mobsToSpawn + " mobů)");
        }

        // spawn mobů
        for (int i = 0; i < mobsToSpawn; i++) {
            spawnMob();
        }
    }

    private void spawnMob() {

        Location spawn = arena.getSpawn();
        if (spawn == null) return;

        EntityType type = getRandomMob();

        LivingEntity mob = (LivingEntity) spawn.getWorld().spawnEntity(spawn, type);

        // ❤️ základní úpravy moba
        mob.setCustomName("§cMob §7[Wave " + wave + "]");
        mob.setCustomNameVisible(true);
        mob.setMaxHealth(20 + (wave * 2));
        mob.setHealth(mob.getMaxHealth());

        // ➕ uložíme do arény
        arena.addMob(mob);
    }

    private EntityType getRandomMob() {
        EntityType[] mobs = {
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.SPIDER
        };

        return mobs[random.nextInt(mobs.length)];
    }

    // ☠️ zavolá se při smrti moba
    public void mobKilled(LivingEntity entity) {

        if (!arena.getMobs().contains(entity)) return;

        arena.removeMob(entity);
        mobsAlive--;

        // 📊 info hráčům
        for (Player player : arena.getPlayers()) {
            player.sendMessage("§7Zbývá mobů: §c" + mobsAlive);
        }
    }

    public int getWave() {
        return wave;
    }
}
