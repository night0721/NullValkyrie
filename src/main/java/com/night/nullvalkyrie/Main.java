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
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("armor").setExecutor(new ArmorCommand());
        getCommand("gun").setExecutor(new GunCommand());
        bossbar = Bukkit.createBossBar(
                ChatColor.GOLD + "Kuudra",
                BarColor.RED,
                BarStyle.SEGMENTED_12
        );
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.hasItem()) {
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand()!= null && player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_HOE)) {
                    player.launchProjectile(Snowball.class, player.getLocation().getDirection());

                }
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.RED+"[ADMIN] " + e.getPlayer().getName() + ChatColor.WHITE + " joined the server!");

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
