package me.night.nullvalkyrie.ui.inventory;

import me.night.nullvalkyrie.database.ShopDataManager;
import me.night.nullvalkyrie.items.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Shop {
    public static Inventory GUI;

    public void UI(Player player) {
        GUI = Bukkit.createInventory(null, 54, ChatColor.GREEN + "7-Eleven 24/7");
        ItemStack frame = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53})
            GUI.setItem(i, frame);
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closemeta = close.getItemMeta();
        if (closemeta != null) closemeta.setDisplayName(ChatColor.WHITE + "Close the menu");
        close.setItemMeta(closemeta);
        GUI.setItem(0, close);
        HashMap<String, Integer> list = ShopDataManager.getItems();
        int[] a = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43, 44};
        int counter = 0;
        for (String c : list.keySet()) {
            if (counter <= 20) {
                ItemStack item = CustomItemManager.produceItem(c).clone();
                ItemMeta itemMeta = item.getItemMeta();
                List<String> lore = itemMeta.getLore();
                lore.add("Price (BIN): " + list.get(c));
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                GUI.setItem(a[counter], item);
                counter++;
            }
        }
        player.openInventory(GUI);
    }
}
