package me.night.nullvalkyrie.ui.inventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Menu extends GUIManager {
    public static final String title = ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Valkyrie Menu";
    @Override
    public void UI(Player player) {
        init(45, title);
        setCloseButton(true);
        setFrame(true, Material.BLUE_STAINED_GLASS_PANE);
        ItemStack KYS = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta KYSmeta = KYS.getItemMeta();
        if (KYSmeta == null) return;
        KYSmeta.setDisplayName(ChatColor.RED + "KILL YOURSELF WHEN???");
        KYSmeta.setLore(Arrays.asList(ChatColor.GRAY + "KYS", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
        KYS.setItemMeta(KYSmeta);
        GUI.setItem(20, KYS);

        ItemStack home = new ItemStack(Material.MAP);
        ItemMeta homemeta = home.getItemMeta();
        if (homemeta == null) return;
        homemeta.setDisplayName(ChatColor.BLUE + "Teleport to home");
        homemeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to teleport back to home", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
        home.setItemMeta(homemeta);
        GUI.setItem(22, home);

        ItemStack chest = new ItemStack(Material.ENDER_CHEST);
        ItemMeta chestmeta = chest.getItemMeta();
        if (chestmeta == null) return;
        chestmeta.setDisplayName(ChatColor.GREEN + "Open your chest");
        chestmeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to open the chest", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
        chest.setItemMeta(chestmeta);
        GUI.setItem(24, chest);

        player.openInventory(GUI);
    }
}
