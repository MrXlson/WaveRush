package me.plugin.waverush.listener;

import me.plugin.waverush.gui.KitGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LobbyListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals("§8Lobby Menu")) return;

        e.setCancelled(true);

        if (e.getCurrentItem() == null) return;

        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem().getType().name().equals("NETHER_STAR")) {
            KitGUI.open(player);
        }
    }
}
