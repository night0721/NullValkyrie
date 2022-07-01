package com.night.nullvalkyrie.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

public class GunCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta hoedata = hoe.getItemMeta();
        hoedata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun");
        hoedata.setUnbreakable(true);
        hoedata.setLore(Arrays.asList(ChatColor.GOLD + "Shoot Snowball!"));
        hoe.setItemMeta(hoedata);
        player.getInventory().addItem(hoe);
        return false;
    }
}
