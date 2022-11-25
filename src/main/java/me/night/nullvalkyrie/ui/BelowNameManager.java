package me.night.nullvalkyrie.ui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class BelowNameManager {
    public void setBelowName(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective obj = board.registerNewObjective("HealthBar", Criteria.HEALTH, ChatColor.RED + "❤");
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        player.setScoreboard(board);
        player.setHealth(player.getHealth());
    }
}
