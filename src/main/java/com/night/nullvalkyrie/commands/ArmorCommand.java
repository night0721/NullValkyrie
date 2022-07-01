package com.night.nullvalkyrie.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetdata = (LeatherArmorMeta) helmet.getItemMeta();
        helmetdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Angeles Helmet");
        helmetdata.setColor(org.bukkit.Color.fromRGB(2,2,58));
        helmetdata.setUnbreakable(true);
        helmet.setItemMeta(helmetdata);
        player.getInventory().addItem(helmet);

        ItemStack cp = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta cpdata =  (LeatherArmorMeta) cp.getItemMeta();
        cpdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Angeles Chestplate");
        cpdata.setColor(org.bukkit.Color.fromRGB(2,2,58));
        cpdata.setUnbreakable(true);
        cp.setItemMeta(cpdata);
        player.getInventory().addItem(cp);

        ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta legdata =  (LeatherArmorMeta) leg.getItemMeta();
        legdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Angeles Leggings");
        legdata.setColor(org.bukkit.Color.fromRGB(2,2,58));
        legdata.setUnbreakable(true);
        leg.setItemMeta(legdata);
        player.getInventory().addItem(leg);

        ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootdata =  (LeatherArmorMeta) boot.getItemMeta();
        bootdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Angeles Boots");
        bootdata.setColor(org.bukkit.Color.fromRGB(2,2,58));
        bootdata.setUnbreakable(true);
        boot.setItemMeta(legdata);
        player.getInventory().addItem(boot);


        return false;
    }
}
