package me.night.nullvalkyrie.items;

import me.night.nullvalkyrie.Main;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class CustomItemManager {
    private static final HashMap<String, ItemStack> weapons = new HashMap<>();
    public static HashMap<String, NamespacedKey> keys = new HashMap<>();
    private static Main main;
    public CustomItemManager(Main main) {
        CustomItemManager.main = main;
        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
        if(!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }
        createDirectoryInPluginFolder("ItemData");
        createFilesFromConfig(main.getConfig());
        register();
    }
    public void register() {
        List<String> files = getAllFilesFromDirectory("ItemData");
        for (String file : files) {
            FileConfiguration fileConfig = loadConfig("ItemData/" + file);
            ItemStack item = new ItemStack(Material.matchMaterial(fileConfig.getString("material")));
            List<String> properties = new ArrayList<>();
            List<String> itemAbility = new ArrayList<>();
            HashMap<String, Double> attributes = new HashMap<>();
            for (String key : fileConfig.getKeys(true)) {
                if (key.startsWith("enchants.")) {
                    item.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(Arrays.asList(key.split("\\.")).get(1))), fileConfig.getInt(key));
                } else if (key.startsWith("lore.properties.")) {
                    String property = Arrays.asList(key.split("\\.")).get(2);
                    if (property.equals("damage") && fileConfig.getInt(key) > 0) {
                        properties.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+" + fileConfig.getInt(key));
                    } else if (property.equals("speed") && fileConfig.getInt(key) > 0) {
                        properties.add(ChatColor.GRAY + "Speed: " + ChatColor.RED + "+" + fileConfig.getInt(key));
                    }
                } else if (key.startsWith("lore.ability.")) {
                    String paths = Arrays.asList(key.split("\\.")).get(2);
                    if (paths.equals("name")) {
                        itemAbility.add(ChatColor.GOLD + "Item Ability: " + fileConfig.getString(key));
                    } else if (paths.equals("details")) {
                        for (Object line : fileConfig.getList(key)) {
                            itemAbility.add(ChatColor.GRAY.toString() + line);
                        }
                    }
                } else if (key.startsWith("attributes.")) {
                    if (fileConfig.getDouble(key) > 0.0) {
                        attributes.put(Arrays.asList(key.split("\\.")).get(1), fileConfig.getDouble(key));
                    }
                } else if (key.startsWith("recipe.")) {

                }
            }
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Rarity.getRarity(fileConfig.getString("rarity")).getColor() + fileConfig.getString("name"));
            itemMeta.setUnbreakable(true);
            ArrayList<String> lore = new ArrayList<>();
            lore.addAll(properties);
            lore.add("");
            ArrayList<String> enchantmentList = new ArrayList<>();
            for (Enchantment enchantment : item.getEnchantments().keySet()) {
                List<String> splitted = Arrays.asList(Arrays.asList(enchantment.getKey().toString().split(":")).get(1).split("_"));
                StringBuilder builder = new StringBuilder();
                for (String strings : splitted) {
                    String formatted = strings.substring(0, 1).toUpperCase() + strings.substring(1);
                    if (splitted.size() > 1) {
                        if (strings.equals(splitted.get(splitted.size() - 1))) {
                            builder.append(formatted);
                        } else {
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
            lore.add(Rarity.getRarity(fileConfig.getString("rarity")).getDisplay());
            itemMeta.setLore(lore);
            for (String attribute : attributes.keySet()) {
                if (attribute.equals("damage")) {
                    AttributeModifier p = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", attributes.get(attribute), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, p);
                } else if (attribute.equals("moveSpeed")) {
                    AttributeModifier s = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", attributes.get(attribute), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                    itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, s);
                }
            }
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
            for (String key : fileConfig.getKeys(true)) {
                if (key.startsWith("pdc.")) {
                    String property = Arrays.asList(key.split("\\.")).get(1);
                    if (property.equals("ammo")) {
                        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                        NamespacedKey key1 = new NamespacedKey(main, "ammo");
                        keys.put(fileConfig.getString("name") + "." + property, key1);
                        container.set(key1, PersistentDataType.INTEGER, fileConfig.getInt(key));
                    } else if (property.equals("maxload")) {
                        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                        NamespacedKey key2 = new NamespacedKey(main, "maxload");
                        keys.put(fileConfig.getString("name") + "." + property, key2);
                        container.set(key2, PersistentDataType.INTEGER, fileConfig.getInt(key));
                    }
                }
            }
            item.setItemMeta(itemMeta);
            weapons.put(fileConfig.getString("name"), item);
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
        File file = new File(main.getDataFolder(), path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file;
    }

    public static void createDirectoryInPluginFolder(String path) {
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
    public static List<String> getAllFilesFromDirectory(String path) {
        ArrayList<String> ns = new ArrayList<>();
        try {
            File f = new File(main.getDataFolder(), path);
            File[] files = f.listFiles();
            for (File file : files) {
                ns.add(file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }
    public void createFilesFromConfig(FileConfiguration config) {
        for(String filename : config.getKeys(false)) {
            FileConfiguration fileConfig = loadConfig("ItemData/" + filename + ".yml");
            for(String key : config.getKeys(true)) {
                if(key.startsWith(filename)) {
                    List<String> paths = new ArrayList<>(Arrays.asList(key.split("\\.")));
                    if(paths.size() != 1) {
                        paths.remove(0);
                        if(paths.size() == 1) {
                            fileConfig.set(paths.get(0), config.get(key));
                        } else {
                            fileConfig.set(String.join(".", paths), config.get(key));
                        }
                        try {
                            fileConfig.save(loadFile("ItemData/" + filename + ".yml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }
    public static ItemStack getItem(String name){
        return weapons.get(name);
    }

    public static void updateYamlFilesToPlugin(String path) {
        File file = new File(main.getDataFolder(), path);
        if (!file.exists()) main.saveResource(path, true);
        else main.saveResource(path, true);
    }
}
