package com.night.nullvalkyrie.Items;

import com.night.nullvalkyrie.Enchantments.EnchantmentManager;
import com.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.night.nullvalkyrie.Enchantments.EnchantmentManager.ThunderBolt;

public class CustomItem {
    private static Main main;
    public CustomItem(Main main) {
        this.main = main;
        if(!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }
    }
    public static ItemStack WidowSword;
    public static ItemStack Terminator;
    public static ItemStack Grenade;
    public static ItemStack SnowGun;
    public static ItemStack ExplosiveBow;
    public static ItemStack AOTV;
    public static ItemStack GrapplingHook;
    public static void register() {
        createItemDataDirectory("ItemData");
        setWidowSword();
        setTerminator();
        setGrenade();
        setSnowGun();
        setExplosiveBow();
        setAOTV();
        setGrapplingHook();
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
        widow_sword.addUnsafeEnchantment(Enchantment.FIRE_ASPECT,2);
        widow_sword.addUnsafeEnchantment(Enchantment.LUCK,5);
        widow_sword.addUnsafeEnchantment(ThunderBolt,5);
        ItemMeta wsMeta = widow_sword.getItemMeta();
        wsMeta.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Fabled Widow Sword");
        wsMeta.setUnbreakable(true);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+100");
        lore.add("");
        ArrayList<String> enchants = new ArrayList<>();
        for (Enchantment enchant : widow_sword.getEnchantments().keySet()) {
            List<String> splitted = Arrays.asList(Arrays.asList(enchant.getKey().toString().split(":")).get(1).split("_"));
            StringBuilder name = new StringBuilder();
            for (String i : splitted) {
                String s = i.substring(0, 1).toUpperCase() + i.substring(1);
                if(splitted.size() > 1) {
                    if(i.equals(splitted.get(splitted.size() - 1))) {
                        name.append(s);
                    } else {
                        name.append(s);
                        name.append(" ");
                    }
                } else name.append(s);

            }
            enchants.add(name + " " + widow_sword.getEnchantmentLevel(enchant));
        }
        lore.add(ChatColor.BLUE + String.join(", ", enchants));
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
        wsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        widow_sword.setItemMeta(wsMeta);
        WidowSword = widow_sword;
        ShapedRecipe wither_sword_recipe = new ShapedRecipe(NamespacedKey.minecraft("widow_sword"), widow_sword);
        wither_sword_recipe.shape(" A ", " A "," B ");
        wither_sword_recipe.setIngredient('A', Material.IRON_INGOT);
        wither_sword_recipe.setIngredient('B', Material.STICK);
        Bukkit.addRecipe(wither_sword_recipe);
    }
    private static void setTerminator() {
        HashMap<String, List<String>> d = new HashMap<>();
        List<String> pr = new ArrayList<>();
        pr.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+50");
        d.put("properties", pr);
        List<String> ia = new ArrayList<>();
        ia.add(ChatColor.GOLD + "Item Ability: Triple Shot");
        ia.add(ChatColor.GRAY + "Shoot three arrow at one time");
        d.put("ability", ia);
        HashMap<Enchantment, Integer> e = new HashMap<>();
        e.put(Enchantment.DAMAGE_ALL, 20);
        e.put(Enchantment.LOOT_BONUS_MOBS, 10);
        e.put(ThunderBolt,5);
        Terminator = main.getCustomItemManager().register(Material.BOW, "Terminator",Rarity.MYTHIC, d, e,50.0,0.05);

    }
    private static void setGrenade() {
        ItemStack egg = new ItemStack(Material.EGG);
        ItemMeta eggdata = egg.getItemMeta();
        eggdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Frag Grenade");
        eggdata.setUnbreakable(true);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Item Ability: Throw a TNT ");
        lore.add(ChatColor.GRAY + "Shoot TNT with 25x more power!");
        lore.add("");
        lore.add(net.md_5.bungee.api.ChatColor.of("#ff23ff").toString() + ChatColor.BOLD + "MYTHIC");
        eggdata.setLore(lore);
        eggdata.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        egg.setItemMeta(eggdata);
        Grenade = egg;
    }
    private static void setSnowGun() {
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        hoe.addUnsafeEnchantment(EnchantmentManager.ThunderBolt, 5);
        hoe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
        ItemMeta hoedata = hoe.getItemMeta();
        hoedata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun");
        hoedata.setUnbreakable(true);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Item Ability: Shoot Snowball");
        lore.add(ChatColor.GRAY + "Shoot snowball!");
        lore.add("");
        lore.add(net.md_5.bungee.api.ChatColor.of("#ff23ff").toString() + ChatColor.BOLD + "MYTHIC");
        hoedata.setLore(lore);
        hoedata.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        hoe.setItemMeta(hoedata);

        SnowGun = hoe;
    }
    private static void setExplosiveBow() {
        ItemStack i = new ItemStack(Material.BOW);
        i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
        i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 10);
        i.addUnsafeEnchantment(ThunderBolt,5);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.GOLD + "Explosive Bow");
        im.setUnbreakable(true);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Item Ability: Explosive Shot");
        lore.add(ChatColor.GRAY + "Shoot explosive arrows");
        lore.add("");
        lore.add(ChatColor.GOLD.toString() + ChatColor.BOLD + "LEGENDARY");
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        ExplosiveBow = i;
    }
    private static void setAOTV() {
        ItemStack i = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.DARK_PURPLE + "Aspect of The Void");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+100");
        lore.add("");
        lore.add(ChatColor.GOLD + "Item Ability: Instant Teleport");
        lore.add(ChatColor.GRAY + "Teleport 12 blocks ahead");
        lore.add("");
        lore.add(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "EPIC");
        im.setLore(lore);
        im.setUnbreakable(true);
        AttributeModifier dmg = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 25, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier speed = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", 0.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        im.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, dmg);
        im.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, speed);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        AOTV = i;
    }
    private static void setGrapplingHook() {
        ItemStack i = new ItemStack(Material.FISHING_ROD, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.WHITE + "Grappling Hook");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "I believe I can fly...");
        lore.add("");
        lore.add(ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON");
        im.setLore(lore);
        im.setUnbreakable(true);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        GrapplingHook = i;
    }
}
