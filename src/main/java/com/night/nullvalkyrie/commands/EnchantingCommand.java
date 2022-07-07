package com.night.nullvalkyrie.commands;

        import org.bukkit.Bukkit;
        import org.bukkit.command.Command;
        import org.bukkit.command.CommandExecutor;
        import org.bukkit.command.CommandSender;
        import org.bukkit.entity.Player;
        import org.bukkit.event.inventory.InventoryType;

public class EnchantingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.openEnchanting(null, true);
        }


        return false;
    }
}