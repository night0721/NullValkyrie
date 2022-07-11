package com.night.nullvalkyrie.commands;

import com.night.nullvalkyrie.Enchantments.EnchantmentHandler;
import com.night.nullvalkyrie.Items.CustomItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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
        System.out.println(args[0]);
        Player player = (Player) sender;
        if(args[0].equalsIgnoreCase("Snow Gun")) {
            ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
            hoe.addUnsafeEnchantment(EnchantmentHandler.ThunderBolt, 5);
            hoe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
            ItemMeta hoedata = hoe.getItemMeta();
            hoedata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "SnowGun");
            hoedata.setUnbreakable(true);
            hoedata.setLore(Arrays.asList(ChatColor.GOLD + "Shoot Snowball!"));
            hoe.setItemMeta(hoedata);

            player.getInventory().addItem(hoe);
        } else if(args[0].equalsIgnoreCase("Grenade")) {
            ItemStack egg = new ItemStack(Material.EGG);
            ItemMeta eggdata = egg.getItemMeta();
            eggdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Frag Grenade");
            eggdata.setUnbreakable(true);
            eggdata.setLore(Arrays.asList(ChatColor.GOLD + "Boom"));
            egg.setItemMeta(eggdata);
            player.getInventory().addItem(egg);
        } else if(args[0].equalsIgnoreCase("Widow Sword")) {
            player.getInventory().addItem(CustomItemManager.WidowSword);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("Snow Gun", "Grenade", "Widow Sword"), new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
