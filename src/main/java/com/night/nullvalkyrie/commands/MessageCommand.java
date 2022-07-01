package com.night.nullvalkyrie.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 2) {
                if(Bukkit.getPlayerExact(args[0]) != null) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    StringBuilder builder = new StringBuilder();
                    for(int i = 1; i < args.length; i++) {
                        builder.append(args[i]).append(" ");
                    }
                    player.sendMessage(ChatColor.DARK_AQUA + "TO " + ChatColor.RED + target.getName() + ChatColor.WHITE + " : " + builder);
                    target.sendMessage(ChatColor.DARK_AQUA + "FROM " + ChatColor.RED + player.getName() + ChatColor.WHITE + " : " + builder);
                } else {
                    player.sendMessage(ChatColor.RED + "You cannot send message to offline players");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid parameter, use /msg <Player> <Message>");
            }
        }

        return false;
    }
}
