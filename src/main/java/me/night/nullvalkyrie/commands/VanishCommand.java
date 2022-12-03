package me.night.nullvalkyrie.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishCommand extends Command {
    private final List<UUID> vanished = new ArrayList<>();

    public VanishCommand() {
        super("vanish", new String[]{}, "Turn yourself into invisible", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (vanished.contains(player.getUniqueId())) {
                vanished.remove(player.getUniqueId());
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.showPlayer(player);
                }
                player.sendMessage(ChatColor.GREEN + "You are now seen by people");
            } else {
                vanished.add(player.getUniqueId());
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.hidePlayer(player);
                }
                player.sendMessage(ChatColor.GREEN + "You are now vanished");
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
