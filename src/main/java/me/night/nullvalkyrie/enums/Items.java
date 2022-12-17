package me.night.nullvalkyrie.enums;

public enum Items {

    COAL("Coal", 27),
    IRON("Iron", 23),
    GOLD("Gold", 20),
    LAPIS("Lapis", 14),
    REDSTONE("Redstone", 10),
    EMERALD("Emerald", 6),
    QUARTZ("Quartz", 4),
    OBSIDIAN("Obsidian", 3),
    DIAMOND("Diamond", 2),
    NETHERITE("Netherite", 1);

    private final String name;
    private final double weight;

    Items(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
}
