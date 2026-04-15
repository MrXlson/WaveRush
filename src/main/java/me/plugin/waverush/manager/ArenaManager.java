package me.plugin.waverush.manager;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private final WaveRushPlugin plugin;

    private final Map<String, Arena> arenas = new HashMap<>();
    private final Map<Player, Arena> playerArena = new HashMap<>();

    public ArenaManager(WaveRushPlugin plugin) {
        this.plugin = plugin;
    }

    // CREATE
    public void createArena(String name, Arena arena) {
        arenas.put(name, arena);
    }

    // JOIN
    public void joinArena(Player player, String name) {
        Arena arena = arenas.get(name);

        if (arena == null) {
            player.sendMessage("§cAréna neexistuje!");
            return;
        }

        if (playerArena.containsKey(player)) {
            player.sendMessage("§cUž jsi ve hře!");
            return;
        }

        arena.addPlayer(player);
        playerArena.put(player, arena);

        player.sendMessage("§aPřipojen do arény: " + name);
    }

    // GET ARENA BY PLAYER
    public Arena getArena(Player player) {
        return playerArena.get(player);
    }

    // REMOVE PLAYER
    public void removePlayer(Player player) {
        Arena arena = playerArena.get(player);

        if (arena != null) {
            arena.removePlayer(player);
            playerArena.remove(player);
        }
    }

    // PLAYER COUNT (🔥 FIX)
    public int getPlayerCount(String arenaName) {
        Arena arena = arenas.get(arenaName);
        if (arena == null) return 0;
        return arena.getPlayers().size();
    }

    // LOBBY TELEPORT
    public void sendToLobby(Player player) {
        if (plugin.getConfig().contains("lobby")) {

            String world = plugin.getConfig().getString("lobby.world");
            double x = plugin.getConfig().getDouble("lobby.x");
            double y = plugin.getConfig().getDouble("lobby.y");
            double z = plugin.getConfig().getDouble("lobby.z");
            float yaw = (float) plugin.getConfig().getDouble("lobby.yaw");
            float pitch = (float) plugin.getConfig().getDouble("lobby.pitch");

            Location loc = new Location(
                    Bukkit.getWorld(world), x, y, z, yaw, pitch
            );

            player.teleport(loc);
        }
    }

    public Map<String, Arena> getArenas() {
        return arenas;
    }
}
