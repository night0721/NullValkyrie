package me.night.nullvalkyrie.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmorCommand extends Command {

    public ArmorCommand() {
        super(
                "armor",
                new String[]{},
                "Give you a set of armor",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta helmetdata = (LeatherArmorMeta) helmet.getItemMeta();
            helmetdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Angeles Helmet");
            helmetdata.setColor(org.bukkit.Color.fromRGB(2, 2, 58));
            helmetdata.setUnbreakable(true);
            helmet.setItemMeta(helmetdata);
            player.getInventory().addItem(helmet);

            ItemStack cp = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta cpdata = (LeatherArmorMeta) cp.getItemMeta();
            cpdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Angeles Chestplate");
            cpdata.setColor(org.bukkit.Color.fromRGB(2, 2, 58));
            cpdata.setUnbreakable(true);
            cp.setItemMeta(cpdata);
            player.getInventory().addItem(cp);

            ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta legdata = (LeatherArmorMeta) leg.getItemMeta();
            legdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Angeles Leggings");
            legdata.setColor(org.bukkit.Color.fromRGB(2, 2, 58));
            legdata.setUnbreakable(true);
            leg.setItemMeta(legdata);
            player.getInventory().addItem(leg);

            ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta bootdata = (LeatherArmorMeta) boot.getItemMeta();
            bootdata.setDisplayName(net.md_5.bungee.api.ChatColor.of("#ff23ff") + "Angeles Boots");
            bootdata.setColor(org.bukkit.Color.fromRGB(2, 2, 58));
            bootdata.setUnbreakable(true);
            boot.setItemMeta(legdata);
            player.getInventory().addItem(boot);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("angeles", "widow"), new ArrayList<>());
        } else if (args.length == 2) {
            List<String> names = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                names.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[1], names, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
