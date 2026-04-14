package me.plugin.waverush.manager;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.game.GameTask;
import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ArenaManager {

    private final WaveRushPlugin plugin;

    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<Player, Arena> playing = new HashMap<>();
    private final Map<Player, ItemStack[]> savedInventory = new HashMap<>();

    // 🔥 NOVÉ – hráči v arénách
    private final Map<String, List<Player>> arenaPlayers = new HashMap<>();

    private final KitManager kitManager;

    public ArenaManager(WaveRushPlugin plugin) {
        this.plugin = plugin;
        this.kitManager = new KitManager(plugin);
    }

    public void createArena(String name, Arena arena) {
        arenas.put(name.toLowerCase(), arena);
        arenaPlayers.put(name.toLowerCase(), new ArrayList<>());
    }

    public Arena getArena(String name) {
        return arenas.get(name.toLowerCase());
    }

    public Map<String, Arena> getArenas() {
        return arenas;
    }

    public int getPlayerCount(String arena) {
        return arenaPlayers.getOrDefault(arena.toLowerCase(), new ArrayList<>()).size();
    }

    public List<Player> getPlayers(String arena) {
        return arenaPlayers.getOrDefault(arena.toLowerCase(), new ArrayList<>());
    }

    public Location getLobby() {
        String world = plugin.getConfig().getString("lobby.world");
        double x = plugin.getConfig().getDouble("lobby.x");
        double y = plugin.getConfig().getDouble("lobby.y");
        double z = plugin.getConfig().getDouble("lobby.z");
        float yaw = (float) plugin.getConfig().getDouble("lobby.yaw");
        float pitch = (float) plugin.getConfig().getDouble("lobby.pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public void setLobby(Location loc) {
        plugin.getConfig().set("lobby.world", loc.getWorld().getName());
        plugin.getConfig().set("lobby.x", loc.getX());
        plugin.getConfig().set("lobby.y", loc.getY());
        plugin.getConfig().set("lobby.z", loc.getZ());
        plugin.getConfig().set("lobby.yaw", loc.getYaw());
        plugin.getConfig().set("lobby.pitch", loc.getPitch());
        plugin.saveConfig();
    }

    public void joinArena(Player player, String name) {
        Arena arena = getArena(name);
        if (arena == null) return;

        savedInventory.put(player, player.getInventory().getContents());
        player.getInventory().clear();

        playing.put(player, arena);

        // 🔥 přidání hráče do arény
        arenaPlayers.get(name.toLowerCase()).add(player);

        player.teleport(arena.getPos1());

        kitManager.giveKit(player);

        new GameTask(plugin, player, arena).runTaskTimer(
                plugin,
                0L,
                plugin.getConfig().getInt("game.wave-delay-ticks")
        );
    }

    public void leave(Player player) {
        Arena arena = playing.remove(player);

        if (arena != null) {
            arenaPlayers.get(arena.getName().toLowerCase()).remove(player);
        }

        player.teleport(getLobby());

        if (savedInventory.containsKey(player)) {
            player.getInventory().setContents(savedInventory.get(player));
            savedInventory.remove(player);
        }
    }

    public KitManager getKitManager() {
        return kitManager;
    }
}
