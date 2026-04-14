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
import org.bukkit.inventory.EquipmentSlot;
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
        Player p = e.getPlayer();

        ItemStack item = e.getItem();
        if (item == null || item.getType() != Material.GOLDEN_HOE) return;

        // 🔥 FIX – ignoruje offhand (druhá ruka)
        if (e.getHand() != EquipmentSlot.HAND) return;

        if (e.getClickedBlock() == null) return;

        Action action = e.getAction();

        if (action == Action.LEFT_CLICK_BLOCK) {
            selectionManager.setPos1(p, e.getClickedBlock().getLocation());
            p.sendMessage(ChatColor.GREEN + "Pozice 1 nastavena!");
            e.setCancelled(true);
        }

        else if (action == Action.RIGHT_CLICK_BLOCK) {
            selectionManager.setPos2(p, e.getClickedBlock().getLocation());
            p.sendMessage(ChatColor.AQUA + "Pozice 2 nastavena!");
            e.setCancelled(true);
        }
    }
}
