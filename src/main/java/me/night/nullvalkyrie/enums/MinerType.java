package me.night.nullvalkyrie.enums;

public enum MinerType {
    DIAMOND("Diamond"), EMERALD("Emerald"), GOLD("Gold"), IRON("Iron"), REDSTONE("Redstone"), COAL("Coal"), LAPIS("Lapis"), QUARTZ("Quartz"), OBSIDIAN("Obsidian"), NETHERITE("Netherite");
    private final String name;
    MinerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
