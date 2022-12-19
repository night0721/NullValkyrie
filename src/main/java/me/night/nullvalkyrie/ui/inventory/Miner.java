package me.night.nullvalkyrie.ui.inventory;

import me.night.nullvalkyrie.database.MinerDataManager;
import me.night.nullvalkyrie.entities.miners.CryptoMiner;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Miner extends GUIManager {
    public static final String title = ChatColor.DARK_AQUA + "Crypto Miners";
    @Override
    public void UI(Player player) {
        init(45, title);
        setCloseButton(true);
        setFrame(true, Material.BLUE_STAINED_GLASS_PANE);
        int[] a = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
        int counter = 0;
        for (CryptoMiner c : MinerDataManager.getMiners().values()) {
            if (counter <= 20) {
                ItemStack item = new ItemStack(c.getType());
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null) {
                    itemMeta.setDisplayName(c.getName());
                    List<String> lore = new ArrayList<>();
                    lore.add("Level: " + c.getLevel());
                    lore.add("Rate: " + c.getRate());
                    lore.add("Last Claim: " + new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(new Date(c.getLastclaim())));
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    GUI.setItem(a[counter], item);
                    counter++;
                }
            }
            player.openInventory(GUI);
        }
    }

}
