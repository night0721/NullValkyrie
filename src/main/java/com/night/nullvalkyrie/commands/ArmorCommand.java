package com.night.nullvalkyrie.commands;

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
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(org.bukkit.Color.fromRGB(2,2,58));
        helmet.setItemMeta(helmetMeta);
        player.getInventory().addItem(helmet);

        ItemStack cp = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta cpdata =  (LeatherArmorMeta) cp.getItemMeta();
        cpdata.setColor(org.bukkit.Color.fromRGB(2,2,58));
        cp.setItemMeta(cpdata);
        player.getInventory().addItem(cp);

        ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta legdata =  (LeatherArmorMeta) leg.getItemMeta();
        legdata.setColor(org.bukkit.Color.fromRGB(2,2,58));
        leg.setItemMeta(legdata);
        player.getInventory().addItem(leg);

        ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootdata =  (LeatherArmorMeta) boot.getItemMeta();
        bootdata.setColor(org.bukkit.Color.fromRGB(2,2,58));
        boot.setItemMeta(legdata);
        player.getInventory().addItem(boot);


        return false;
    }
}
