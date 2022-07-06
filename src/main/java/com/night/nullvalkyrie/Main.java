package com.night.nullvalkyrie;

import com.night.nullvalkyrie.Chests.MenuListener;
import com.night.nullvalkyrie.NameTag.NameTagManager;
import com.night.nullvalkyrie.RankSys.RankListener;
import com.night.nullvalkyrie.RankSys.RankManager;
import com.night.nullvalkyrie.SideBar.SideBarListener;
import com.night.nullvalkyrie.commands.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin implements Listener {
    private BossBar bossbar;
    private RankManager rankManager;
    private NameTagManager nameTagManager;

    public RankManager getRankManager() {
        return rankManager;
    }
    public NameTagManager getNameTagManager() { return nameTagManager; }
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("PREPARING TO DESTROY HYPIXEL");

        getCommand("test").setExecutor(new TestCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("armor").setExecutor(new ArmorCommand());
        getCommand("gun").setExecutor(new GunCommand());
        getCommand("msg").setExecutor(new MessageCommand());
        getCommand("menu").setExecutor(new MenuCommand());
        getCommand("rank").setExecutor(new RankCommand(this));
        getCommand("hologram").setExecutor(new HologramCommand());
        bossbar = Bukkit.createBossBar(
                ChatColor.GOLD + "Kuudra",
                BarColor.RED,
                BarStyle.SEGMENTED_12
        );
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new RankListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SideBarListener(this), this);
        rankManager = new RankManager(this);
        nameTagManager = new NameTagManager(this);

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
        e.setJoinMessage(rankManager.getRank(e.getPlayer().getUniqueId()).getDisplay() + " " + e.getPlayer().getName() + ChatColor.WHITE + " joined the server!");

        e.getPlayer().sendTitle(ChatColor.RED +"Welcome to Operation Valkyrie!", ChatColor.GREEN + "LOL", 20, 100, 20);
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
        String s = centerText("Operation Valkyrie\n", 45);
        String s2 = centerText("Support 1.18 & 1.8.9",45);
        e.setMotd(ChatColor.AQUA.toString() + ChatColor.BOLD + s + ChatColor.GOLD + ChatColor.BOLD + s2);
        try {
            e.setServerIcon(Bukkit.loadServerIcon(new File("nuke.png")));
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }
//    For hologram clicks to change page
//    @EventHandler
//    public void onEntityInteract(EntityInteractEvent e) {
//        System.out.println(e.getEntity().getLocation());
//        e.getEntity().setCustomName(ChatColor.RED + "Changed name since you ust clicked lol");
//    }

}
