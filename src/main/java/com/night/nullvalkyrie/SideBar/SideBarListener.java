package com.night.nullvalkyrie.SideBar;

import com.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public class SideBarListener implements Listener {
    private Main main;
    public SideBarListener(Main main) {
        this.main = main;
    }
    private HashMap<UUID, Integer> deaths = new HashMap<>();
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("Pluto", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Pluto");
        Score website = obj.getScore(ChatColor.YELLOW + "cath.js.org");
        website.setScore(1);
        Score space1 = obj.getScore(" ");
        space1.setScore(2);
        Score name = obj.getScore(ChatColor.BLUE + "Player Name: " + player.getName());
        name.setScore(4);
        Team playersOnline = board.registerNewTeam("deathsTotal");
        playersOnline.addEntry(ChatColor.BOLD.toString());
        playersOnline.setPrefix(ChatColor.BLUE + "Deaths: ");
        playersOnline.setSuffix(ChatColor.YELLOW + "0");
        obj.getScore(ChatColor.BOLD.toString()).setScore(3);
        player.setScoreboard(board);
        deaths.put(player.getUniqueId(), 0);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity().getPlayer();
        int amount = deaths.get(player.getUniqueId());
        amount++;
        deaths.put(player.getUniqueId(), amount);
        player.getScoreboard().getTeam("deathsTotal").setSuffix(ChatColor.YELLOW.toString() + amount);
    }



}
