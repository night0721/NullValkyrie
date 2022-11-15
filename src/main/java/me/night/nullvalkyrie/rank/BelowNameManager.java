package me.night.nullvalkyrie.rank;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class BelowNameManager {
    public void setBelowName(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective obj = board.registerNewObjective("HealthBar", "health");
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        obj.setDisplayName(ChatColor.RED + "❤");
        player.setScoreboard(board);
        player.setHealth(player.getHealth());
    }
}