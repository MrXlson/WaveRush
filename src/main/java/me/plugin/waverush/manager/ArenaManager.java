package me.plugin.waverush.manager;

import me.plugin.waverush.model.Arena;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<Player, Arena> playing = new HashMap<>();

    public void createArena(String name, Arena arena) {
        arenas.put(name.toLowerCase(), arena);
    }

    public Arena getArena(String name) {
        return arenas.get(name.toLowerCase());
    }

    public void joinArena(Player player, String name) {
        Arena arena = getArena(name);
        if (arena == null) return;

        playing.put(player, arena);

        // teleport na střed arény
        player.teleport(arena.getPos1());
    }

    public boolean isPlaying(Player player) {
        return playing.containsKey(player);
    }

    public void leave(Player player) {
        playing.remove(player);
    }
}
