package com.night.nullvalkyrie;

import com.night.nullvalkyrie.Chests.MenuListener;
import com.night.nullvalkyrie.RankSys.*;
import com.night.nullvalkyrie.commands.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {
    private BossBar bossbar;
    private RankManager rankManager;
    private NameTagManager nameTagManager;
    private SideBarManager sideBarManager;
    private BelowNameManager belowNameManager;

    public RankManager getRankManager() {
        return rankManager;
    }
    public NameTagManager getNameTagManager() { return nameTagManager; }
    public SideBarManager getSideBarManager() { return  sideBarManager; }
    public BelowNameManager getBelowNameManager() { return belowNameManager; }
    @Override
    public void onEnable() {
        new VanishCommand();new TestCommand();new WeaponCommand();new AnvilCommand();new ArmorCommand();new MenuCommand();new RankCommand(this);
        new MessageCommand();new HologramCommand();new CraftCommand();new EnchantingCommand();new SpawnCommand();
        bossbar = Bukkit.createBossBar(
                ChatColor.GOLD + "Kuudra",
                BarColor.RED,
                BarStyle.SEGMENTED_12
        );
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(this), this);
        nameTagManager = new NameTagManager(this);
        rankManager = new RankManager(this);
        sideBarManager = new SideBarManager(this);
        belowNameManager = new BelowNameManager();
        ItemStack widow_sword = new ItemStack(Material.STICK);
        widow_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
        widow_sword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 10);
        widow_sword.addUnsafeEnchantment(Enchantment.LUCK, 10);
        widow_sword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemMeta wsMeta = widow_sword.getItemMeta();
        wsMeta.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Fabled Widow Sword");
        wsMeta.setUnbreakable(true);
        widow_sword.setItemMeta(wsMeta);
        ShapedRecipe wither_sword_recipe = new ShapedRecipe(new NamespacedKey(this, "widow_sword"), widow_sword);
        wither_sword_recipe.shape(" A ", " A "," B ");
        wither_sword_recipe.setIngredient('A', Material.IRON_INGOT);
        wither_sword_recipe.setIngredient('B', Material.STICK);
        Bukkit.addRecipe(wither_sword_recipe);
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
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.hasItem()) {
            if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if (name.equalsIgnoreCase(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun")) {
                    Snowball s = player.launchProjectile(Snowball.class, player.getLocation().getDirection());
                    s.setVelocity(player.getLocation().getDirection().multiply(10));
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().sendTitle(ChatColor.RED +"Welcome to Apache!", ChatColor.GREEN + "LOL", 20, 100, 20);
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§1NOT ENOUGH MANNER"));

        bossbar.addPlayer(e.getPlayer());
        e.getPlayer().setPlayerListHeaderFooter(ChatColor.AQUA + "You are playing on " + ChatColor.GREEN + "127.0.0.1", ChatColor.GOLD + "Ranks, boosters, & more!" + ChatColor.AQUA + "127.0.0.1");
    }
    public String centerText(String text, int lineLength) {
        StringBuilder builder = new StringBuilder();
        char space = ' ';
        int distance = (lineLength - text.length()) / 2;
        for (int ii = 0; ii < distance; ii++) {
            builder.append(space);
        }
        builder.append(text);
        for (int i = 0; i < distance; ++i) {
            builder.append(space);
        }
        return builder.toString();
    }
    @EventHandler
    public void onPing(ServerListPingEvent e) {
        e.setMaxPlayers(8964);
        String s = centerText("Apache\n", 45);
        String s2 = centerText("Support 1.18 & 1.8.9",25);
        e.setMotd(ChatColor.AQUA.toString() + ChatColor.BOLD + s + ChatColor.GOLD + ChatColor.BOLD + s2);
        try {
            e.setServerIcon(Bukkit.loadServerIcon(new File("nuke.png")));
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }
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
//    For hologram clicks to change page
//    @EventHandler
//    public void onEntityInteract(EntityInteractEvent e) {
//        System.out.println(e.getEntity().getLocation());
//        e.getEntity().setCustomName(ChatColor.RED + "Changed name since you ust clicked lol");
//    }

}
