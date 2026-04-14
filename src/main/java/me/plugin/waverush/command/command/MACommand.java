package me.plugin.waverush.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MACommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("select")) {
            player.getInventory().addItem(new org.bukkit.inventory.ItemStack(Material.GOLDEN_HOE));
            player.sendMessage(ChatColor.GREEN + "Dostal jsi selekční nástroj!");
            return true;
        }

        player.sendMessage(ChatColor.RED + "/ma select");
        return true;
    }
}
