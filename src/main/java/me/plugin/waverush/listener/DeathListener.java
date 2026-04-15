package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.model.Arena;
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

        for (Arena arena : arenaManager.getArenas()) {

            if (!arena.getPlayers().contains(player)) continue;

            // 🔥 odstranění hráče z arény
            arena.removePlayer(player);

            player.sendMessage("§cZemřel jsi!");

            // 🔥 pokud nikdo nezůstal → konec hry
            if (arena.getPlayers().isEmpty()) {

                if (arena.getGameTask() != null) {
                    arena.getGameTask().cancel();
                }

                arena.setGameTask(null);
            }

            return;
        }
    }
}
