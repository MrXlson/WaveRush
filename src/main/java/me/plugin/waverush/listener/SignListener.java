package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    private final ArenaManager arenaManager;

    public SignListener(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (!(e.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) e.getClickedBlock().getState();

        if (!sign.getLine(0).equalsIgnoreCase("[WaveRush]")) return;

        String arenaName = sign.getLine(1);

        Player player = e.getPlayer(); // 🔥 FIX

        arenaManager.joinArena(player, arenaName);
    }
}
