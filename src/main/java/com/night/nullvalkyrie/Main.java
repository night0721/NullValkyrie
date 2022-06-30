package com.night.nullvalkyrie;

import com.night.nullvalkyrie.commands.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    private BossBar bossbar;
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("PREPARING TO DESTROY HYPIXEL");

        getCommand("test").setExecutor(new TestCommand());

        bossbar = Bukkit.createBossBar(
                ChatColor.GOLD + "Kuudra",
                BarColor.RED,
                BarStyle.SEGMENTED_12
        );
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // e.setCancelled(true);
        // e.getPlayer().sendMessage(ChatColor.RED + "STOP UR MOM");
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.RED+"[ADMIN] " + e.getPlayer().getName() + ChatColor.WHITE + " joined the server!");
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.ORANGE);
        helmet.setItemMeta(helmetMeta);
        e.getPlayer().getInventory().addItem(helmet);
        ItemStack cp = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta cpdata =  (LeatherArmorMeta) cp.getItemMeta();
        cpdata.setColor(org.bukkit.Color.fromRGB(2,2,58));
        cp.setItemMeta(cpdata);
        e.getPlayer().getInventory().addItem(cp);
        e.getPlayer().sendTitle(ChatColor.RED +"Welcome to Operation Valkyrie!", ChatColor.GREEN + "LOL", 20, 100, 20);
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§1NOT ENOUGH MANNER"));

        bossbar.addPlayer(e.getPlayer());
        e.getPlayer().setPlayerListHeaderFooter(ChatColor.AQUA + "You are playing on " + ChatColor.GREEN + "127.0.0.1", ChatColor.GOLD + "Ranks, boosters, & more!" + ChatColor.AQUA + "127.0.0.1");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
