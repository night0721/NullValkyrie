package com.night.nullvalkyrie.Items;

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


public class CustomItemManager {
    private static HashMap<String, ItemStack> a = new HashMap<>();
    private static Main main;
    public CustomItemManager(Main main) {
        this.main = main;
        if(!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }
        createItemDataDirectory("ItemData");
        register();
    }
    public void register() {
        List<String> hh = getAllFilesFromDirectory("ItemData");
        for(int kk = 0; kk < hh.size(); kk++) {
            FileConfiguration c = loadConfig("ItemData\\" + hh.get(kk));
            ItemStack i = new ItemStack(Material.matchMaterial(c.getString("material")));
            HashMap<String, List<String>> d = new HashMap<>();
            List<String> pr = new ArrayList<>();
            List<String> ia = new ArrayList<>();
            HashMap<String, Double> aa = new HashMap<>();
            for (String key : c.getKeys(true)) {
                if (key.startsWith("enchants.")) {
                    i.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(Arrays.asList(key.split("\\.")).get(1))), c.getInt(key));
                } else if (key.startsWith("lore.properties.")) {
                    String property = Arrays.asList(key.split("\\.")).get(2);
                    if (property.equals("damage") && c.getInt(key) > 0) {
                        pr.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+" + c.getInt(key));
                    } else if (property.equals("damage") && c.getInt(key) > 0) {
                        pr.add(ChatColor.GRAY + "Speed: " + ChatColor.RED + "+" + c.getInt(key));
                    }
                } else if (key.startsWith("lore.ability.")) {
                    String a = Arrays.asList(key.split("\\.")).get(2);
                    if (a.equals("name")) {
                        ia.add(ChatColor.GOLD + "Item Ability: " + c.getString(key));
                    } else if (a.equals("details")) {
                        for (Object line : c.getList(key)) {
                            ia.add(ChatColor.GRAY.toString() + line);
                        }
                    }
                } else if (key.startsWith("attributes.")) {
                    if (c.getDouble(key) > 0.0) {
                        aa.put(Arrays.asList(key.split("\\.")).get(1), c.getDouble(key));
                    }
                } else if (key.startsWith("recipe.")) {

                }
            }
            d.put("properties", pr);
            d.put("ability", ia);
            d.put("attributes", pr);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(Rarity.getRarity(c.getString("rarity")).getColor() + c.getString("name"));
            im.setUnbreakable(true);
            ArrayList<String> l = new ArrayList<>();
            l.addAll(d.get("properties"));
            l.add("");
            ArrayList<String> e = new ArrayList<>();
            for (Enchantment ee : i.getEnchantments().keySet()) {
                List<String> splitted = Arrays.asList(Arrays.asList(ee.getKey().toString().split(":")).get(1).split("_"));
                StringBuilder n = new StringBuilder();
                for (String ii : splitted) {
                    String s = ii.substring(0, 1).toUpperCase() + ii.substring(1);
                    if (splitted.size() > 1) {
                        if (ii.equals(splitted.get(splitted.size() - 1))) {
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
            l.addAll(ia);
            l.add("");
            l.add(Rarity.getRarity(c.getString("rarity")).getDisplay());
            im.setLore(l);
            for (String q : aa.keySet()) {
                if (q.equals("damage")) {
                    AttributeModifier p = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", aa.get(q), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                    im.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, p);
                } else if (q.equals("moveSpeed")) {
                    AttributeModifier s = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", aa.get(q), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                    im.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, s);
                }
            }
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
            i.setItemMeta(im);
            a.put(c.getString("name"), i);
        }
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
    private static List<String> getAllFilesFromDirectory(String path) {
        ArrayList<String> ns = new ArrayList<>();
        try {
            File f = new File(main.getDataFolder(), path);
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                ns.add(files[i].getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }
    public static ItemStack getItem(String name){
        return a.get(name);
    }
}
