package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ArenaListener implements Listener {

    private final ArenaManager manager;
    private final SelectionManager selectionManager;

    public ArenaListener(ArenaManager manager, SelectionManager selectionManager) {
        this.manager = manager;
        this.selectionManager = selectionManager;
    }

    @EventHandler
    public void onSelect(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (player.getInventory().getItemInMainHand().getType() != Material.GOLDEN_HOE) return;

        if (e.getClickedBlock() == null) return;

        if (e.getAction().toString().contains("LEFT_CLICK")) {
            selectionManager.setPos1(player, e.getClickedBlock().getLocation());
            player.sendMessage(ChatColor.GREEN + "Pozice 1 nastavena!");
        } else if (e.getAction().toString().contains("RIGHT_CLICK")) {
            selectionManager.setPos2(player, e.getClickedBlock().getLocation());
            player.sendMessage(ChatColor.GREEN + "Pozice 2 nastavena!");
        }
    }
}
