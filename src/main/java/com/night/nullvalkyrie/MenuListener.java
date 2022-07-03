package com.night.nullvalkyrie;

import com.night.nullvalkyrie.commands.MenuCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getInventory().equals(MenuCommand.inv) && e.getCurrentItem() != null) {
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
                    break;
                case 24:
                    player.closeInventory();
                    player.openInventory(WitherChest.witherchest);
                    return;
                default:
                    return;
            }


            player.closeInventory();
        }
    }
}
