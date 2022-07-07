package com.night.nullvalkyrie.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class GunCommand extends Command {

    public GunCommand() {
        super(
                "gun",
                new String[]{},
                "Give you a gun",
                ""

        );
    }


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta hoedata = hoe.getItemMeta();
        hoedata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun");
        hoedata.setUnbreakable(true);
        hoedata.setLore(Arrays.asList(ChatColor.GOLD + "Shoot Snowball!"));
        hoe.setItemMeta(hoedata);
        player.getInventory().addItem(hoe);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
