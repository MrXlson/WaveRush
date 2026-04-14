package me.plugin.waverush.listener;

import me.plugin.waverush.WaveRushPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        player.sendMessage(ChatColor.RED + "Prohrál jsi!");
        player.sendTitle("§cPROHRA", "§7Zkus to znovu!", 10, 60, 10);

        WaveRushPlugin.getPlugin(WaveRushPlugin.class)
                .getArenaManager()
                .leave(player);
    }
}
