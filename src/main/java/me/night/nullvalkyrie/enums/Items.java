package me.night.nullvalkyrie.enums;

import org.bukkit.Material;

public enum Items {

    ETERNALSTARE("Eternal Stare", 29, Rarity.LEGENDARY, Material.COAL), // legendary charm
    MORNINGTEA("Morning Tea", 28, Rarity.EPIC, Material.IRON_INGOT), // epic emote
    PARACHUTE("Parachute - Doomed Chorus", 11, Rarity.EPIC, Material.GOLD_INGOT), // epic parachute
    RALLYCAR("Rally Car - Doomed Chorus", 10, Rarity.EPIC, Material.REDSTONE), //epic backpack
    WORLDAFLAME("World Aflame", 6.5, Rarity.LEGENDARY, Material.LAPIS_LAZULI), // legendary background
    MOLOTOVCOTAIL("Molotov Cotail - Soul Flame", 5.5, Rarity.EPIC, Material.COPPER_INGOT), // epic throwable
    KATANA("Katana - Silent Echo", 4.67, Rarity.EPIC, Material.EMERALD), // epic melee
    DLQ33("DL Q33 - Doomed Chorus", 4, Rarity.EPIC, Material.QUARTZ), // epic gun
    DAME("Dame - Shot Caller", 1.25, Rarity.EPIC, Material.DIAMOND), // character epic
    KILO141("Kilo 141 - Demonsong", 0.08, Rarity.LEGENDARY, Material.NETHERITE_INGOT); // weapon legendary

    private final String name;
    private final double weight;
    private final Rarity rarity;
    private final Material material;


    Items(String name, double weight, Rarity rarity, Material material) {
        this.name = name;
        this.weight = weight;
        this.rarity = rarity;
        this.material = material;
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
}
