package me.plugin.waverush.manager;

import me.plugin.waverush.game.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {

    public static void update(Player player, int wave) {

        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective obj = board.registerNewObjective("wave", "dummy", "§6WaveRush");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore("§7Wave: §e" + wave).setScore(2);
        obj.getScore("§7Kills: §a" + GameTask.getKills(player)).setScore(1);

        player.setScoreboard(board);
    }
}
