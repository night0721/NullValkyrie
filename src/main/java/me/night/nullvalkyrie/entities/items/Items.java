package me.night.nullvalkyrie.entities.items;

import me.night.nullvalkyrie.entities.miners.Rarity;
import org.bukkit.Material;

public enum Items {

    ETERNALSTARE("Eternal Stare", 29, Rarity.LEGENDARY, Material.COAL, 10), // legendary charm
    MORNINGTEA("Morning Tea", 28, Rarity.EPIC, Material.IRON_INGOT, 12), // epic emote
    PARACHUTE("Parachute - Doomed Chorus", 11, Rarity.EPIC, Material.GOLD_INGOT, 14), // epic parachute
    RALLYCAR("Rally Car - Doomed Chorus", 10, Rarity.EPIC, Material.REDSTONE, 16), //epic backpack
    WORLDAFLAME("World Aflame", 6.5, Rarity.LEGENDARY, Material.LAPIS_LAZULI, 28), // legendary background
    MOLOTOVCOTAIL("Molotov Cotail - Soul Flame", 5.5, Rarity.EPIC, Material.COPPER_INGOT, 30), // epic throwable
    KATANA("Katana - Silent Echo", 4.67, Rarity.EPIC, Material.EMERALD, 32), // epic melee
    DLQ33("DL Q33 - Doomed Chorus", 4, Rarity.EPIC, Material.QUARTZ, 34), // epic gun
    DAME("Dame - Shot Caller", 1.25, Rarity.EPIC, Material.DIAMOND, 19), // character epic
    KILO141("Kilo 141 - Demonsong", 0.08, Rarity.LEGENDARY, Material.NETHERITE_INGOT, 25); // weapon legendary

    private final String name;
    private final double weight;
    private final Rarity rarity;
    private final Material material;
    private final int slot;

    Items(String name, double weight, Rarity rarity, Material material, int slot) {
        this.name = name;
        this.weight = weight;
        this.rarity = rarity;
        this.material = material;
        this.slot = slot;
    }
    public static Items getByName(String name) {
        for (Items item : Items.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Material getMaterial() {
        return material;
    }

    public int getSlot() {
        return slot;
    }
}
