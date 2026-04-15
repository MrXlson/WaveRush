package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final ArenaManager arenaManager;

    public DeathListener(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        Arena arena = arenaManager.getArena(player);
        if (arena == null) return;

        // 🔥 odeber hráče z arény
        arena.removePlayer(player);

        Bukkit.getScheduler().runTaskLater(
                Bukkit.getPluginManager().getPlugin("WaveRush"),
                () -> player.spigot().respawn(),
                2L
        );

        // 🔥 teleport do lobby
        arenaManager.sendToLobby(player);

        player.sendMessage("§cZemřel jsi!");

        // 🔥 kontrola konce hry
        if (arena.getPlayers().isEmpty()) {
            arena.endGame(false); // lose
        }
    }
}
