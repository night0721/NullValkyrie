package me.night.nullvalkyrie.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.List;

public class HologramCommand extends Command {
    public HologramCommand() {
        super("hologram", new String[]{}, "Spawn a hologram", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            String[] ar = new String[]{ChatColor.AQUA + "Hi", ChatColor.DARK_PURPLE + "What", ChatColor.GOLD + "Hello World"};
            Location location = player.getLocation();
            for (String line : ar) {
                ArmorStand stand = location.getWorld().spawn(location.subtract(0, 0.3, 0), ArmorStand.class, armorStand -> {
                    armorStand.setVisible(false);
                });
                stand.setGravity(false);
                stand.setInvulnerable(true);
                stand.setCustomNameVisible(true);
                stand.setCustomName(line);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
