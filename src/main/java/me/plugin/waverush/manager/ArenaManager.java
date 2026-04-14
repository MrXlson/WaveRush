package me.plugin.waverush.manager;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.game.GameTask;
import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<Player, Arena> playing = new HashMap<>();
    private final Map<Player, ItemStack[]> savedInventory = new HashMap<>();

    private final KitManager kitManager = new KitManager();

    // 🏠 Lobby (změň si souřadnice!)
    private final Location lobby = new Location(Bukkit.getWorld("world"), 0, 100, 0);

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

        // teleport do arény
        player.teleport(arena.getPos1());

        // 🎒 dá kit
        kitManager.giveKit(player);

        // ▶️ start hry
        new GameTask(player, arena).runTaskTimer(
                WaveRushPlugin.getPlugin(WaveRushPlugin.class),
                0L,
                100L
        );
    }

    public void leave(Player player) {
        playing.remove(player);

        // 🔙 teleport do lobby
        if (lobby.getWorld() != null) {
            player.teleport(lobby);
        }

        // 🔙 vrátí inventář
        if (savedInventory.containsKey(player)) {
            player.getInventory().setContents(savedInventory.get(player));
            savedInventory.remove(player);
        }
    }

    public boolean isPlaying(Player player) {
        return playing.containsKey(player);
    }

    public KitManager getKitManager() {
        return kitManager;
    }
}
