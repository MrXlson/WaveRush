package me.plugin.waverush.command;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import me.plugin.waverush.model.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MACommand implements CommandExecutor {

    private final ArenaManager arenaManager;
    private final SelectionManager selectionManager;

    public MACommand() {
        this.arenaManager = WaveRushPlugin.getPlugin(WaveRushPlugin.class).getArenaManager();
        this.selectionManager = WaveRushPlugin.getPlugin(WaveRushPlugin.class).getSelectionManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        // /ma select
        if (args.length == 1 && args[0].equalsIgnoreCase("select")) {
            player.getInventory().addItem(new ItemStack(Material.GOLDEN_HOE));
            player.sendMessage(ChatColor.GREEN + "Dostal jsi selekční nástroj!");
            return true;
        }

        // /ma create <name>
        if (args.length == 2 && args[0].equalsIgnoreCase("create")) {

            if (selectionManager.getPos1(player) == null || selectionManager.getPos2(player) == null) {
                player.sendMessage(ChatColor.RED + "Musíš nastavit obě pozice!");
                return true;
            }

            Arena arena = new Arena(
                    args[1],
                    selectionManager.getPos1(player),
                    selectionManager.getPos2(player)
            );

            arenaManager.createArena(args[1], arena);

            player.sendMessage(ChatColor.GREEN + "Arena vytvořena: " + args[1]);
            return true;
        }

        // 🔥 /ma join <name>
        if (args.length == 2 && args[0].equalsIgnoreCase("join")) {

            Arena arena = arenaManager.getArena(args[1]);

            if (arena == null) {
                player.sendMessage(ChatColor.RED + "Arena neexistuje!");
                return true;
            }

            arenaManager.joinArena(player, args[1]);

            player.sendMessage(ChatColor.GREEN + "Připojen do arény: " + args[1]);
            return true;
        }

        player.sendMessage(ChatColor.RED + "/ma select | /ma create <name> | /ma join <name>");
        return true;
    }
}
