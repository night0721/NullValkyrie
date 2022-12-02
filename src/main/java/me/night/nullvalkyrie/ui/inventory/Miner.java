package me.night.nullvalkyrie.ui.inventory;

import me.night.nullvalkyrie.database.MinerDataManager;
import me.night.nullvalkyrie.miners.CryptoMiner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Miner {
    public static Inventory GUI;

    public void UI(Player player) {
        GUI = Bukkit.createInventory(null, 45, ChatColor.DARK_AQUA + "Crypto Miners");
        ItemStack frame = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}) {
            GUI.setItem(i, frame);
        }
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closemeta = close.getItemMeta();
        if (closemeta != null) closemeta.setDisplayName(ChatColor.WHITE + "Close the menu");
        close.setItemMeta(closemeta);
        GUI.setItem(0, close);
        int[] a = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
        int counter = 0;
        for (CryptoMiner c : MinerDataManager.getMiners().values()) {
            if (counter <= 20) {
                ItemStack item = new ItemStack(c.getType());
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(c.getName());
                List<String> lore = new ArrayList<>();
                lore.add("Level: " + c.getLevel());
                lore.add("Rate: " + c.getRate());
                lore.add("Last Claim: " + new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(new Date(c.getLastclaim())));
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                GUI.setItem(a[counter], item);
                counter++;
            }
            player.openInventory(GUI);
        }
    }

}