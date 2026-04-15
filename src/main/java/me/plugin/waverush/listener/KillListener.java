package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.model.Arena;
import org.bukkit.entity.Player;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillListener implements Listener {

    private final ArenaManager arenaManager;

    public KillListener(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {

        if (!(e.getEntity() instanceof Monster)) return;
        if (!(e.getEntity().getKiller() instanceof Player player)) return;

        Arena arena = getArena(player);
        if (arena == null) return;

        if (arena.getGameTask() != null) {
            arena.getGameTask().mobKilled(player);
        }
    }

    private Arena getArena(Player player) {
        for (Arena arena : arenaManager.getArenas()) {
            if (arena.getPlayers().contains(player)) {
                return arena;
            }
        }
        return null;
    }
}
