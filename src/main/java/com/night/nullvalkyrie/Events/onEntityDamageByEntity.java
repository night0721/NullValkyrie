package com.night.nullvalkyrie.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.night.nullvalkyrie.Items.CustomItemManager.loadConfig;

public class onEntityDamageByEntity implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.SNOWBALL) {
            Snowball sb = (Snowball) e.getDamager();
            Player pl = (Player) sb.getShooter();
            if(pl.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = pl.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if (name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun")) {
                    e.setDamage(10000);
                } else if (name.equalsIgnoreCase("AA-12")) {
                    e.setDamage(7);
                } else {
                    e.setDamage(0);
                }
            }
        } else if(e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if(player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getLore() != null && player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.GOLD + "Item Ability: Damage Multiplier")) {
                if(e.getEntity() instanceof Zombie) {
                    int zombie = loadConfig("ItemData\\WidowSword.yml").getInt("zombie");
                    e.setDamage(e.getDamage() * (1 + zombie / 100));
                } else if(e.getEntity() instanceof Skeleton) {
                    int skeleton = loadConfig("ItemData\\WidowSword.yml").getInt("skeleton");
                    e.setDamage(e.getDamage() * (1 + skeleton / 100));
                } else if(e.getEntity() instanceof Spider) {
                    int spider = loadConfig("ItemData\\WidowSword.yml").getInt("spider");
                    e.setDamage(e.getDamage() * (1 + spider / 100));
                }
                player.sendMessage(String.valueOf(e.getDamage()));
            }
        }
    }
}
