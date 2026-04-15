package me.plugin.waverush.listener;

import me.plugin.waverush.game.GameTask;
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
    public void onMobKill(EntityDeathEvent e) {

        // 🔥 kontrola že killer je hráč
        if (!(e.getEntity().getKiller() instanceof Player player)) return;

        // 🔥 jen mobky (ne hráči)
        if (!(e.getEntity() instanceof Monster)) return;

        // 🔥 najdi arénu hráče
        for (Arena arena : arenaManager.getArenas()) {

            if (!arena.getPlayers().contains(player)) continue;

            GameTask gameTask = arena.getGameTask();
            if (gameTask == null) return;

            // 🔥 přičti kill
            gameTask.addKill(player);

            // 🔥 odečti moba z wave
            gameTask.mobKilled();

            return;
        }
    }
}
