package me.night.nullvalkyrie.ui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class GUIManager {
    public static Inventory GUI;
    private boolean close;

    public abstract void UI(Player player);

    public void init(int size, String title) {
        GUI = Bukkit.createInventory(null, size, title);
    }

    public void setCloseButton(boolean boo) {
        if (boo) {
            close = true;
            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closemeta = close.getItemMeta();
            if (closemeta != null) closemeta.setDisplayName(ChatColor.WHITE + "Close the menu");
            close.setItemMeta(closemeta);
            GUI.setItem(0, close);
        } else close = false;
    }

    public void setFrame(boolean boo, Material... frame) {
        if (boo) {
            ItemStack frames = new ItemStack(frame[0]);
            switch (GUI.getSize()) {
                case 27 -> {
                    if (close) {
                        for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26})
                            GUI.setItem(i, frames);
                    } else {
                        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26})
                            GUI.setItem(i, frames);
                    }
                }
                case 36 -> {
                    if (close) {
                        for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35})
                            GUI.setItem(i, frames);
                    } else {
                        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35})
                            GUI.setItem(i, frames);
                    }
                }
                case 45 -> {
                    if (close) {
                        for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44})
                            GUI.setItem(i, frames);
                    } else {
                        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44})
                            GUI.setItem(i, frames);
                    }
                }
                case 54 -> {
                    if (close) {
                        for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51 ,52, 53})
                            GUI.setItem(i, frames);
                    } else {
                        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51 ,52, 53})
                            GUI.setItem(i, frames);
                    }
                }
            }
        }
    }
}
