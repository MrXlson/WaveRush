package me.plugin.waverush.manager;

import me.plugin.waverush.model.Arena;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();

    public void addArena(Arena arena) {
        arenas.put(arena.name.toLowerCase(), arena);
    }

    public Arena getArena(String name) {
        return arenas.get(name.toLowerCase());
    }
}
