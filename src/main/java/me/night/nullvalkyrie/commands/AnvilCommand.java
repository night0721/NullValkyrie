package me.night.nullvalkyrie.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class AnvilCommand extends Command {

    public AnvilCommand() {
        super("anvil", new String[]{"av"}, "Open anvil", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            Inventory av = Bukkit.createInventory(null, InventoryType.ANVIL, "AV");
            player.openInventory(av);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}