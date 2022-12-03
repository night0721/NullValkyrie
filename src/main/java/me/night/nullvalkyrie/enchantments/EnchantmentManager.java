package me.night.nullvalkyrie.enchantments;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnchantmentManager {
    public static final Enchantment ThunderBolt = new CustomEnchantment("thunderbolt", "ThunderBolt", 5);
    public static final Enchantment SmeltingTouch = new CustomEnchantment("smelting-touch", "Smelting Touch", 1);

    public static void register() {
        List<Enchantment> enchants = new ArrayList<>();
        enchants.add(ThunderBolt);
        enchants.add(SmeltingTouch);
        List<Boolean> registeredList = new ArrayList<>();
        for (Enchantment enchant : enchants) {
            registeredList.add(Arrays.stream(Enchantment.values()).toList().contains(enchant));
        }
        for (int counter = 0; counter < registeredList.size(); counter++) {
            if (!registeredList.get(counter)) registerEnchantment(enchants.get(counter));
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
