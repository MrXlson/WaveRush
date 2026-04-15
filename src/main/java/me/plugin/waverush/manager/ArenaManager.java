package me.plugin.waverush.manager;

import me.plugin.waverush.model.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();
    private final List<Location> signs = new ArrayList<>();

    // ========================
    // ARENY
    // ========================

    public void createArena(String name, Arena arena) {
        arenas.put(name.toLowerCase(), arena);
    }

    public Arena getArena(String name) {
        return arenas.get(name.toLowerCase());
    }

    public Collection<Arena> getArenas() {
        return arenas.values();
    }

    // ========================
    // JOIN
    // ========================

    public boolean joinArena(Player player, String name) {
        Arena arena = getArena(name);
        if (arena == null) return false;

        arena.addPlayer(player);
        return true;
    }

    public boolean joinFirstAvailable(Player player) {
        for (Arena arena : arenas.values()) {
            arena.addPlayer(player);
            return true;
        }
        return false;
    }

    // ========================
    // LEAVE
    // ========================

    public void sendToLobby(Player player) {
        for (Arena arena : arenas.values()) {
            if (arena.getPlayers().contains(player)) {
                arena.removePlayer(player);
                return;
            }
        }
    }

    public boolean isInArena(Player player) {
        for (Arena arena : arenas.values()) {
            if (arena.getPlayers().contains(player)) return true;
        }
        return false;
    }

    // ========================
    // INFO
    // ========================

    public int getPlayerCount(String arenaName) {
        Arena arena = getArena(arenaName);
        return arena == null ? 0 : arena.getPlayers().size();
    }

    // ========================
    // SIGNY
    // ========================

    public void addSign(Location location) {
        signs.add(location);
    }

    public List<Location> getSigns() {
        return signs;
    }
}
