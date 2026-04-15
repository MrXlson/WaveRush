package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    private final ArenaManager arenaManager;

    public SignListener(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onSignCreate(SignChangeEvent e) {

        if (!e.getLine(0).equalsIgnoreCase("[WaveRush]")) return;

        String arena = e.getLine(1);

        arenaManager.addSign(e.getBlock().getLocation());

        e.setLine(0, "§b[WaveRush]");
        e.setLine(2, "§aWAITING");
        e.setLine(3, "§b0 hráčů");

        e.getPlayer().sendMessage("§aCedulka vytvořena!");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if (e.getClickedBlock() == null) return;
        if (!(e.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) e.getClickedBlock().getState();

        if (!sign.getLine(0).contains("WaveRush")) return;

        String arenaName = sign.getLine(1);

        if (!arenaManager.joinFirstAvailable(e.getPlayer())) {
            e.getPlayer().sendMessage("§cŽádná dostupná aréna!");
        }
    }
}
