package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.entities.miners.CryptoMiner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BetaCommand extends Command {

    public BetaCommand() {
        super("beta", new String[]{"b", "npc"}, "Beta", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            // TODO:  how to make a armor stand turn
            CryptoMiner.spawn(player, args[0], "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}