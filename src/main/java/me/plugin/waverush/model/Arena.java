package me.plugin.waverush.model;

import me.plugin.waverush.game.GameTask;
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
}
