package me.night.nullvalkyrie.commands;

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

//            CryptoMiner miner = new CryptoMiner(args[0], Material.DIAMOND_ORE, 1, 0.5, System.currentTimeMillis());
//            miner.spawn(player, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198");
//            new LuckyDraw().UI(player);
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}