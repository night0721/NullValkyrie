package me.night.nullvalkyrie.ui;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.database.ranks.RankManager;
import me.night.nullvalkyrie.npc.NPCManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.night.nullvalkyrie.database.DatabaseManager.createUserSchema;

public class ScoreboardListener implements Listener {

    public static NameTagManager nameTagManager;
    private final SideBarManager sideBarManager;
    private final BelowNameManager belowNameManager;
    public ScoreboardListener(Main main) {
        nameTagManager = new NameTagManager();
        sideBarManager = new SideBarManager(main);
        belowNameManager = new BelowNameManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!player.hasPlayedBefore()) {
            e.getPlayer().sendTitle(ChatColor.RED + "Welcome to Matrix!", ChatColor.GREEN + "LOL", 20, 100, 20);
            RankManager.setRank(player.getUniqueId(), Rank.ROOKIE);
            createUserSchema(e.getPlayer().getDisplayName());
        }
        e.getPlayer().setPlayerListHeaderFooter(ChatColor.AQUA + "You are playing on " + ChatColor.GREEN + "127.0.0.1", ChatColor.GOLD + "Ranks, boosters, & more!" + ChatColor.AQUA + "127.0.0.1");
        nameTagManager.setNametags(player);
        nameTagManager.newTag(player);
        sideBarManager.setSideBar(player);
        sideBarManager.start(player);
        belowNameManager.setBelowName(player);
        e.setJoinMessage(RankManager.getRank(e.getPlayer().getUniqueId()).getDisplay() + " " + e.getPlayer().getName() + ChatColor.WHITE + " joined the server!");
        if(NPCManager.getNPCs() == null) return;
        if(NPCManager.getNPCs().isEmpty()) return;
        NPCManager.addJoinPacket(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.RED + "bozo " + e.getPlayer().getName() + " has left.");
        nameTagManager.removeTag(e.getPlayer());
        e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        AnimatedSideBar board = new AnimatedSideBar(e.getPlayer().getUniqueId());
        if(board.hasID()) {
            board.stop();
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        Bukkit.broadcastMessage(me.night.nullvalkyrie.database.ranks.RankManager.getRank(player.getUniqueId()).getDisplay() + " " + player.getName() + ChatColor.WHITE + ": " + e.getMessage());
    }

    //Death changing in sidebar
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(e.getEntity().getPlayer() == null) sideBarManager.changeOnDeath(e.getEntity().getPlayer());
    }
}
