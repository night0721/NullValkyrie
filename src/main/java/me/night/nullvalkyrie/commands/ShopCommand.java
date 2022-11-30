package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.database.ShopDataManager;
import me.night.nullvalkyrie.items.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class ShopCommand extends Command {
    public ShopCommand() {
        super("7elven",
                new String[]{"711", "seven", "7ven"},
                "Shop",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.GREEN + "7-Eleven 24/7");
        int counter = 0;
        HashMap<String, Integer> list = ShopDataManager.getItems();
        for (String c : list.keySet()) {
            ItemStack item = CustomItemManager.produceItem(c).clone();
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = itemMeta.getLore();
            lore.add("Price (BIN): " + list.get(c));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inv.setItem(counter, item);
            counter++;
        }
        Player player = (Player) sender;
        player.openInventory(inv);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
