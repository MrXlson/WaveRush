package me.plugin.waverush.model;

import me.plugin.waverush.game.GameTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private final String name;
    private final Location spawn;

    private final List<Player> players = new ArrayList<>();

    // GAME STATE
    private boolean running = false;
    private int wave = 0;

    // GAME TASK
    private GameTask gameTask;

    public Arena(String name, Location spawn) {
        this.name = name;
        this.spawn = spawn;
    }

    // ========================
    // PLAYERS
    // ========================

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
            player.teleport(spawn);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    // ========================
    // GAME
    // ========================

    public void startGame() {
        if (running) return;

        running = true;
        wave = 1;

        broadcast("§aHra začíná! Wave 1");
    }

    public void stopGame() {
        running = false;
        wave = 0;

        if (gameTask != null) {
            gameTask.cancel();
            gameTask = null;
        }

        broadcast("§cHra skončila!");
    }

    public boolean isRunning() {
        return running;
    }

    public int getWave() {
        return wave;
    }

    public void nextWave() {
        wave++;
        broadcast("§eDalší wave: " + wave);
    }

    // ========================
    // GAME TASK
    // ========================

    public void setGameTask(GameTask task) {
        this.gameTask = task;
    }

    public GameTask getGameTask() {
        return gameTask;
    }

    // ========================
    // MOB / KILLS
    // ========================

    private int mobsAlive = 0;

    public void setMobsAlive(int amount) {
        this.mobsAlive = amount;
    }

    public void mobKilled() {
        mobsAlive--;

        if (mobsAlive <= 0) {
            nextWave();
        }
    }

    // ========================
    // UTILS
    // ========================

    public void broadcast(String msg) {
        for (Player p : players) {
            p.sendMessage(msg);
        }
    }

    public String getName() {
        return name;
    }

    public Location getSpawn() {
        return spawn;
    }
}
