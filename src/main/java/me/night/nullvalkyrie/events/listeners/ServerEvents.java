package me.night.nullvalkyrie.events.listeners;

import me.night.nullvalkyrie.events.custom.InteractHologramEvent;
import me.night.nullvalkyrie.packets.PacketInjector;
import me.night.nullvalkyrie.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.File;

public class ServerEvents implements Listener {
    public BossBar bossbar;
    public PacketInjector injector;
    public ServerEvents() {
        bossbar = Bukkit.createBossBar(ChatColor.GOLD + "Kuudra", BarColor.RED, BarStyle.SEGMENTED_12);
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
        String s = Util.centerText("Vanadium", 45);
        String s2 = Util.centerText("Support 1.19.2", 45);
        e.setMotd(ChatColor.AQUA.toString() + ChatColor.BOLD + s + "\n" + ChatColor.GOLD + ChatColor.BOLD + s2);
        try {
            e.setServerIcon(Bukkit.loadServerIcon(new File("nuke.png")));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
    @EventHandler
    public void onClickHologram(InteractHologramEvent e) {
        if (e.getHologram().getCustomName().equals(ChatColor.GOLD + "Click me to change!!!")) {
            // TODO: change hologram things
        }
    }
}
