package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.items.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopCommand extends Command {
    private FileConfiguration file;
    private Inventory inv;

    public ShopCommand() {
        super("7elven",
                new String[]{"711", "seven", "7ven"},
                "Shop",
                ""
        );
        file = CustomItemManager.loadConfig("miners.yml");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        inv = Bukkit.createInventory(null, 45, ChatColor.GREEN + "7-Eleven 24/7");
        for (String c : file.getKeys(false)) {
            ItemStack item = new ItemStack(Material.matchMaterial(file.getString(c + ".material")));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(file.getString(c + ".name"));
            List<String> lore = new ArrayList<>();
            lore.add("Price (BIN): " + file.getString(c + ".price"));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);

        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
