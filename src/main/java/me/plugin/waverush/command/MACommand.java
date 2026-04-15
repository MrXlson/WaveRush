package me.plugin.waverush.command;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import me.plugin.waverush.model.Arena;
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
            player.sendMessage("§cPoužití: /ma <join/create/setspawn/setlobby/list/reload>");
            return true;
        }

        // ========================
        // CREATE
        // ========================
        if (args[0].equalsIgnoreCase("create")) {

            if (args.length < 2) {
                player.sendMessage("§cPoužití: /ma create <název>");
                return true;
            }

            String name = args[1];

            if (arenaManager.getArena(name) != null) {
                player.sendMessage("§cTato aréna už existuje!");
                return true;
            }

            Arena arena = new Arena(name);
            arenaManager.createArena(name, arena);

            player.sendMessage("§aAréna vytvořena: " + name);
            return true;
        }

        // ========================
        // SETSPAWN
        // ========================
        if (args[0].equalsIgnoreCase("setspawn")) {

            if (args.length < 2) {
                player.sendMessage("§cPoužití: /ma setspawn <arena>");
                return true;
            }

            String name = args[1];
            Arena arena = arenaManager.getArena(name);

            if (arena == null) {
                player.sendMessage("§cAréna neexistuje!");
                return true;
            }

            arena.setSpawn(player.getLocation());

            plugin.getConfig().set("arenas." + name + ".spawn", player.getLocation());
            plugin.saveConfig();

            player.sendMessage("§aSpawn nastaven pro arénu: " + name);
            return true;
        }

        // ========================
        // SETLOBBY
        // ========================
        if (args[0].equalsIgnoreCase("setlobby")) {

            plugin.getConfig().set("lobby", player.getLocation());
            plugin.saveConfig();

            player.sendMessage("§aLobby nastaveno!");
            return true;
        }

        // ========================
        // JOIN
        // ========================
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
            return true;
        }

        // ========================
        // LEAVE
        // ========================
        if (args[0].equalsIgnoreCase("leave")) {
            arenaManager.sendToLobby(player);
            player.sendMessage("§cOpustil jsi arénu.");
            return true;
        }

        // ========================
        // LIST
        // ========================
        if (args[0].equalsIgnoreCase("list")) {
            for (Arena arena : arenaManager.getArenas()) {
                player.sendMessage("§e" + arena.getName());
            }
            return true;
        }

        // ========================
        // RELOAD
        // ========================
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPluginConfig();
            player.sendMessage("§aReload hotový!");
            return true;
        }

        return true;
    }
}
