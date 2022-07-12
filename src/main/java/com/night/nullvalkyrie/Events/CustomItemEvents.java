package com.night.nullvalkyrie.Events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

import static com.night.nullvalkyrie.Items.CustomItemManager.loadConfig;

public class CustomItemEvents implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType().equals(EntityType.SNOWBALL)) {
            Snowball sb = (Snowball) e.getDamager();
            Player pl = (Player) sb.getShooter();
            if (pl.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = pl.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if (name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun")) {
                    e.setDamage(10000);
                } else if (name.equalsIgnoreCase("AA-12")) {
                    e.setDamage(7);
                } else {
                    e.setDamage(0);
                }
            }
        } else if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if(name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Fabled Widow Sword")) {
                    if (e.getEntity() instanceof Zombie) {
                        int zombie = loadConfig("ItemData\\WidowSword.yml").getInt("zombie");
                        e.setDamage(e.getDamage() * (1 + zombie / 100));
                    } else if (e.getEntity() instanceof Skeleton) {
                        int skeleton = loadConfig("ItemData\\WidowSword.yml").getInt("skeleton");
                        e.setDamage(e.getDamage() * (1 + skeleton / 100));
                    } else if (e.getEntity() instanceof Spider) {
                        int spider = loadConfig("ItemData\\WidowSword.yml").getInt("spider");
                        e.setDamage(e.getDamage() * (1 + spider / 100));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (name.equalsIgnoreCase(ChatColor.WHITE + "Grappling Hook")) {
                if(e.getState().equals(PlayerFishEvent.State.REEL_IN)) {
                    Location change = e.getHook().getLocation().subtract(player.getLocation());
                    player.setVelocity(change.toVector().multiply(0.4));
                }
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (name.equalsIgnoreCase(ChatColor.DARK_PURPLE + "Aspect of The Void")) {
                    Block block = player.getTargetBlock((Set<Material>) null, 12);
                    Location l = block.getLocation();
                    l.add(0, 1, 0);
                    float yaw = player.getEyeLocation().getYaw();
                    float pitch = player.getEyeLocation().getPitch();
                    l.setYaw(yaw);
                    l.setPitch(pitch);
                    player.teleport(l);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 10);
                } else if (name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun")) {
                    Snowball s = player.launchProjectile(Snowball.class, player.getLocation().getDirection());
                    s.setVelocity(player.getLocation().getDirection().multiply(10));
                } else if (name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Terminator")) {
                    Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    arrow.setVelocity(arrow.getVelocity().multiply(5));
                    arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    arrow.setDamage(50);
                    Arrow a1 = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    a1.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(5)).multiply(5));
                    a1.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    a1.setDamage(50);
                    Arrow a2 = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    a2.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(-5)).multiply(5));
                    a2.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    a2.setDamage(50);
                    e.setCancelled(true);
                } else if(name.equalsIgnoreCase(ChatColor.GOLD + "Explosive Bow")) {
                    Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    arrow.setVelocity(arrow.getVelocity().multiply(5));
                    arrow.setDamage(50);
                    e.setCancelled(true);

                }
            }
        }
    }
    @EventHandler
    public void onEntityShoot(EntityShootBowEvent e) {
        if (e.getProjectile() instanceof Arrow) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                    String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                    if (name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Terminator")) {
                        e.setCancelled(true);
                    } else if(name.equalsIgnoreCase(ChatColor.GOLD + "Explosive Bow")) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if(e.getEntity().getShooter() instanceof Player) {
            Player shooter = (Player) e.getEntity().getShooter();
            if(shooter.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = shooter.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if(name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Frag Grenade")) {
                    if(e.getHitBlock() == null) {
                        Location l = e.getHitEntity().getLocation();
                        e.getHitEntity().getWorld().createExplosion(l.getX(),l.getY(),l.getZ(),100,false,false);
                    } else if(e.getHitEntity() == null) {
                        Location l = e.getHitBlock().getLocation();
                        e.getHitBlock().getWorld().createExplosion(l.getX(),l.getY(),l.getZ(),100,false,false);
                    }
                } else if(name.equalsIgnoreCase(ChatColor.GOLD + "Explosive Bow")) {
                    Arrow arrow = (Arrow) e.getEntity();
                    Location al = arrow.getLocation();
                    shooter.getWorld().createExplosion(al, 100, false, false);
                }
            }

        }
    }
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void Projectile(ProjectileLaunchEvent e) {
        if(e.getEntity().getShooter() instanceof Player) {
            Player player = (Player) e.getEntity().getShooter();
            if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if (name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Frag Grenade")) {
                    Egg s = (Egg) e.getEntity();
                    s.setVelocity(player.getLocation().getDirection().multiply(10));
                }
            }
        }
    }
}