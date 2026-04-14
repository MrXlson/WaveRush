package me.plugin.waverush.manager;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.game.GameTask;
import me.plugin.waverush.model.Arena;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<Player, Arena> playing = new HashMap<>();
    private final Map<Player, ItemStack[]> savedInventory = new HashMap<>();

    public void createArena(String name, Arena arena) {
        arenas.put(name.toLowerCase(), arena);
    }

    public Arena getArena(String name) {
        return arenas.get(name.toLowerCase());
    }

    public void joinArena(Player player, String name) {
        Arena arena = getArena(name);
        if (arena == null) return;

        // 💾 uloží inventář
        savedInventory.put(player, player.getInventory().getContents());

        // 🧹 vyčistí inventář
        player.getInventory().clear();

        playing.put(player, arena);

        player.teleport(arena.getPos1());

        new GameTask(player, arena).runTaskTimer(
                WaveRushPlugin.getPlugin(WaveRushPlugin.class),
                0L,
                100L
        );
    }

    public void leave(Player player) {
        playing.remove(player);

        // 🔙 vrátí inventář
        if (savedInventory.containsKey(player)) {
            player.getInventory().setContents(savedInventory.get(player));
            savedInventory.remove(player);
        }
    }

    public boolean isPlaying(Player player) {
        return playing.containsKey(player);
    }
}
