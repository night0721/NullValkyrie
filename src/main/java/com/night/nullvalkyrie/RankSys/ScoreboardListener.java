package com.night.nullvalkyrie.RankSys;

import com.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardListener implements Listener {

    private Main main;
    public ScoreboardListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!player.hasPlayedBefore()) {
            main.getRankManager().setRank(player.getUniqueId(), Rank.ROOKIE);
        }
        main.getNameTagManager().setNametags(player);
        main.getNameTagManager().newTag(player);
        main.getSideBarManager().setSideBar(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.RED + "bozo " + e.getPlayer().getName() + " has left.");
        main.getNameTagManager().removeTag(e.getPlayer());
        e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        Bukkit.broadcastMessage(main.getRankManager().getRank(player.getUniqueId()).getDisplay() + " " + player.getName() + ChatColor.WHITE + ": " + e.getMessage());
    }

    //Death changing in sidebar
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        main.getSideBarManager().changeOnDeath(e.getEntity().getPlayer());
    }
}
