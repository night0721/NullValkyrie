package me.night.nullvalkyrie.ui.inventory;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().equals(Menu.GUI) && e.getCurrentItem() != null) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()) {
                case 0:
                    break;
                case 20:
                    player.setHealth(0);
                    player.sendMessage(ChatColor.RED + "又做兵 抵死");
                    break;
                case 22:
                    player.teleport(player.getWorld().getSpawnLocation());
                    break;
                case 24:
                    player.closeInventory();
                    player.openInventory(Shop.GUI);
                    return;
                default:
                    return;
            }
            player.closeInventory();
        }
        if (e.getInventory().equals(Shop.GUI) && e.getCurrentItem() != null) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getRawSlot() == 0) {
                player.closeInventory();
            }
        }
    }
}
