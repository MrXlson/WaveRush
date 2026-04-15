package me.plugin.waverush.task;

import me.plugin.waverush.manager.ArenaManager;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

public class SignUpdateTask extends BukkitRunnable {

    private final ArenaManager arenaManager;

    public SignUpdateTask(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public void run() {

        for (Location loc : arenaManager.getSigns()) {

            if (!(loc.getBlock().getState() instanceof Sign)) continue;

            Sign sign = (Sign) loc.getBlock().getState();

            String arenaName = sign.getLine(1);
            int players = arenaManager.getPlayerCount(arenaName);

            String status = "§aWAITING";
            if (players >= 4) status = "§cFULL";
            else if (players > 0) status = "§eINGAME";

            sign.setLine(2, status);
            sign.setLine(3, "§b" + players + " hráčů");

            sign.update();
        }
    }
}
