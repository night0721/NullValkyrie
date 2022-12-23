package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.entities.pets.ZombiePet;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.entity.Player;

import java.util.List;

public class BetaCommand extends Command {

    public BetaCommand() {
        super("beta", new String[]{"b"}, "Beta", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            ZombiePet a = new ZombiePet(player.getLocation(), player);
            ((CraftWorld) player.getWorld()).getHandle().addFreshEntity(a);
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}