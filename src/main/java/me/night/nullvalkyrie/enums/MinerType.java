package me.night.nullvalkyrie.enums;

import org.bukkit.Material;

public enum MinerType {
    DIAMOND("Diamond", Material.DIAMOND_ORE, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), EMERALD("Emerald", Material.EMERALD_ORE, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), GOLD("Gold", Material.GOLD_ORE, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), IRON("Iron", Material.IRON_ORE, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), REDSTONE("Redstone", Material.REDSTONE_ORE, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), COAL("Coal", Material.COAL_ORE, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), LAPIS("Lapis", Material.LAPIS_ORE, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), QUARTZ("Quartz", Material.NETHER_QUARTZ_ORE, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), OBSIDIAN("Obsidian", Material.OBSIDIAN, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198"), NETHERITE("Netherite", Material.ANCIENT_DEBRIS, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198");
    private final String name;
    private final Material material;
    private final String headTexture;

    MinerType(String name, Material material, String headTexture) {
        this.name = name;
        this.material = material;
        this.headTexture = headTexture;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public String getHeadTexture() {
        return headTexture;
    }

    public static MinerType getByName(String name) {
        for (MinerType type : MinerType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
