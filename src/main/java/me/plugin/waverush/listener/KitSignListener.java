package me.plugin.waverush.listener;

import me.plugin.waverush.manager.KitManager;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class KitSignListener implements Listener {

    private final KitManager kitManager;

    public KitSignListener(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    // ========================
    // CREATE SIGN
    // ========================
    @EventHandler
    public void onCreate(SignChangeEvent e) {

        if (!e.getLine(0).equalsIgnoreCase("[Kit]")) return;

        String kit = e.getLine(1);

        e.setLine(0, "§6[Kit]");
        e.setLine(1, "§e" + kit);
        e.setLine(2, "Klikni pro výběr");
    }

    // ========================
    // CLICK SIGN
    // ========================
    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!(e.getClickedBlock().getState() instanceof Sign sign)) return;

        if (!sign.getLine(0).contains("[Kit]")) return;

        Player player = e.getPlayer();
        String kit = sign.getLine(1).replace("§e", "");

        kitManager.setKit(player, kit);
    }
}
