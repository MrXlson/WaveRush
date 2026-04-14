package me.plugin.waverush.listener;

import me.plugin.waverush.manager.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KitListener implements Listener {

    private final KitManager kitManager;

    public KitListener(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals("§8Výběr Kitu")) return;

        e.setCancelled(true);

        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        Player player = (Player) e.getWhoClicked();
        String name = e.getCurrentItem().getItemMeta().getDisplayName();

        if (name.contains("Warrior")) {
            kitManager.setKit(player, "warrior");
        } else if (name.contains("Archer")) {
            kitManager.setKit(player, "archer");
        } else if (name.contains("Mage")) {
            kitManager.setKit(player, "mage");
        }

        player.sendMessage("§aKit vybrán!");
        player.closeInventory();
    }
}
