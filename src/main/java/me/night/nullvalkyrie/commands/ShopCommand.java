package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.ui.inventory.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopCommand extends Command {
    public ShopCommand() {
        super("7elven", new String[]{"711", "seven", "7ven"}, "Shop", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        new Shop().UI((Player) sender);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
