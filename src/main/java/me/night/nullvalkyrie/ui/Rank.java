package me.night.nullvalkyrie.ui;

import org.bukkit.ChatColor;

public enum Rank {
    OWNER(ChatColor.DARK_RED + "<OWNER>"),
    ADMIN(ChatColor.RED + "<ADMIN>"),
    SPECIAL(ChatColor.GOLD + "<SPECIAL>"),
    ROOKIE(ChatColor.DARK_GREEN + "<ROOKIE>");

    private final String display;

    Rank(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
