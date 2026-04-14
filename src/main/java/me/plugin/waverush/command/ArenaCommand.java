package me.plugin.waverush.command;

import me.plugin.waverush.game.GameTask;
import me.plugin.waverush.manager.ArenaManager;
import me.plugin.waverush.manager.SelectionManager;
import me.plugin.waverush.model.Arena;
import me.plugin.waverush.model.Selection;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArenaCommand implements CommandExecutor {

    private final ArenaManager arenaManager;
    private final SelectionManager selectionManager;

    public ArenaCommand(ArenaManager arenaManager, SelectionManager selectionManager) {
        this.arenaManager = arenaManager;
        this.selectionManager = selectionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length == 0) {
            p.sendMessage("/ma select | create | join");
            return true;
        }

        if (args[0].equalsIgnoreCase("select")) {
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_HOE));
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_SHOVEL));
            p.sendMessage("§aDostal jsi nástroje.");
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 2) return true;

            Selection sel = selectionManager.get(p.getUniqueId());

            Arena arena = new Arena(args[1]);
            arena.pos1 = sel.pos1;
            arena.pos2 = sel.pos2;
            arena.playerSpawn = sel.playerSpawn;
            arena.mobSpawns.addAll(sel.mobSpawns);

            arenaManager.addArena(arena);

            p.sendMessage("§aArena vytvořena!");
        }

        if (args[0].equalsIgnoreCase("join")) {
            if (args.length < 2) return true;

            Arena arena = arenaManager.getArena(args[1]);
            if (arena == null) {
                p.sendMessage("§cArena neexistuje.");
                return true;
            }

            arena.players.add(p);
            p.teleport(arena.playerSpawn);

            new GameTask(arena).runTaskTimer(
                    me.plugin.waverush.WaveRushPlugin.get(),
                    0, 100
            );

            p.sendMessage("§aJoined arena!");
        }

        return true;
    }
}
