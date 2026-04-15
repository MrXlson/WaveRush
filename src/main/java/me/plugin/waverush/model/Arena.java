package me.plugin.waverush.model;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private final String name;
    private final Location pos1;
    private final Location pos2;

    private final List<Player> players = new ArrayList<>();

    public Arena(String name, Location pos1, Location pos2) {
        this.name = name;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public String getName() {
        return name;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    // 🔥 ADD PLAYER
    public void addPlayer(Player player) {
        players.add(player);
    }

    // 🔥 REMOVE PLAYER
    public void removePlayer(Player player) {
        players.remove(player);
    }

    // 🔥 GET PLAYERS
    public List<Player> getPlayers() {
        return players;
    }

    // 🔥 END GAME
    public void endGame(boolean win) {
        for (Player player : players) {
            if (win) {
                player.sendMessage("§aVyhrál jsi!");
            } else {
                player.sendMessage("§cProhrál jsi!");
            }
        }

        players.clear();
    }
}
