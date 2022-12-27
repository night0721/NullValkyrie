package me.night.nullvalkyrie.entities.miners;

import org.bukkit.Material;
public enum MinerType {
    DIAMOND("Diamond", Material.DIAMOND_ORE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM4Y2E2NjRmMDZkNDE0NjUwYWFjNWNkNDgyYzNiMGM3OWE2NjFiNWYxMWRjODUyMTQyNWJhNmU1NjllNSJ9fX0="),
    EMERALD("Emerald", Material.EMERALD_ORE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjRmNzNlMmVjZGMxYTM5MzlmYzEyZWY1N2Q3MTA0YTcwYzAyOGM1Y2ZiNzMyNGQ2OGVkM2IxZTIwYTkxMGEzIn19fQ=="),
    GOLD("Gold", Material.GOLD_ORE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDcxNDlmYjllNjM4NTI2OGY1MTk4N2IyZGU1MjVmNTkwNTFiNjE3Njc2Mjc2ZDI1YzZiMjQ1Y2E4ZDRkNCJ9fX0="),
    IRON("Iron", Material.IRON_ORE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMzMTc1M2QzNDQwYjJhN2QxMTVmNmE3NTQyY2Q0YjI0ODdjYjdhZGEwYzk0NWJlNGQ0ZGY2MmJiMzlkNDA2NyJ9fX0="),
    REDSTONE("Redstone", Material.REDSTONE_ORE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU1YjcwZWEyM2VmNjFkNjE4N2U1NTgzMTI4MTU2ZTE2MDg2YzIyODM4YjQ4YTc2YTk5NWZiZjk4ZjFlYjhhIn19fQ=="),
    COAL("Coal", Material.COAL_ORE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM2ZDBkYWQ4NmRjZDNkN2JjNDIxYTNjMjIyYWJhMzQxNDRjOTc5MWRkMmZjYTZiNzZlMzYwMmE2ZTM2In19fQ=="),
    LAPIS("Lapis", Material.LAPIS_ORE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI4M2QyMjkzYTVjYjVlOGE5YTY4M2JiOWYxYzI2NzAzZjdjMDNmMzE0Mzk2MTEzYjA3MzQ5Njk5YTNjOGIifX19"),
    QUARTZ("Quartz", Material.NETHER_QUARTZ_ORE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ1MjVhNTE2YTg3YTMxNTdlMTE2MzlhZWJiYzRjMmRmMmRiMTFkNTNjZWQ3NjgxMzQ0MzA0NmVkM2U1YiJ9fX0="),
    OBSIDIAN("Obsidian", Material.OBSIDIAN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDY1YmIxOTJhNjU2YmQ2ZGVjOGQ4YzhlNGRiM2I1NzNjZjcxNjliYjczOTM5MTZlZjlhYzE5ZGExNjNhZGE1In19fQ=="),
    NETHERITE("Netherite", Material.ANCIENT_DEBRIS, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM1NzQ0NGFkZTY0ZWM2Y2VhNjQ1ZWM1N2U3NzU4NjRkNjdjNWZhNjIyOTk3ODZlMDM3OTkzMTdlZTRhZCJ9fX0=");

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
