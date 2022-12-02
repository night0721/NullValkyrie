package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.ui.inventory.Menu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MenuCommand extends Command {
    public MenuCommand() {
        super("menu", new String[]{"m"}, "Open the menu", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            new Menu().UI((Player) sender);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
