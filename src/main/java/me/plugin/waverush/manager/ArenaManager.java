package me.plugin.waverush.manager;

import me.plugin.waverush.model.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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

        // ❌ zabrání duplicitnímu připojení
        if (arena.getPlayers().contains(player)) {
            player.sendMessage("§cUž jsi v této aréně!");
            return false;
        }

        // ❌ zabrání být ve více arénách
        if (isInArena(player)) {
            player.sendMessage("§cUž jsi v jiné aréně!");
            return false;
        }

        arena.addPlayer(player);

        // 🔥 auto start hry
        if (arena.getGameTask() == null) {
            arena.startGame();
        }

        return true;
    }

    public boolean joinFirstAvailable(Player player) {
        for (Arena arena : arenas.values()) {

            if (arena.getPlayers().contains(player)) return false;
            if (isInArena(player)) return false;

            arena.addPlayer(player);

            if (arena.getGameTask() == null) {
                arena.startGame();
            }

            return true;
        }
        return false;
    }

    // ========================
    // LEAVE
    // ========================

    public void sendToLobby(Player player, JavaPlugin plugin) {

        Location loc = plugin.getConfig().getLocation("lobby");

        if (loc != null) {
            player.teleport(loc);
        }

        for (Arena arena : arenas.values()) {
            if (arena.getPlayers().contains(player)) {
                arena.removePlayer(player);
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
