package me.night.nullvalkyrie.util.enchantments;

import me.night.nullvalkyrie.enums.Enchantments;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnchantmentManager {
    public static List<Enchantment> enchants = new ArrayList<>();
    public static void register() {
        List<Boolean> registeredList = new ArrayList<>();
        for (Enchantments enchantment : Enchantments.values()) {
            Enchantment en = new CustomEnchantment(enchantment.getNamespacekey(), enchantment.getName(), enchantment.getMaxLevel());
            enchants.add(en);
            registeredList.add(Arrays.stream(Enchantment.values()).toList().contains(en));
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
