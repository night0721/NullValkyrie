package me.night.nullvalkyrie.miners;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.Items.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinerGUI {
    private Main main;
    private FileConfiguration file;
    private Inventory inv;
    public MinerGUI(Main main, Player player) {
        this.main = main;
        if(!main.getDataFolder().exists()) main.getDataFolder().mkdir();
        file = CustomItemManager.loadConfig("miners.yml");
        createUI();
        player.openInventory(inv);
    }
    public void createUI() {
        inv = Bukkit.createInventory(null,45, ChatColor.DARK_AQUA + "Crypto Miners");
        ItemStack frame = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        for(int i : new int[]{1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44}) {
            inv.setItem(i, frame);
        }
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closemeta = close.getItemMeta();
        closemeta.setDisplayName(ChatColor.WHITE + "Close the menu");
        closemeta.setLore(Arrays.asList(ChatColor.GRAY + "Close the menu"));
        close.setItemMeta(closemeta);
        inv.setItem(0, close);
        int[] a = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34};
        int ind = 0;
        for(String c : file.getKeys(false)) {
            ItemStack item = new ItemStack(Material.matchMaterial(file.getString(c + ".material")));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(file.getString(c + ".name"));
            List<String> lore = new ArrayList<>();
            lore.add("Level: " + file.getString(c + ".level"));
            lore.add("Rate: " + file.getString(c + ".rate"));
            boolean b = file.getBoolean(c + ".rate") ? lore.add(ChatColor.GRAY + "Click to enable miner!") : lore.add(ChatColor.RED + "Click to disable miner!");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inv.setItem(a[ind], item);
            ind++;
        }
    }

}
