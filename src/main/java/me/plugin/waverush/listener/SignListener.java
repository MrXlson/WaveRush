package me.plugin.waverush.listener;

import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.model.Arena;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class SignListener implements Listener {

    private final ArenaManager arenaManager;

    public SignListener(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    // ========================
    // VYTVOŘENÍ CEDULKY
    // ========================
    @EventHandler
    public void onSignCreate(SignChangeEvent e) {

        if (!e.getLine(0).equalsIgnoreCase("[WaveRush]")) return;

        String arenaName = e.getLine(1);

        Arena arena = arenaManager.getArena(arenaName);
        if (arena == null) {
            e.getPlayer().sendMessage("§cAréna neexistuje!");
            return;
        }

        e.setLine(0, "§6[WaveRush]");
        e.setLine(2, "§aWAITING");
        e.setLine(3, "0 hráčů");

        arenaManager.addSign(e.getBlock().getLocation());
    }

    // ========================
    // KLIK NA CEDULKU
    // ========================
    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!(e.getClickedBlock().getState() instanceof Sign sign)) return;

        if (!sign.getLine(0).contains("WaveRush")) return;

        Player player = e.getPlayer();
        String arenaName = sign.getLine(1);

        Arena arena = arenaManager.getArena(arenaName);
        if (arena == null) {
            player.sendMessage("§cAréna neexistuje!");
            return;
        }

        // ❌ FIX: zabránění spam joinu
        if (arena.getPlayers().contains(player)) {
            player.sendMessage("§cUž jsi v aréně!");
            return;
        }

        // ❌ FIX: kontrola jestli už někde je
        if (arenaManager.isInArena(player)) {
            player.sendMessage("§cUž jsi v jiné aréně!");
            return;
        }

        boolean success = arenaManager.joinArena(player, arenaName);

        if (!success) {
            player.sendMessage("§cNelze se připojit!");
            return;
        }

        player.sendMessage("§aPřipojen přes cedulku!");
    }
}
