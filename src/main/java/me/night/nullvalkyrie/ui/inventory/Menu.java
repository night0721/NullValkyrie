package me.night.nullvalkyrie.ui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Menu {
    public static Inventory GUI;

    public void UI(Player player) {
        GUI = Bukkit.createInventory(player, 45, ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Valkyrie Menu");

        ItemStack KYS = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta KYSmeta = KYS.getItemMeta();
        KYSmeta.setDisplayName(ChatColor.RED + "KILL YOURSELF WHEN???");
        KYSmeta.setLore(Arrays.asList(ChatColor.GRAY + "KYS", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
        KYS.setItemMeta(KYSmeta);
        GUI.setItem(20, KYS);

        ItemStack home = new ItemStack(Material.MAP);
        ItemMeta homemeta = home.getItemMeta();
        homemeta.setDisplayName(ChatColor.BLUE + "Teleport to home");
        homemeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to teleport back to home", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
        home.setItemMeta(homemeta);
        GUI.setItem(22, home);

        ItemStack chest = new ItemStack(Material.ENDER_CHEST);
        ItemMeta chestmeta = chest.getItemMeta();
        chestmeta.setDisplayName(ChatColor.GREEN + "Open your chest");
        chestmeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to open the chest", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
        chest.setItemMeta(chestmeta);
        GUI.setItem(24, chest);

        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closemeta = close.getItemMeta();
        closemeta.setDisplayName(ChatColor.WHITE + "Close the menu");
        closemeta.setLore(Arrays.asList(ChatColor.GRAY + "Close the menu", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
        close.setItemMeta(closemeta);
        GUI.setItem(0, close);

        ItemStack frame = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44})
            GUI.setItem(i, frame);

        player.openInventory(GUI);
    }
}
