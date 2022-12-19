package me.night.nullvalkyrie.ui.inventory;

import me.night.nullvalkyrie.database.ShopDataManager;
import me.night.nullvalkyrie.entities.items.CustomItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shop extends GUIManager {
    public static final String title = ChatColor.GREEN + "7-Eleven 24/7";
    @Override
    public void UI(Player player) {
        init(54, title);
        setCloseButton(true);
        setFrame(true, Material.GREEN_STAINED_GLASS_PANE);
        HashMap<String, Integer> list = ShopDataManager.getItems();
        int[] a = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43, 44};
        int counter = 0;
        for (String c : list.keySet()) {
            if (counter <= 20) {
                ItemStack item = CustomItemManager.produceItem(c).clone();
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta == null) return;
                List<String> lore = itemMeta.getLore() == null ? new ArrayList<>() : itemMeta.getLore();
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
