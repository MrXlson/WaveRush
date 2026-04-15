package me.plugin.waverush.model;

import me.plugin.waverush.game.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private final String name;
    private final List<Player> players = new ArrayList<>();

    private Location spawn;
    private GameTask gameTask;

    public Arena(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        players.add(player);

        if (players.size() >= 1) {
            startCountdown();
        }
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
    // GAME
    // ========================

    public void startCountdown() {
        new me.plugin.waverush.task.CountdownTask(this)
                .runTaskTimer(Bukkit.getPluginManager().getPlugin("WaveRush"), 0L, 20L);
    }

    public void startGame() {
        gameTask = new GameTask(this);
        gameTask.runTaskTimer(
                Bukkit.getPluginManager().getPlugin("WaveRush"),
                0L, 40L
        );
    }

    public GameTask getGameTask() {
        return gameTask;
    }

    // ========================
    // SPAWN
    // ========================

    public void setSpawn(Location loc) {
        this.spawn = loc;
    }

    public Location getSpawn() {
        return spawn;
    }
}
