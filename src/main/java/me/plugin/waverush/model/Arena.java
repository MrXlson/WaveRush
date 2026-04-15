package me.plugin.waverush.model;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.EntityType;

import java.util.*;

public class Arena {

    private final String name;
    private final List<Player> players = new ArrayList<>();

    private Location spawnLocation;

    private GameTask gameTask;

    public Arena(String name) {
        this.name = name;
    }

    // ========================
    // PLAYERS
    // ========================

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    // ========================
    // SPAWN
    // ========================

    public void setSpawnLocation(Location loc) {
        this.spawnLocation = loc;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public LivingEntity spawnMob() {
        if (spawnLocation == null) return null;

        return (LivingEntity) spawnLocation.getWorld().spawnEntity(
                spawnLocation,
                EntityType.ZOMBIE
        );
    }

    // ========================
    // GAME
    // ========================

    public void startGame() {
        if (gameTask != null) return;

        gameTask = new GameTask(this);
        gameTask.runTaskTimer(
                org.bukkit.Bukkit.getPluginManager().getPlugin("WaveRush"),
                0L,
                20L
        );
    }

    public void stopGame() {
        if (gameTask == null) return;

        gameTask.cancel();
        gameTask = null;
    }

    public GameTask getGameTask() {
        return gameTask;
    }

    public void setGameTask(GameTask task) {
        this.gameTask = task;
    }
}
