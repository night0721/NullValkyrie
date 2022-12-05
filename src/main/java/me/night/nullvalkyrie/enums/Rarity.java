package me.night.nullvalkyrie.enums;

import org.bukkit.ChatColor;
public enum Rarity {
    COMMON(ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON", ChatColor.WHITE.toString()),
    UNCOMMON(net.md_5.bungee.api.ChatColor.of("#31ff09").toString() + ChatColor.BOLD + "UNCOMMON", net.md_5.bungee.api.ChatColor.of("#31ff09").toString()),
    RARE(net.md_5.bungee.api.ChatColor.of("#2f57ae").toString() + ChatColor.BOLD + "RARE", net.md_5.bungee.api.ChatColor.of("#2f57ae").toString()),
    EPIC(net.md_5.bungee.api.ChatColor.of("#b201b2").toString() + ChatColor.BOLD + "EPIC", net.md_5.bungee.api.ChatColor.of("#b201b2").toString()),
    LEGENDARY(net.md_5.bungee.api.ChatColor.of("#ffa21b").toString() + ChatColor.BOLD + "LEGENDARY", net.md_5.bungee.api.ChatColor.of("#ffa21b").toString()),
    MYTHIC(net.md_5.bungee.api.ChatColor.of("#ff23ff").toString() + ChatColor.BOLD + "MYTHIC", net.md_5.bungee.api.ChatColor.of("#ff23ff").toString()),
    ULTRA(ChatColor.RED.toString() + ChatColor.BOLD + "ULTRA", ChatColor.RED.toString()),
    GRAND(net.md_5.bungee.api.ChatColor.of("#00fdff").toString() + ChatColor.BOLD + "GRAND", net.md_5.bungee.api.ChatColor.of("#00fdff").toString());
    private final String display;
    private final String color;
    Rarity(String display, String color) {
        this.display = display;
        this.color = color;
    }
    public String getDisplay() {
        return display;
    }
    public String getColor() {
        return color;
    }
    public static Rarity getRarity(String str) {
        return switch (str) {
            case "UNCOMMON" -> UNCOMMON;
            case "RARE" -> RARE;
            case "EPIC" -> EPIC;
            case "LEGENDARY" -> LEGENDARY;
            case "MYTHIC" -> MYTHIC;
            case "ULTRA" -> ULTRA;
            case "GRAND" -> GRAND;
            default -> COMMON;
        };
    }
}
