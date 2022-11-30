package me.night.nullvalkyrie.util;

import org.bukkit.ChatColor;

public class Util {
    public static String centerText(String text, int lineLength) {
        StringBuilder builder = new StringBuilder();
        char space = ' ';
        int distance = (lineLength - text.length()) / 2;
        String repeat = String.valueOf(space).repeat(Math.max(0, distance));
        builder.append(repeat);
        builder.append(text);
        builder.append(repeat);
        return builder.toString();
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
