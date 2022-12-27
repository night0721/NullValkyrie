package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.entities.holograms.PerPlayerHologram;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HologramCommand extends Command {
    public HologramCommand() {
        super("hologram", new String[]{}, "Spawn a hologram", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            String[] ar = new String[]{ChatColor.AQUA + "Hi", ChatColor.DARK_PURPLE + "What", ChatColor.GOLD + "Hello World", ChatColor.GOLD + ChatColor.BOLD.toString() + "CLICK"};
            new PerPlayerHologram(player, ar);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
