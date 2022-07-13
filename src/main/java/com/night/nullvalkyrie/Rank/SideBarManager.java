package com.night.nullvalkyrie.Rank;

import com.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.UUID;

public class SideBarManager {
    private final HashMap<UUID, Integer> deaths = new HashMap<>();
    @SuppressWarnings("deprecation")
    public void setSideBar(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective obj = board.registerNewObjective("Pluto", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Pluto");
        Score name = obj.getScore(ChatColor.BLUE + "Player Name: ");
        name.setScore(8);
        Score name2 = obj.getScore(ChatColor.WHITE + player.getName());
        name2.setScore(7);
        Score space1 = obj.getScore("  ");
        space1.setScore(6);
        Score players = obj.getScore(ChatColor.LIGHT_PURPLE + "Players Online:");
        players.setScore(5);
        Score playercount = obj.getScore(ChatColor.YELLOW.toString() + Bukkit.getServer().getOnlinePlayers().size());
        playercount.setScore(4);
        Score space2 = obj.getScore(" ");
        space2.setScore(2);
        Score website = obj.getScore(ChatColor.YELLOW + "cath.js.org");
        website.setScore(1);

        Team playersOnline = board.registerNewTeam("deathsTotal");
        playersOnline.addEntry(ChatColor.BOLD.toString());
        playersOnline.setPrefix(ChatColor.BLUE + "Deaths: ");
        playersOnline.setSuffix(ChatColor.YELLOW + "0");
        obj.getScore(ChatColor.BOLD.toString()).setScore(3);
        player.setScoreboard(board);
        deaths.put(player.getUniqueId(), 0);
    }

    public void changeOnDeath(Player player) {
        int amount = deaths.get(player.getUniqueId());
        amount++;
        deaths.put(player.getUniqueId(), amount++);
        player.getScoreboard().getTeam("deathsTotal").setSuffix(ChatColor.YELLOW.toString() + amount);
    }
}
