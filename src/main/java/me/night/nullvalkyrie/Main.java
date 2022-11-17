package me.night.nullvalkyrie;

import me.night.nullvalkyrie.chests.MenuListener;
import me.night.nullvalkyrie.discord.DiscordClientManager;
import me.night.nullvalkyrie.enchantments.EnchantmentManager;
import me.night.nullvalkyrie.events.CustomItemEvents;
//import me.night.nullvalkyrie.hardpoint.GameEvent;
import me.night.nullvalkyrie.events.DamageEffect;
import me.night.nullvalkyrie.items.CustomItemManager;
import me.night.nullvalkyrie.rank.ScoreboardListener;
import me.night.nullvalkyrie.util.Util;
import me.night.nullvalkyrie.commands.*;
import me.night.nullvalkyrie.database.Client;
import me.night.nullvalkyrie.miners.CryptoMiner;
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
import java.util.Date;

public final class Main extends JavaPlugin implements Listener {
    private BossBar bossbar;
    private CustomItemManager customItemManager;
    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        new CommandManager(this).register();
        bossbar = Bukkit.createBossBar(ChatColor.GOLD + "Kuudra", BarColor.RED, BarStyle.SEGMENTED_12);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageEffect(this), this);
        //Bukkit.getPluginManager().registerEvents(new GameEvent(this), this);
        EnchantmentManager.register();
        new DiscordClientManager();
        customItemManager = new CustomItemManager(this);
        new CryptoMiner(this, "Gay", Material.ENDER_CHEST, 10, 0.7, new Date().getTime());
        new Client();
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
}
