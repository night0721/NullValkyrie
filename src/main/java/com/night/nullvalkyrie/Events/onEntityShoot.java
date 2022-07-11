package com.night.nullvalkyrie.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class onEntityShoot implements Listener {
    @EventHandler
    public void onEntityShoot(EntityShootBowEvent e) {
        if(e.getProjectile() instanceof Arrow) {
            if(e.getEntity() instanceof Player) {
                if(e.getBow() != null && e.getBow().getItemMeta() != null && e.getBow().getItemMeta().getLore() != null && e.getBow().getItemMeta().getLore().contains(ChatColor.GOLD + "Item Ability: Triple Shot")) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.hasItem()) {
                if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
                    String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                    if (name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Terminator")) {
                        Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                        arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                        arrow.setVelocity(arrow.getVelocity().multiply(5));
                        Arrow a1 = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                        a1.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(5)).multiply(5));
                        a1.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                        Arrow a2 = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                        a2.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(-5)).multiply(5));
                        a2.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                        e.setCancelled(true);
                    }
                }
            }
        }

    }
}
