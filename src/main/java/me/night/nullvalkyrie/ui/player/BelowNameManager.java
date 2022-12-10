package me.night.nullvalkyrie.ui.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class BelowNameManager {
    public void setBelowName(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective obj = board.registerNewObjective("HealthBar", Criteria.create("CustomHealth"), ChatColor.RED.toString() + player.getHealth()  + "❤");
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        player.setScoreboard(board);
    }
}
