package me.night.nullvalkyrie.util.enchantments;

import org.bukkit.enchantments.Enchantment;

public enum Enchantments {
    THUNDERBOLT("thunderbolt", "ThunderBolt", 5),
    SMELTING_TOUCH("smelting-touch", "Smelting Touch", 1);
    public final String namespacekey;
    public final String name;
    public final int maxLevel;



    Enchantments(String namespacekey, String name, int maxLevel) {
        this.namespacekey = namespacekey;
        this.name = name;
        this.maxLevel = maxLevel;
    }
    public String getNamespacekey() {
        return namespacekey;
    }

    public String getName() {
        return name;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
    public Enchantment getEnchant() {
        return EnchantmentManager.enchants.get(this.ordinal());
    }
}
