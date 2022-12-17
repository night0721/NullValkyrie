package me.night.nullvalkyrie.ui.inventory;

import me.night.nullvalkyrie.enums.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LuckyDraw extends GUIManager {
    public static final String title = ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "X Lucky Draw";
    @Override
    public void UI(Player player) {
        init(45, title);
        setCloseButton(true);
        setFrame(true, Material.BLUE_STAINED_GLASS_PANE);
        int[] slots = new int[]{10, 12, 14, 16, 28, 30, 32, 34, 19, 25};
        int count = 0;
        for (Items name : Items.values()) {
            ItemStack item = new ItemStack(name.getMaterial());
            ItemMeta meta = item.getItemMeta();
            if (meta == null) return;
            meta.setDisplayName(ChatColor.GOLD + name.getName());
            meta.setLore(List.of(name.getRarity().getDisplay()));
            item.setItemMeta(meta);
            GUI.setItem(slots[count], item);
            count++;
        }
        ItemStack roll = new ItemStack(Material.ARROW);
        ItemMeta meta = roll.getItemMeta();
        if (meta == null) return;
        meta.setLore(List.of("Press to roll!"));
        roll.setItemMeta(meta);
        GUI.setItem(22, roll);
        player.openInventory(GUI);
    }
}
