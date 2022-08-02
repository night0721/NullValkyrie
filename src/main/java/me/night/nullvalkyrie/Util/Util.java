package me.night.nullvalkyrie.Util;

public class Util {
    public static String centerText(String text, int lineLength) {
        StringBuilder builder = new StringBuilder();
        char space = ' ';
        int distance = (lineLength - text.length()) / 2;
        for (int ii = 0; ii < distance; ii++) {
            builder.append(space);
        }
        builder.append(text);
        for (int i = 0; i < distance; ++i) {
            builder.append(space);
        }
        return builder.toString();
    }
}
