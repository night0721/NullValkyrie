package me.night.nullvalkyrie.Rank;

import me.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    public static RankManager rankManager;
    public static NameTagManager nameTagManager;
    private SideBarManager sideBarManager;
    private BelowNameManager belowNameManager;
    public ScoreboardListener(Main main) {
        nameTagManager = new NameTagManager(main);
        rankManager = new RankManager(main);
        sideBarManager = new SideBarManager();
        belowNameManager = new BelowNameManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!player.hasPlayedBefore()) {
            e.getPlayer().sendTitle(ChatColor.RED + "Welcome to Matrix!", ChatColor.GREEN + "LOL", 20, 100, 20);
            rankManager.setRank(player.getUniqueId(), Rank.ROOKIE);
        }
        e.getPlayer().setPlayerListHeaderFooter(ChatColor.AQUA + "You are playing on " + ChatColor.GREEN + "127.0.0.1", ChatColor.GOLD + "Ranks, boosters, & more!" + ChatColor.AQUA + "127.0.0.1");
        nameTagManager.setNametags(player);
        nameTagManager.newTag(player);
        sideBarManager.setSideBar(player);
        belowNameManager.setBelowName(player);
        e.setJoinMessage(rankManager.getRank(e.getPlayer().getUniqueId()).getDisplay() + " " + e.getPlayer().getName() + ChatColor.WHITE + " joined the server!");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.RED + "bozo " + e.getPlayer().getName() + " has left.");
        nameTagManager.removeTag(e.getPlayer());
        e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        Bukkit.broadcastMessage(rankManager.getRank(player.getUniqueId()).getDisplay() + " " + player.getName() + ChatColor.WHITE + ": " + e.getMessage());
    }

    //Death changing in sidebar
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        sideBarManager.changeOnDeath(e.getEntity().getPlayer());
    }
}
