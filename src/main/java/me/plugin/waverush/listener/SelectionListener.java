package me.plugin.waverush.listener;

import me.plugin.waverush.manager.SelectionManager;
import me.plugin.waverush.model.Selection;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectionListener implements Listener {

    private final SelectionManager manager;

    public SelectionListener(SelectionManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if (e.getItem() == null) return;

        Selection sel = manager.get(p.getUniqueId());

        if (e.getItem().getType() == Material.GOLDEN_HOE) {

            if (e.getAction().toString().contains("LEFT")) {
                sel.pos1 = p.getLocation();
                p.sendMessage("§aPos1 set");
            } else {
                sel.pos2 = p.getLocation();
                p.sendMessage("§aPos2 set");
            }
        }

        if (e.getItem().getType() == Material.GOLDEN_SHOVEL) {

            if (e.getAction().toString().contains("LEFT")) {
                sel.playerSpawn = p.getLocation();
                p.sendMessage("§aPlayer spawn set");
            } else {
                sel.mobSpawns.add(p.getLocation());
                p.sendMessage("§aMob spawn přidán");
            }
        }
    }
}
