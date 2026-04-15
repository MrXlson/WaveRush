package me.plugin.waverush.command;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import me.plugin.waverush.model.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MACommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Pouze hráč!");
            return true;
        }

        Player player = (Player) sender;

        WaveRushPlugin plugin = (WaveRushPlugin) Bukkit.getPluginManager().getPlugin("WaveRush");
        ArenaManager arenaManager = plugin.getArenaManager();
        SelectionManager selectionManager = plugin.getSelectionManager();

        boolean isAdmin = player.hasPermission("waverush.admin");

        if (args.length == 0) {
            player.sendMessage("§ePoužití: /ma <menu|kit|join|create|select|list>");
            return true;
        }

        // 🔥 MENU
        if (args[0].equalsIgnoreCase("menu")) {
            if (!isAdmin && !player.hasPermission("waverush.menu")) {
                player.sendMessage("§cNemáš oprávnění!");
                return true;
            }

            player.performCommand("ma menu"); // případně GUI
            return true;
        }

        // 🔥 KIT
        if (args[0].equalsIgnoreCase("kit")) {
            if (!isAdmin && !player.hasPermission("waverush.kit")) {
                player.sendMessage("§cNemáš oprávnění!");
                return true;
            }

            player.performCommand("ma kit"); // případně GUI
            return true;
        }

        // 🔥 JOIN
        if (args[0].equalsIgnoreCase("join")) {
            if (!isAdmin && !player.hasPermission("waverush.join")) {
                player.sendMessage("§cNemáš oprávnění!");
                return true;
            }

            if (args.length < 2) {
                player.sendMessage("§cPoužití: /ma join <arena>");
                return true;
            }

            arenaManager.joinArena(player, args[1]);
            return true;
        }

        // 🔥 CREATE
        if (args[0].equalsIgnoreCase("create")) {
            if (!isAdmin && !player.hasPermission("waverush.create")) {
                player.sendMessage("§cNemáš oprávnění!");
                return true;
            }

            if (args.length < 2) {
                player.sendMessage("§cPoužití: /ma create <název>");
                return true;
            }

            String name = args[1];

            Location pos1 = selectionManager.getPos1(player);
            Location pos2 = selectionManager.getPos2(player);

            if (pos1 == null || pos2 == null) {
                player.sendMessage("§cMusíš označit 2 pozice!");
                return true;
            }

            // 🔥 FIX PRO TVŮJ SYSTÉM
            Arena arena = new Arena(name, pos1, pos2);
            arenaManager.createArena(name, arena);

            player.sendMessage("§aAréna vytvořena: " + name);
            return true;
        }

        // 🔥 SELECT
        if (args[0].equalsIgnoreCase("select")) {
            if (!isAdmin && !player.hasPermission("waverush.select")) {
                player.sendMessage("§cNemáš oprávnění!");
                return true;
            }

            player.sendMessage("§aPoužij motyku pro označení pozic!");
            return true;
        }

        // 🔥 LIST
        if (args[0].equalsIgnoreCase("list")) {
            player.sendMessage("§eArény:");

            for (Arena arena : arenaManager.getArenas().values()) {
                player.sendMessage("§7- " + arena.getName());
            }

            return true;
        }

        return true;
    }
}
