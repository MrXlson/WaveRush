package me.plugin.waverush.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {

    public void setScoreboard(Player player, int wave) {

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("wr", "dummy", "§6WaveRush");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore("§eVlna: §f" + wave).setScore(1);

        player.setScoreboard(board);
    }
}
