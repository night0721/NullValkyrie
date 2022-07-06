package com.night.nullvalkyrie.commands;

import com.night.nullvalkyrie.Main;
import com.night.nullvalkyrie.RankSys.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//92.0.69.141:25565
public class RankCommand implements CommandExecutor {
    private Main main;
    public RankCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.isOp()) {
                if(args.length == 2) {
                    if(Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        for(Rank rank : Rank.values()) {
                            if(rank.name().equalsIgnoreCase(args[1])) {
                                main.getRankManager().setRank(target.getUniqueId(), rank);
                                player.sendMessage(ChatColor.GREEN + "You changed " + target.getName() + "'s rank to " + rank.getDisplay());
                                if(target.isOnline()) {
                                    target.getPlayer().sendMessage(ChatColor.GREEN + player.getName() + " set your rank to " + rank.getDisplay());
                                }
                                return true;
                            }
                        }
                        player.sendMessage(ChatColor.RED + "Invalid Rank, please specify a valid rank, ROOKIE, SPECIAL, ADMIN, OWNER");
                    } else {
                        player.sendMessage("This player has never played in this server before!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid parameter, use /rank <Player> <Rank>");
                }
            } else {
                player.sendMessage(ChatColor.RED + "You must be server operator to use this command");
            }
        }
        return false;
    }
}
