package me.night.nullvalkyrie.events.listeners;

import me.night.nullvalkyrie.events.custom.InteractHologramEvent;
import me.night.nullvalkyrie.packets.handle.PacketInjector;
import me.night.nullvalkyrie.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.io.File;

public class ServerEvents implements Listener {
    public final BossBar bossbar;
    public final PacketInjector injector;

    public ServerEvents() {
        bossbar = Bukkit.createBossBar(ChatColor.GOLD + "Kuudra", BarColor.RED, BarStyle.SEGMENTED_12);
        this.injector = new PacketInjector();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        bossbar.addPlayer(e.getPlayer());
        injector.addPlayer(e.getPlayer());
        e.getPlayer().setResourcePack("https://www.dropbox.com/s/7y7p93xzhar6vvw/%C2%A7b%C2%A7lNKRP%201.19.3.zip?dl=1");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        injector.removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        e.setMaxPlayers(8964);
        String s = Util.centerText("Vanadium", 45);
        String s2 = Util.centerText("Support 1.19.3", 45);
        e.setMotd(ChatColor.AQUA.toString() + ChatColor.BOLD + s + "\n" + ChatColor.GOLD + ChatColor.BOLD + s2);
        try {
            e.setServerIcon(Bukkit.loadServerIcon(new File("nuke.png")));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    @EventHandler
    public void onClickHologram(InteractHologramEvent e) {
        if (e.getHologram().getCustomName() == null) return;
        if (e.getHologram().getCustomName().equals(ChatColor.GOLD + "Click me to change!!!")) {
            // TODO: change hologram things
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onResourcePackChange(PlayerResourcePackStatusEvent e) {
        if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED || e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            e.getPlayer().kickPlayer("You must download the resource pack to play on this server!");
        }
    }
}
