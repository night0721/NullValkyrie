package com.night.nullvalkyrie.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeaponCommand extends Command {

    public WeaponCommand() {
        super(
                "weapon",
                new String[]{},
                "Give you a weapon",
                ""

        );
    }


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args[0].equalsIgnoreCase("snowgun")) {
            ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
            ItemMeta hoedata = hoe.getItemMeta();
            hoedata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun");
            hoedata.setUnbreakable(true);
            hoedata.setLore(Arrays.asList(ChatColor.GOLD + "Shoot Snowball!"));
            hoe.setItemMeta(hoedata);
            player.getInventory().addItem(hoe);
        } else if(args[0].equalsIgnoreCase("grenade")) {
            ItemStack egg = new ItemStack(Material.EGG);
            ItemMeta eggdata = egg.getItemMeta();
            eggdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Frag Grenade");
            eggdata.setUnbreakable(true);
            eggdata.setLore(Arrays.asList(ChatColor.GOLD + "Boom"));
            egg.setItemMeta(eggdata);
            player.getInventory().addItem(egg);
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("snowgun", "grenade"), new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
