package me.night.nullvalkyrie.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EnchantingCommand extends Command {
    public EnchantingCommand() {
        super(
                "enchant",
                new String[]{"et", "enchanting", "enchantingtable"},
                "Open enchanting table",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.openEnchanting(player.getLocation(), true);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}