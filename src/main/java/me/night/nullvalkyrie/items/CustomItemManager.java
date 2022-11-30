package me.night.nullvalkyrie.items;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.util.Util;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static me.night.nullvalkyrie.database.CustomWeaponsDataManager.getWeapon;


public class CustomItemManager {
    private static final HashMap<String, ItemStack> weapons = new HashMap<>();
    public static HashMap<String, NamespacedKey> keys = new HashMap<>();
    private static Main main;

    public CustomItemManager(Main main) {
        CustomItemManager.main = main;
    }

    public static ItemStack produceItem(String itemName) {
        HashMap<String, Object> weapon = getWeapon(itemName);

        ItemStack item = new ItemStack((Material) weapon.get("Material"));
        List<String> properties = new ArrayList<>();
        List<String> itemAbility = new ArrayList<>();
        HashMap<String, Object> enchants = (HashMap<String, Object>) weapon.get("Enchants");
        HashMap<String, Object> attributes = (HashMap<String, Object>) weapon.get("Attributes");
        for (String enchant : enchants.keySet()) {
            item.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchant)), (Integer) enchants.get(enchant));
        }
        HashMap<String, Object> lo = (HashMap<String, Object>) weapon.get("Lore");
        HashMap<String, Object> abi = (HashMap<String, Object>) lo.get("Ability");
        HashMap<String, Object> prob = (HashMap<String, Object>) lo.get("Properties");
        for (String p : prob.keySet())
            if ((int) prob.get(p) > 0)
                properties.add(ChatColor.GRAY + Util.capitalize(p) + ": " + ChatColor.RED + "+" + prob.get(p));
        itemAbility.add(ChatColor.GOLD + "Item Ability: " + abi.get("Name"));
        for (String line : (List<String>) abi.get("Details"))
            itemAbility.add(ChatColor.GRAY + line);
        //recipe
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Rarity.getRarity((String) weapon.get("Rarity")).getColor() + weapon.get("Name"));
        itemMeta.setUnbreakable(true);
        ArrayList<String> lore = new ArrayList<>();
        lore.addAll(properties);
        lore.add("");
        ArrayList<String> enchantmentList = new ArrayList<>();
        for (Enchantment enchantment : item.getEnchantments().keySet()) {
            List<String> splitted = Arrays.asList(Arrays.asList(enchantment.getKey().toString().split(":")).get(1).split("_"));
            StringBuilder builder = new StringBuilder();
            for (String strings : splitted) {
                String formatted = Util.capitalize(strings);
                if (splitted.size() > 1) {
                    if (strings.equals(splitted.get(splitted.size() - 1))) builder.append(formatted);
                    else {
                        builder.append(formatted);
                        builder.append(" ");
                    }
                } else builder.append(formatted);
            }
            enchantmentList.add(builder + " " + item.getEnchantmentLevel(enchantment));
        }
        lore.add(ChatColor.BLUE + String.join(", ", enchantmentList));
        lore.add("");
        lore.addAll(itemAbility);
        lore.add("");
        lore.add(Rarity.getRarity((String) weapon.get("Rarity")).getDisplay());
        itemMeta.setLore(lore);
        for (String attribute : attributes.keySet()) {
            if (attribute.equals("damage")) {
                AttributeModifier p = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", (Double) attributes.get(attribute), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, p);
            } else if (attribute.equals("moveSpeed")) {
                AttributeModifier s = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", (Double) attributes.get(attribute), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, s);
            }
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
//        for (String key : fileConfig.getKeys(true)) {
//            if (key.startsWith("pdc.")) {
//                String property = Arrays.asList(key.split("\\.")).get(1);
//                if (property.equals("ammo")) {
//                    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
//                    NamespacedKey key1 = new NamespacedKey(main, "ammo");
//                    keys.put(Rarity.getRarity(fileConfig.getString("rarity")).getColor() + fileConfig.getString("name") + "." + property, key1);
//                    container.set(key1, PersistentDataType.INTEGER, fileConfig.getInt(key));
//                } else if (property.equals("maxload")) {
//                    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
//                    NamespacedKey key2 = new NamespacedKey(main, "maxload");
//                    keys.put(Rarity.getRarity(fileConfig.getString("rarity")).getColor() + fileConfig.getString("name") + "." + property, key2);
//                    container.set(key2, PersistentDataType.INTEGER, fileConfig.getInt(key));
//                }
//            }
//        }
        item.setItemMeta(itemMeta);
        return item;
    }


    public static void setItemRecipe(NamespacedKey key, ItemStack i, int ingredient, String shape1, String shape2, String shape3, List<Material> ingredients) {
//        ShapedRecipe wither_sword_recipe = new ShapedRecipe(new NamespacedKey(main, "widow_sword"), widow_sword);
//        wither_sword_recipe.shape(" A ", " A "," B ");
//        wither_sword_recipe.setIngredient('A', Material.IRON_INGOT);
//        wither_sword_recipe.setIngredient('B', Material.STICK);
//        Bukkit.addRecipe(wither_sword_recipe);
    }

    public static YamlConfiguration loadConfig(String path) {
        File f = new File(main.getDataFolder(), path);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return YamlConfiguration.loadConfiguration(f);
    }

    public static ItemStack getItem(String name) {
        return weapons.get(name);
    }

    public static void updateYamlFilesToPlugin(String path) {
        File file = new File(main.getDataFolder(), path);
        if (!file.exists()) main.saveResource(path, true);
        else main.saveResource(path, true);
    }
}
