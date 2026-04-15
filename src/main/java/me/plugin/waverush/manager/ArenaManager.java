package me.plugin.waverush.manager;

import me.plugin.waverush.model.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();
    private final List<Location> signs = new ArrayList<>();

    // =========================
    // 🔥 ARENA MANAGEMENT
    // =========================

    public void createArena(String name, Arena arena) {
        arenas.put(name.toLowerCase(), arena);
    }

    public Arena getArena(String name) {
        return arenas.get(name.toLowerCase());
    }

    public Collection<Arena> getArenas() {
        return arenas.values(); // ⚠️ už NEPOUŽÍVEJ .values() někde jinde
    }

    public boolean exists(String name) {
        return arenas.containsKey(name.toLowerCase());
    }

    // =========================
    // 🔥 JOIN SYSTEM
    // =========================

    public boolean joinArena(Player player, String name) {
        Arena arena = getArena(name);
        if (arena == null) return false;

        // max hráči (můžeš pak dát do configu)
        if (arena.getPlayers().size() >= 4) return false;

        arena.addPlayer(player);
        return true;
    }

    public boolean joinFirstAvailable(Player player) {
        for (Arena arena : arenas.values()) {
            if (arena.getPlayers().size() < 4) {
                arena.addPlayer(player);
                return true;
            }
        }
        return false;
    }

    // =========================
    // 🔥 LEAVE / LOBBY
    // =========================

    public void sendToLobby(Player player) {
        for (Arena arena : arenas.values()) {
            if (arena.getPlayers().contains(player)) {
                arena.removePlayer(player);
                break;
            }
        }
    }

    // =========================
    // 🔥 PLAYER INFO
    // =========================

    public int getPlayerCount(String arenaName) {
        Arena arena = getArena(arenaName);
        if (arena == null) return 0;
        return arena.getPlayers().size();
    }

    public boolean isInArena(Player player) {
        for (Arena arena : arenas.values()) {
            if (arena.getPlayers().contains(player)) return true;
        }
        return false;
    }

    // =========================
    // 🔥 SIGNS SYSTEM
    // =========================

    public void addSign(Location loc) {
        signs.add(loc);
    }

    public void removeSign(Location loc) {
        signs.remove(loc);
    }

    public List<Location> getSigns() {
        return signs;
    }
}
