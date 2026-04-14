package me.plugin.waverush.listener;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.manager.ArenaManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    private final ArenaManager arenaManager;

    public SignListener() {
        this.arenaManager = WaveRushPlugin.getPlugin(WaveRushPlugin.class).getArenaManager();
    }

    @EventHandler
    public void onSignCreate(SignChangeEvent e) {

        if (!e.getLine(0).equalsIgnoreCase("[WaveRush]")) return;

        String arenaName = e.getLine(1);

        if (arenaManager.getArena(arenaName) == null) {
            e.getPlayer().sendMessage(ChatColor.RED + "Arena neexistuje!");
            return;
        }

        e.setLine(0, "§6[WaveRush]");
        e.setLine(2, "§aWAITING");
        e.setLine(3, "§70 hráčů");

        e.getPlayer().sendMessage(ChatColor.GREEN + "Cedulka vytvořena!");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if (!(e.getClickedBlock() instanceof Sign)) return;

        Sign sign = (Sign) e.getClickedBlock().getState();

        if (!sign.getLine(0).contains("[WaveRush]")) return;

        String arenaName = ChatColor.stripColor(sign.getLine(1));

        Player player = e.getPlayer();

        arenaManager.joinArena(player, arenaName);
    }
}
