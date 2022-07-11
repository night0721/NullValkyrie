package com.night.nullvalkyrie.Items;


import com.night.nullvalkyrie.Enchantments.EnchantmentHandler;
import com.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static com.night.nullvalkyrie.Enchantments.EnchantmentHandler.ThunderBolt;

public class CustomItemManager {
    private static Main main;
    public CustomItemManager(Main main) {
        this.main = main;
        if(!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

    }
    public static ItemStack WidowSword;
    public static ItemStack Terminator;
    public static ItemStack ExplosiveBow;
    public static ItemStack Grenade;
    public static ItemStack SnowGun;
    public static void register() {
        createItemDataDirectory("ItemData");
        setWidowSword();
        setTerminator();
        setGrenade();
        setSnowGun();
    }

    public static YamlConfiguration loadConfig(String path) {
        File f = new File(main.getDataFolder(), path);
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return YamlConfiguration.loadConfiguration(f);
    }
    public static File loadFile(String path) {
        File f = new File(main.getDataFolder(), path);
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return f;
    }
    private static void createItemDataDirectory(String path) {
        File f = new File(main.getDataFolder(), path);
        if(!f.exists()) {
            try {
                if(!f.mkdir()) {
                    try {
                        f.mkdir();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }
    private static void setWidowSword() {
        YamlConfiguration config = loadConfig("ItemData\\WidowSword.yml");
        config.set("zombie", 100);
        config.set("skeleton", 100);
        config.set("spider", 100);
        try {
            config.save(loadFile("ItemData\\WidowSword.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int skeletondmg = config.getInt("skeleton");
        int zombiedmg = config.getInt("zombie");
        int spiderdmg = config.getInt("spider");
        ItemStack widow_sword = new ItemStack(Material.STICK);
        widow_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
        widow_sword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 10);
        ItemMeta wsMeta = widow_sword.getItemMeta();
        wsMeta.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Fabled Widow Sword");
        wsMeta.setUnbreakable(true);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+100");
        lore.add("");
        lore.add(ChatColor.GOLD + "Item Ability: Damage Multiplier");
        lore.add(ChatColor.GRAY + "Damage dealt to mobs will be multiplied");
        lore.add(ChatColor.RED + "Zombie +" + zombiedmg + "%");
        lore.add(ChatColor.RED + "Skeleton +" + skeletondmg + "%");
        lore.add(ChatColor.RED + "Spider +" + spiderdmg + "%");
        lore.add("");
        lore.add(net.md_5.bungee.api.ChatColor.of("#ff23ff").toString() + ChatColor.BOLD + "MYTHIC");
        wsMeta.setLore(lore);
        AttributeModifier dmg = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        wsMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, dmg);
        wsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        widow_sword.setItemMeta(wsMeta);
        WidowSword = widow_sword;
        ShapedRecipe wither_sword_recipe = new ShapedRecipe(new NamespacedKey(main, "widow_sword"), widow_sword);
        wither_sword_recipe.shape(" A ", " A "," B ");
        wither_sword_recipe.setIngredient('A', Material.IRON_INGOT);
        wither_sword_recipe.setIngredient('B', Material.STICK);
        Bukkit.addRecipe(wither_sword_recipe);
    }
    private static void setTerminator() {
        ItemStack terminator = new ItemStack(Material.BOW);
        terminator.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
        terminator.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 10);
        terminator.addUnsafeEnchantment(ThunderBolt,5);
        ItemMeta terminatorMeta = terminator.getItemMeta();
        terminatorMeta.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Terminator");
        terminatorMeta.setUnbreakable(true);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Item Ability: Triple Shot");
        lore.add(ChatColor.GRAY + "Shoot three arrow at one time");
        lore.add("");
        lore.add(net.md_5.bungee.api.ChatColor.of("#ff23ff").toString() + ChatColor.BOLD + "MYTHIC");
        terminatorMeta.setLore(lore);
        terminator.setItemMeta(terminatorMeta);
        Terminator = terminator;
    }
    private static void setGrenade() {
        ItemStack egg = new ItemStack(Material.EGG);
        ItemMeta eggdata = egg.getItemMeta();
        eggdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Frag Grenade");
        eggdata.setUnbreakable(true);
        eggdata.setLore(Arrays.asList(ChatColor.GOLD + "Boom"));
        egg.setItemMeta(eggdata);
        Grenade = egg;
    }
    private static void setSnowGun() {
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        hoe.addUnsafeEnchantment(EnchantmentHandler.ThunderBolt, 5);
        hoe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
        ItemMeta hoedata = hoe.getItemMeta();
        hoedata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun");
        hoedata.setUnbreakable(true);
        hoedata.setLore(Arrays.asList(ChatColor.GOLD + "Shoot Snowball!"));
        hoe.setItemMeta(hoedata);
        SnowGun = hoe;
    }
}