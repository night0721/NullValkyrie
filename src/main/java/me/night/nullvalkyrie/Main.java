package me.night.nullvalkyrie;

import me.night.nullvalkyrie.chests.MenuListener;
import me.night.nullvalkyrie.discord.DiscordClientManager;
import me.night.nullvalkyrie.enchantments.EnchantmentManager;
import me.night.nullvalkyrie.events.CustomItemEvents;
import me.night.nullvalkyrie.events.DamageEffect;
import me.night.nullvalkyrie.items.CustomItemManager;
import me.night.nullvalkyrie.npc.ClickNPC;
import me.night.nullvalkyrie.npc.NPC;
import me.night.nullvalkyrie.npc.PacketInjector;
import me.night.nullvalkyrie.rank.ScoreboardListener;
import me.night.nullvalkyrie.util.FileManager;
import me.night.nullvalkyrie.util.Util;
import me.night.nullvalkyrie.commands.*;
import me.night.nullvalkyrie.database.DatabaseManager;
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
    private PacketInjector injector;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        EnchantmentManager.register();
        new CustomItemManager(this);
        new FileManager();
        new CommandManager(this).register();
        bossbar = Bukkit.createBossBar(ChatColor.GOLD + "Kuudra", BarColor.RED, BarStyle.SEGMENTED_12);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageEffect(this), this);
        Bukkit.getPluginManager().registerEvents(new ClickNPC(), this);
        //Bukkit.getPluginManager().registerEvents(new GameEvent(this), this);
        new DiscordClientManager();
        new DatabaseManager();
        NPC.loadNPC(CustomItemManager.loadConfig("npcs.yml"));
        this.injector = new PacketInjector();
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        bossbar.addPlayer(e.getPlayer());
        injector.addPlayer(e.getPlayer());
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
}
