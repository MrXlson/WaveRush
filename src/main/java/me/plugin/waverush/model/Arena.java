package me.plugin.waverush.model;

import me.plugin.waverush.game.GameTask;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private final String name;
    private final List<Player> players = new ArrayList<>();

    // 🔥 GAME TASK (wave systém)
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
    // PLAYERS
    // ========================

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean contains(Player player) {
        return players.contains(player);
    }

    // ========================
    // GAME TASK (WAVES)
    // ========================

    public GameTask getGameTask() {
        return gameTask;
    }

    public void setGameTask(GameTask gameTask) {
        this.gameTask = gameTask;
    }
}
