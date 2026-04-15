package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final ArenaManager arenaManager;

    public DeathListener(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        if (!arenaManager.isInArena(player)) return;

        arenaManager.sendToLobby(player);
    }
}
