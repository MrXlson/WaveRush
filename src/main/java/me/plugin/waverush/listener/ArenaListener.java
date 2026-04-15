package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ArenaListener implements Listener {

    private final ArenaManager arenaManager;
    private final SelectionManager selectionManager;

    public ArenaListener(ArenaManager arenaManager, SelectionManager selectionManager) {
        this.arenaManager = arenaManager;
        this.selectionManager = selectionManager;
    }

    @EventHandler
    public void onSelect(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        // musí držet motyku
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() != Material.WOODEN_HOE) return;

        // jen admin
        if (!player.hasPermission("waverush.select")) return;

        Action action = e.getAction();

        // LEFT CLICK = POS1
        if (action == Action.LEFT_CLICK_BLOCK) {
            e.setCancelled(true);

            selectionManager.setPos1(player, e.getClickedBlock().getLocation());
            player.sendMessage(ChatColor.GREEN + "Pos1 nastaven!");
        }

        // RIGHT CLICK = POS2
        if (action == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);

            selectionManager.setPos2(player, e.getClickedBlock().getLocation());
            player.sendMessage(ChatColor.GREEN + "Pos2 nastaven!");
        }
    }
}
