package me.plugin.waverush.manager;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.game.GameTask;
import me.plugin.waverush.model.Arena;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<Player, Arena> playing = new HashMap<>();

    // vytvoření arény
    public void createArena(String name, Arena arena) {
        arenas.put(name.toLowerCase(), arena);
    }

    // získání arény
    public Arena getArena(String name) {
        return arenas.get(name.toLowerCase());
    }

    // připojení do arény + start hry
    public void joinArena(Player player, String name) {
        Arena arena = getArena(name);
        if (arena == null) return;

        playing.put(player, arena);

        // teleport
        player.teleport(arena.getPos1());

        // 🔥 START GAME LOOP
        new GameTask(player, arena).runTaskTimer(
                WaveRushPlugin.getPlugin(WaveRushPlugin.class),
                0L,
                100L // 5 sekund
        );
    }

    // kontrola jestli hráč hraje
    public boolean isPlaying(Player player) {
        return playing.containsKey(player);
    }

    // opuštění arény
    public void leave(Player player) {
        playing.remove(player);
    }
}
