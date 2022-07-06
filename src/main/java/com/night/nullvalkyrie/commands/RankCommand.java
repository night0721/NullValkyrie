package com.night.nullvalkyrie.commands;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.night.nullvalkyrie.Main;
import com.night.nullvalkyrie.RankSys.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

//92.0.69.141:25565
public class RankCommand implements CommandExecutor {
    private Main main;
    public RankCommand(Main main) {
        this.main = main;
    }
    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!cooldown.asMap().containsKey(player.getUniqueId())) {if(player.isOp()) {
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
              cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 5000);
            }  else {
                long distance = cooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis();
                player.sendMessage(ChatColor.RED + "You are on a " + TimeUnit.MILLISECONDS.toSeconds(distance) + " seconds cooldown to use the command again");
            }
        }
        return false;
    }
}
