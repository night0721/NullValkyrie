package com.night.nullvalkyrie.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class HologramCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            String[] ar = new String[]{
                    ChatColor.AQUA + "Hi",
                    ChatColor.DARK_PURPLE + "What",
                    ChatColor.GOLD + "Hello World"
            };
            Location location = player.getLocation();
            for(String line: ar) {
                ArmorStand stand = location.getWorld().spawn(location.subtract(0,0.3,0), ArmorStand.class, armorStand -> {
                    armorStand.setVisible(false);
                });
                stand.setGravity(false);
                stand.setInvulnerable(true);
                stand.setCustomNameVisible(true);
                stand.setCustomName(line);
            }
        }

        return false;
    }
}
