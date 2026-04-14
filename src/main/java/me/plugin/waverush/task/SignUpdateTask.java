package me.plugin.waverush.task;

import me.plugin.waverush.WaveRushPlugin;
import me.plugin.waverush.manager.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

public class SignUpdateTask extends BukkitRunnable {

    private final ArenaManager arenaManager;

    public SignUpdateTask() {
        this.arenaManager = WaveRushPlugin.getPlugin(WaveRushPlugin.class).getArenaManager();
    }

    @Override
    public void run() {

        Bukkit.getWorlds().forEach(world -> {

            for (var chunk : world.getLoadedChunks()) {
                for (var blockState : chunk.getTileEntities()) {

                    if (!(blockState instanceof Sign sign)) continue;

                    if (!sign.getLine(0).contains("[WaveRush]")) continue;

                    String arena = org.bukkit.ChatColor.stripColor(sign.getLine(1));

                    int players = arenaManager.getPlayerCount(arena);

                    String status = "§aWAITING";

                    if (players > 0) status = "§cINGAME";
                    if (players >= 4) status = "§8FULL";

                    sign.setLine(2, status);
                    sign.setLine(3, "§7" + players + " hráčů");

                    sign.update();
                }
            }
        });
    }
}
