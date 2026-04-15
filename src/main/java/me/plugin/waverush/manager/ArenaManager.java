package me.plugin.waverush.manager;

import me.plugin.waverush.model.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();
    private final List<Location> signs = new ArrayList<>();

    public void createArena(String name, Arena arena) {
        arenas.put(name, arena);
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }

    public Collection<Arena> getArenas() {
        return arenas.values();
    }

    public int getPlayerCount(String arenaName) {
        Arena arena = arenas.get(arenaName);
        if (arena == null) return 0;
        return arena.getPlayers().size();
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

    // 🔥 SIGNS
    public void addSign(Location loc) {
        signs.add(loc);
    }

    public List<Location> getSigns() {
        return signs;
    }
}
