package me.night.nullvalkyrie.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static ItemStack createItem(Material type, int amount, String name, String... lines) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        if (name != null) meta.setDisplayName(color(name));
        if (lines != null) {
            List<String> lore = new ArrayList<>();
            for (String line : lines) {
                lore.add(color(line));
            }
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack enchantItem(ItemStack item, Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
        return item;
    }

    public static ItemStack[] makeArmorSet(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        ItemStack[] armor = new ItemStack[4];
        armor[3] = helmet;
        armor[2] = chestplate;
        armor[1] = leggings;
        armor[0] = boots;
        return armor;
    }

}
