package me.plugin.waverush.listener;

import me.plugin.waverush.game.GameTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillListener implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;

        Player player = e.getEntity().getKiller();

        GameTask.addKill(player);

        player.sendMessage("§aKill! Celkem: §e" + GameTask.getKills(player));
    }
}
