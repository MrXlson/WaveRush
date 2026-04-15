package me.plugin.waverush.command;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MACommand implements CommandExecutor {

    private final ArenaManager arenaManager;
    private final SelectionManager selectionManager;
    private final WaveRushPlugin plugin;

    public MACommand(ArenaManager arenaManager, SelectionManager selectionManager, WaveRushPlugin plugin) {
        this.arenaManager = arenaManager;
        this.selectionManager = selectionManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            player.sendMessage("§cPoužij /ma join <arena>");
            return true;
        }

        if (args[0].equalsIgnoreCase("join")) {

            if (args.length < 2) {
                player.sendMessage("§cZadej arénu!");
                return true;
            }

            boolean success = arenaManager.joinArena(player, args[1]);

            if (!success) {
                player.sendMessage("§cAréna neexistuje!");
            } else {
                player.sendMessage("§aPřipojen!");
            }
        }

        if (args[0].equalsIgnoreCase("list")) {
            for (var arena : arenaManager.getArenas()) {
                player.sendMessage("§e" + arena.getName());
            }
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPluginConfig();
            player.sendMessage("§aReload hotový!");
        }

        return true;
    }
}
