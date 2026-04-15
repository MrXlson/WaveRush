package me.plugin.waverush.model;

import me.plugin.waverush.game.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class Arena {

    private final String name;

    private Location spawn;
    private Location lobby;

    private final List<Player> players = new ArrayList<>();
    private final List<LivingEntity> mobs = new ArrayList<>();

    private GameTask gameTask;

    private boolean running = false;

    public Arena(String name) {
        this.name = name;
    }

    // 📛 název
    public String getName() {
        return name;
    }

    // 📍 SPAWN
    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }

    // 🏠 LOBBY
    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public Location getLobby() {
        return lobby;
    }

    // 👥 HRÁČI
    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    // 💀 MOBOVÉ
    public void addMob(LivingEntity entity) {
        mobs.add(entity);
    }

    public void removeMob(LivingEntity entity) {
        mobs.remove(entity);
    }

    public List<LivingEntity> getMobs() {
        return mobs;
    }

    // 🎮 GAME TASK
    public void setGameTask(GameTask gameTask) {
        this.gameTask = gameTask;
    }

    public GameTask getGameTask() {
        return gameTask;
    }

    // 🚀 START GAME
    public void startGame() {

        if (running) return;

        if (spawn == null) {
            broadcast("§cSpawn není nastaven!");
            return;
        }

        running = true;

        for (Player player : players) {
            player.teleport(spawn);
            player.sendMessage("§aHra začíná!");
        }

        gameTask = new GameTask(this);
        gameTask.runTaskTimer(Bukkit.getPluginManager().getPlugin("WaveRush"), 0L, 20L);
    }

    // 🛑 STOP GAME
    public void stopGame() {

        running = false;

        if (gameTask != null) {
            gameTask.cancel();
        }

        for (LivingEntity mob : mobs) {
            mob.remove();
        }

        mobs.clear();

        for (Player player : players) {
            if (lobby != null) {
                player.teleport(lobby);
            }
            player.sendMessage("§cHra skončila!");
        }

        players.clear();
    }

    // 📢 MESSAGE
    public void broadcast(String msg) {
        for (Player player : players) {
            player.sendMessage(msg);
        }
    }

    public boolean isRunning() {
        return running;
    }
}
