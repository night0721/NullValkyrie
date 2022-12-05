package me.night.nullvalkyrie.items;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.enums.Rarity;
import me.night.nullvalkyrie.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

import static me.night.nullvalkyrie.database.CustomWeaponsDataManager.getWeapon;

public class CustomItemManager {
    public static HashMap<String, NamespacedKey> keys = new HashMap<>();

    public static ItemStack produceItem(String itemName) {
        HashMap<String, Object> weapon = getWeapon(itemName);
        ItemStack item = new ItemStack((Material) weapon.get("Material"));
        List<String> propertiesList = new ArrayList<>();
        List<String> itemAbility = new ArrayList<>();
        HashMap<String, Object> enchants = (HashMap<String, Object>) weapon.get("Enchants");
        HashMap<String, Object> attributes = (HashMap<String, Object>) weapon.get("Attributes");
        for (String enchant : enchants.keySet())
            item.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchant)), (Integer) enchants.get(enchant));
        HashMap<String, Object> lore = (HashMap<String, Object>) weapon.get("Lore");
        HashMap<String, Object> ability = (HashMap<String, Object>) lore.get("Ability");
        HashMap<String, Object> properties = (HashMap<String, Object>) lore.get("Properties");
        for (String p : properties.keySet())
            if ((int) properties.get(p) > 0)
                propertiesList.add(ChatColor.GRAY + Util.capitalize(p) + ": " + ChatColor.RED + "+" + properties.get(p));
        itemAbility.add(ChatColor.GOLD + "Item Ability: " + ability.get("Name"));
        for (String line : (List<String>) ability.get("Details"))
            itemAbility.add(ChatColor.GRAY + line);
        //recipe
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Rarity.getRarity((String) weapon.get("Rarity")).getColor() + weapon.get("Name"));
        itemMeta.setUnbreakable(true);
        ArrayList<String> loreList = new ArrayList<>();
        loreList.addAll(propertiesList);
        loreList.add("");
        ArrayList<String> enchantmentList = new ArrayList<>();
        for (Enchantment enchantment : item.getEnchantments().keySet()) {
            List<String> split = Arrays.asList(Arrays.asList(enchantment.getKey().toString().split(":")).get(1).split("_"));
            StringBuilder builder = new StringBuilder();
            for (String strings : split) {
                String formatted = Util.capitalize(strings);
                if (split.size() > 1) {
                    if (strings.equals(split.get(split.size() - 1))) builder.append(formatted);
                    else {
                        builder.append(formatted);
                        builder.append(" ");
                    }
                } else builder.append(formatted);
            }
            enchantmentList.add(builder + " " + item.getEnchantmentLevel(enchantment));
        }
        loreList.add(ChatColor.BLUE + String.join(", ", enchantmentList));
        loreList.add("");
        loreList.addAll(itemAbility);
        loreList.add("");
        loreList.add(Rarity.getRarity((String) weapon.get("Rarity")).getDisplay());
        itemMeta.setLore(loreList);
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

    public static void updateYamlFilesToPlugin(String path) {
        File file = new File(Main.getPlugin(Main.class).getDataFolder(), path);
        if (!file.exists()) Main.getPlugin(Main.class).saveResource(path, true);
        else Main.getPlugin(Main.class).saveResource(path, true);
    }
}
