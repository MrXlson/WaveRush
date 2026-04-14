package me.plugin.waverush.command;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import me.plugin.waverush.model.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

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

        if (args.length == 1 && args[0].equalsIgnoreCase("select")) {
            player.getInventory().addItem(new org.bukkit.inventory.ItemStack(org.bukkit.Material.GOLDEN_HOE));
            player.sendMessage(ChatColor.GREEN + "Dostal jsi selekční nástroj!");
            return true;
        }

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

        player.sendMessage(ChatColor.RED + "/ma select | /ma create <name>");
        return true;
    }
            }
