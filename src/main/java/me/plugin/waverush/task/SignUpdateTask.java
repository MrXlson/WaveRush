package me.plugin.waverush.task;

import me.plugin.waverush.manager.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

public class SignUpdateTask extends BukkitRunnable {

    private final ArenaManager arenaManager;

    public SignUpdateTask(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public void run() {

        Bukkit.getWorlds().forEach(world -> {
            world.getLoadedChunks().forEach(chunk -> {
                for (Block block : chunk.getBlocks()) {

                    if (!(block.getState() instanceof Sign)) continue;

                    Sign sign = (Sign) block.getState();

                    if (!sign.getLine(0).equalsIgnoreCase("[WaveRush]")) continue;

                    String arenaName = sign.getLine(1);

                    int players = arenaManager.getPlayerCount(arenaName);

                    // 🔥 STATUS
                    String status = "§aWAITING";
                    if (players >= 4) status = "§cFULL";
                    if (players > 0 && players < 4) status = "§eINGAME";

                    sign.setLine(2, status);
                    sign.setLine(3, "§b" + players + " hráčů");

                    sign.update();
                }
            });
        });
    }
}
