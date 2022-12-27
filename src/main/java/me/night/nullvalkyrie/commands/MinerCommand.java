package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.database.MinerDataManager;
import me.night.nullvalkyrie.entities.miners.CryptoMiner;
import me.night.nullvalkyrie.enums.MinerType;
import me.night.nullvalkyrie.ui.inventory.Miner;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;


public class MinerCommand extends Command {

    public MinerCommand() {
        super("miner", new String[]{"miners"}, "Miners", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Invalid command");
                return;
            }
            if (args[0].equalsIgnoreCase("list")) {
                new Miner().UI(player);
            } else if (args[0].equalsIgnoreCase("new")) {
                String name = args[2];
                MinerType type = MinerType.getByName(args[1]);
                int level = 20;
                double rate = 0.4;
                long time = System.currentTimeMillis();
                assert type != null;
                MinerDataManager.setMiner(name, type, level, rate, true, time, player.getLocation());
                CryptoMiner miner = new CryptoMiner(name, type, level, rate, time, player.getLocation());
                miner.spawn(player);
            } else if (args[0].equalsIgnoreCase("claim")) {
                MinerDataManager.setLastClaim(args[1]);
                player.sendMessage(ChatColor.GREEN + "Claimed");
                int seconds = (int) (new Date().getTime() - MinerDataManager.getLastClaim(1)) / 1000;
                CryptoMiner.generate(50, seconds);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> cc = List.of("new", "claim", "list");
            return StringUtil.copyPartialMatches(args[0], cc, new ArrayList<>());
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("new")) {
                List<String> choices = new ArrayList<>();
                for (MinerType type : MinerType.values()) {
                    choices.add(type.getName());
                }
                return StringUtil.copyPartialMatches(args[1], choices, new ArrayList<>());
            } else if (args[0].equalsIgnoreCase("claim")) {
                List<String> choices = new ArrayList<>();
                for (CryptoMiner miner : MinerDataManager.getMiners().values()) {
                    choices.add(miner.getName());
                }
                return StringUtil.copyPartialMatches(args[1], choices, new ArrayList<>());
            }
        }
        return new ArrayList<>();
    }
}
