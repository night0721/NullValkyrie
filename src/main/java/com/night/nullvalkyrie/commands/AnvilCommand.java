package com.night.nullvalkyrie.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public class AnvilCommand extends Command {

    public AnvilCommand() {
        super(
                "anvil",
                new String[]{"av"},
                "Open anvil",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(Bukkit.createInventory(player, InventoryType.ANVIL));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}