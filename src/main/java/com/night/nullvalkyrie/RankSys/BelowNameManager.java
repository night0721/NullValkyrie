package com.night.nullvalkyrie.RankSys;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class BelowNameManager {
    public void setBelowName(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective obj = board.registerNewObjective("HealthBar", "health");
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        obj.setDisplayName("/ 20");
        player.setScoreboard(board);
        player.setHealth(player.getHealth());

    }
}
