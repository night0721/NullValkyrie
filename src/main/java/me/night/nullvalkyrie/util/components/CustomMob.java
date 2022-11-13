package me.night.nullvalkyrie.util.components;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static me.night.nullvalkyrie.util.Utils.color;
import static me.night.nullvalkyrie.util.Utils.*;

public enum CustomMob {

    DESERT_RISEN(
            "&6Desert Risen",
            2048,
            60,
            EntityType.HUSK,
            null,
            null,
            new LootItem(createItem(Material.ROTTEN_FLESH, 1, "&fPreserved Flesh", "&7A preserved flesh from a rotting corpse", "&7Not sure what you'd want this for, though", "&7", "&9Foodstuff"), 1, 3, 100)),

    SKELETAL_MAGE(
            "&dSkeletal Mage",
            2048,
            15,
            EntityType.SKELETON,
            createItem(Material.BONE, 1, null), makeArmorSet(new ItemStack(Material.IRON_HELMET),
            null,
            null,
            null),
            new LootItem(createItem(Material.BONE, 1, "&dBone Wand", "&7A wand made from skeletal bones"), 30),
            new LootItem(new ItemStack(Material.BONE), 1, 3, 100)),

    ZOMBIE_SQUIRE(
            "&bZombie Squire",
            2048,
            12,
            EntityType.ZOMBIE,
            new ItemStack(Material.IRON_SWORD),
            makeArmorSet(new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_BOOTS)), new LootItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), 35), new LootItem(new ItemStack(Material.CHAINMAIL_LEGGINGS), 35), new LootItem(new ItemStack(Material.CHAINMAIL_HELMET), 35), new LootItem(new ItemStack(Material.IRON_BOOTS), 25), new LootItem(new ItemStack(Material.IRON_SWORD), 40)),

    CHARRED_ARCHER(
            "&8Charred Archer",
            2048,
            3,
            EntityType.WITHER_SKELETON,
            enchantItem(new ItemStack(Material.BOW), Enchantment.ARROW_KNOCKBACK, 2),
            null, new LootItem(enchantItem(enchantItem(createItem(Material.BOW, 1, "&cBurnt Bow", "&7This bow is burnt to a crisp but remains intact", "&8due to special enchantments"), Enchantment.ARROW_FIRE, 1), Enchantment.ARROW_KNOCKBACK, 2), 100),
            new LootItem(new ItemStack(Material.BONE), 1, 5, 100)),
    ;

    private String name;
    private double maxHealth, spawnChance;
    private EntityType type;
    private ItemStack mainItem;
    private ItemStack[] armor;
    private List<LootItem> lootTable;

    CustomMob(String name, double maxHealth, double spawnChance, EntityType type, ItemStack mainItem, ItemStack[] armor, LootItem... lootItems) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.type = type;
        this.mainItem = mainItem;
        this.armor = armor;
        lootTable = Arrays.asList(lootItems);
    }

    public LivingEntity spawn(Location location) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        entity.setCustomNameVisible(true);
        entity.setCustomName(color(name + " &r&c" + (int) maxHealth + "/" + (int) maxHealth + "❤"));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);
        EntityEquipment inv = entity.getEquipment();
        if (armor != null) inv.setArmorContents(armor);
        inv.setHelmetDropChance(0f);
        inv.setChestplateDropChance(0f);
        inv.setLeggingsDropChance(0f);
        inv.setBootsDropChance(0f);
        inv.setItemInMainHand(mainItem);
        inv.setItemInMainHandDropChance(0f);
        return entity;
    }

    public void tryDropLoot(Location location) {
        for (LootItem item : lootTable) {
            item.tryDropItem(location);
        }
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

}
