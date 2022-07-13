package com.night.nullvalkyrie;

import com.night.nullvalkyrie.Chests.MenuListener;
import com.night.nullvalkyrie.Enchantments.EnchantmentManager;
import com.night.nullvalkyrie.Events.CustomItemEvents;
import com.night.nullvalkyrie.Items.CustomItemManager;
import com.night.nullvalkyrie.Rank.*;
import com.night.nullvalkyrie.Util.Util;
import com.night.nullvalkyrie.commands.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin implements Listener {
    private BossBar bossbar;
    private RankManager rankManager;
    private NameTagManager nameTagManager;
    private SideBarManager sideBarManager;
    private BelowNameManager belowNameManager;
    private CustomItemManager customItemManager;

    public RankManager getRankManager() {
        return rankManager;
    }

    public NameTagManager getNameTagManager() {
        return nameTagManager;
    }

    public SideBarManager getSideBarManager() {
        return sideBarManager;
    }

    public BelowNameManager getBelowNameManager() {
        return belowNameManager;
    }
    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }

    @Override
    public void onEnable() {
        new VanishCommand();
        new TestCommand();
        new AnvilCommand();
        new ArmorCommand();
        new MenuCommand();
        new MessageCommand();
        new HologramCommand();
        new CraftCommand();
        new EnchantingCommand();
        new RankCommand(this);
        new WeaponCommand(this);
        bossbar = Bukkit.createBossBar(ChatColor.GOLD + "Kuudra", BarColor.RED, BarStyle.SEGMENTED_12);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemEvents(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnCommand(this), this);
        nameTagManager = new NameTagManager(this);
        rankManager = new RankManager(this);
        sideBarManager = new SideBarManager();
        belowNameManager = new BelowNameManager();
        new EnchantmentManager();
        customItemManager = new CustomItemManager(this);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§1NOT ENOUGH MANNER"));
        bossbar.addPlayer(e.getPlayer());
        }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        e.setMaxPlayers(8964);
        String s = Util.centerText("Matrix", 45);
        String s2 = Util.centerText("Support 1.18 & 1.8.9", 45);
        e.setMotd(ChatColor.AQUA.toString() + ChatColor.BOLD + s + "\n" + ChatColor.GOLD + ChatColor.BOLD + s2);
        try {
            e.setServerIcon(Bukkit.loadServerIcon(new File("nuke.png")));
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }


//    For hologram clicks to change page
//    @EventHandler
//    public void onEntityInteract(EntityInteractEvent e) {
//        System.out.println(e.getEntity().getLocation());
//        e.getEntity().setCustomName(ChatColor.RED + "Changed name since you ust clicked lol");
//    }

}
