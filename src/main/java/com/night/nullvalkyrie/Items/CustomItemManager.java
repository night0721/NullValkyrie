package com.night.nullvalkyrie.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CustomItemManager {
    public ItemStack register(Material item, String name, Rarity rarity, HashMap<String, List<String>> lore, HashMap<Enchantment, Integer> enchantment, double damage, double movespeed) {
        ItemStack i = new ItemStack(item);
        for(Enchantment e : enchantment.keySet()) {
            i.addUnsafeEnchantment(e, enchantment.get(e));
        }
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(rarity.getColor() + name);
        im.setUnbreakable(true);
        ArrayList<String> l = new ArrayList<>();
        for(String ll : lore.get("properties")) {
            l.add(ll);
        }
        l.add("");
        ArrayList<String> e = new ArrayList<>();
        for (Enchantment ee : i.getEnchantments().keySet()) {
            List<String> splitted = Arrays.asList(Arrays.asList(ee.getKey().toString().split(":")).get(1).split("_"));
            StringBuilder n = new StringBuilder();
            for (String ii : splitted) {
                String s = ii.substring(0, 1).toUpperCase() + ii.substring(1);
                if(splitted.size() > 1) {
                    if(ii.equals(splitted.get(splitted.size() - 1))) {
                        n.append(s);
                    } else {
                        n.append(s);
                        n.append(" ");
                    }
                } else n.append(s);

            }
            e.add(n + " " + i.getEnchantmentLevel(ee));
        }
        l.add(ChatColor.BLUE + String.join(", ", e));
        l.add("");
        for(String ll : lore.get("ability")) {
            l.add(ll);
        }
        l.add("");
        l.add(rarity.getDisplay());
        im.setLore(l);
        AttributeModifier d = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        im.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, d);
        AttributeModifier s = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", movespeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        im.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, s);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);

        return i;
    }
}
