package com.night.nullvalkyrie.Enchantments;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EnchantmentManager {
    public EnchantmentManager() {
        register();
    }
    public static final Enchantment ThunderBolt = new CustomEnchantment("thunderbolt", "ThunderBolt", 5);
    public static final Enchantment SmeltingTouch = new CustomEnchantment("smelting-touch", "Smelting Touch", 1);
    public static void register() {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(ThunderBolt);
        if(!registered) {
            registerEnchantment(ThunderBolt);
            registerEnchantment(SmeltingTouch);
        }

    }
    public static void registerEnchantment(Enchantment en) {
        try {
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Enchantment.registerEnchantment(en);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
