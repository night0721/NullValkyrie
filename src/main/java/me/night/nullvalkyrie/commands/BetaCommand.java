package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.entities.miners.CryptoMiner;
import me.night.nullvalkyrie.enums.Items;
import me.night.nullvalkyrie.util.RandomCollection;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BetaCommand extends Command {
    RandomCollection<String> randomCollection;

    public BetaCommand() {
        super("beta", new String[]{"b", "npc"}, "Beta", "");
        randomCollection = new RandomCollection<>();
        for (Items e : Items.values()) {
            randomCollection.add(e.getWeight(), e.getName());
        }
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
//            String s = randomCollection.getRandom();
//            if (s == null) System.out.println("You have got all rewards from the draw");
//            System.out.println(s + " with the probability " + Math.round(randomCollection.getChance(s)));
//            randomCollection.remove(s);
            CryptoMiner miner = new CryptoMiner(args[0], Material.DIAMOND_ORE, 1, 0.5, System.currentTimeMillis());
            miner.spawn(player, "https://textures.minecraft.net/texture/c09cc3c75bd13c59602040b5970f30dbc76825c0e817da815a65d76ab0e82198");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}