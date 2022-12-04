package me.night.nullvalkyrie.ui;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.database.RankDataManager;
import me.night.nullvalkyrie.enums.Rank;
import me.night.nullvalkyrie.entities.npcs.NPCManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.night.nullvalkyrie.database.UserDataManager.createUserBank;

public class ScoreboardListener implements Listener {

    public static NameTagManager nameTagManager;
    public static SideBarManager sideBarManager;
    private final BelowNameManager belowNameManager;

    public ScoreboardListener(Main main) {
        nameTagManager = new NameTagManager();
        sideBarManager = new SideBarManager(main);
        belowNameManager = new BelowNameManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPlayedBefore()) {
            e.getPlayer().sendTitle(ChatColor.RED + "Welcome to Vanadium!", ChatColor.GREEN + "LOL", 20, 100, 20);
            RankDataManager.setRank(player.getUniqueId(), Rank.ROOKIE);
            createUserBank(e.getPlayer().getUniqueId().toString());
        }
        e.getPlayer().setPlayerListHeaderFooter(ChatColor.AQUA + "You are playing on " + ChatColor.GREEN + "127.0.0.1", ChatColor.GOLD + "Ranks, boosters, & more!" + ChatColor.AQUA + "127.0.0.1");
        nameTagManager.setNametags(player);
        nameTagManager.newTag(player);
        sideBarManager.setSideBar(player);
        sideBarManager.start(player);
        belowNameManager.setBelowName(player);
        e.setJoinMessage(RankDataManager.getRank(e.getPlayer().getUniqueId()).getDisplay() + " " + e.getPlayer().getName() + ChatColor.WHITE + " joined the server!");
        if (NPCManager.getNPCs() == null) return;
        if (NPCManager.getNPCs().isEmpty()) return;
        NPCManager.addJoinPacket(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        nameTagManager.removeTag(e.getPlayer());
        e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        AnimatedSideBar board = sideBarManager.board;
        if (board.hasID()) board.stop();

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        Bukkit.broadcastMessage(RankDataManager.getRank(player.getUniqueId()).getDisplay() + " " + player.getName() + ChatColor.WHITE + ": " + e.getMessage());
    }
}
