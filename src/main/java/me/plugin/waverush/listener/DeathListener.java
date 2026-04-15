package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.model.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final ArenaManager arenaManager;

    public DeathListener(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    // PLAYER DEATH
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        if (!arenaManager.isInArena(player)) return;

        arenaManager.sendToLobby(player);
    }

    // MOB KILL
    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {

        if (!(e.getEntity().getKiller() instanceof Player player)) return;

        for (Arena arena : arenaManager.getArenas()) {

            if (arena.getPlayers().contains(player)) {

                if (arena.getGameTask() != null) {
                    arena.getGameTask().mobKilled();
                }
            }
        }
    }
}
