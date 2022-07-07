package com.night.nullvalkyrie.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class MenuCommand extends Command {
    public static Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Valkyrie Menu");

    public MenuCommand() {
        super(
                "menu",
                new String[]{"m"},
                "Open the menu",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            inv = Bukkit.createInventory(player,45, ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Valkyrie Menu");

            ItemStack KYS = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta KYSmeta = KYS.getItemMeta();
            KYSmeta.setDisplayName(ChatColor.RED + "KILL YOURSELF WHEN???");
            KYSmeta.setLore(Arrays.asList(ChatColor.GRAY + "KYS", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
            KYS.setItemMeta(KYSmeta);
            inv.setItem(20, KYS);

            ItemStack home = new ItemStack(Material.MAP);
            ItemMeta homemeta = home.getItemMeta();
            homemeta.setDisplayName(ChatColor.BLUE + "Teleport to home");
            homemeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to teleport back to home", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
            home.setItemMeta(homemeta);
            inv.setItem(22, home);

            ItemStack chest = new ItemStack(Material.ENDER_CHEST);
            ItemMeta chestmeta = chest.getItemMeta();
            chestmeta.setDisplayName(ChatColor.GREEN + "Open your chest");
            chestmeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to open the chest", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
            chest.setItemMeta(chestmeta);
            inv.setItem(24, chest);

            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closemeta = close.getItemMeta();
            closemeta.setDisplayName(ChatColor.WHITE + "Close the menu");
            closemeta.setLore(Arrays.asList(ChatColor.GRAY + "Close the menu", ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON"));
            close.setItemMeta(closemeta);
            inv.setItem(0, close);

            ItemStack frame = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
            for(int i : new int[]{1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44}) {
                inv.setItem(i, frame);
            }

            player.openInventory(inv);

        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
