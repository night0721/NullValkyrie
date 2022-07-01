package com.night.nullvalkyrie.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {
    private List<UUID> vanished = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player) {
            if(vanished.contains(player.getUniqueId())) {
                vanished.remove(player.getUniqueId());
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.showPlayer(player);
                }
                player.sendMessage(ChatColor.GREEN + "You are now seen by people");
            } else {
                vanished.add(player.getUniqueId());
                for (Player target: Bukkit.getOnlinePlayers()){
                    target.hidePlayer(player);
                }
                player.sendMessage(ChatColor.GREEN + "You are now vanished");
            }
        }


        return false;
    }
}
