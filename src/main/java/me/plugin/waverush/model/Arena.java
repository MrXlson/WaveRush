package me.plugin.waverush.model;

import me.plugin.waverush.game.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Arena {

    private final String name;

    private final List<Player> players = new ArrayList<>();
    private final Map<Player, Integer> kills = new HashMap<>();

    private Location spawnLocation;
    private GameTask gameTask;

    public Arena(String name) {
        this.name = name;
    }

    // ========================
    // INFO
    // ========================

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    // ========================
    // PLAYER MANAGEMENT
    // ========================

    public void addPlayer(Player player) {
        players.add(player);

        // teleport na spawn
        if (spawnLocation != null) {
            player.teleport(spawnLocation);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
        kills.remove(player);

        // pokud nikdo nezbyde → stop hra
        if (players.isEmpty() && gameTask != null) {
            gameTask.cancel();
            gameTask = null;
        }
    }

    // ========================
    // SPAWN
    // ========================

    public void setSpawnLocation(Location location) {
        this.spawnLocation = location;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    // ========================
    // GAME
    // ========================

    public void startGame() {

        if (gameTask != null) return;

        this.gameTask = new GameTask(this);

        gameTask.runTaskTimer(
                Bukkit.getPluginManager().getPlugin("WaveRush"),
                20L,
                20L
        );
    }

    public GameTask getGameTask() {
        return gameTask;
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

    public Map<Player, Integer> getAllKills() {
        return kills;
    }
}
