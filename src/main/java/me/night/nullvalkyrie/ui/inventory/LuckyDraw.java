package me.night.nullvalkyrie.ui.inventory;

import me.night.nullvalkyrie.entities.items.Items;
import me.night.nullvalkyrie.util.Util;
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
        for (String s1 : InventoryListener.randomCollection.getAll()) {
            Items it = Items.getByName(s1);
            ItemStack item = new ItemStack(it.getMaterial());
            ItemMeta meta = item.getItemMeta();
            if (meta == null) return;
            meta.setDisplayName(ChatColor.GREEN + s1);
            meta.setLore(List.of("", Util.color("&bChance:" + InventoryListener.randomCollection.getChance(s1) + "%"), it.getRarity().getDisplay()));
            item.setItemMeta(meta);
            GUI.setItem(it.getSlot(), item);
        }
        for (int s : slots) {
            if (GUI.getItem(s) == null) {
                ItemStack got = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta gotmeta = got.getItemMeta();
                gotmeta.setDisplayName(ChatColor.RED + "You already got this reward!");
                got.setItemMeta(gotmeta);
                GUI.setItem(s, got);
            }
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
