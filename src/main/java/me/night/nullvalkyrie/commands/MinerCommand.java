package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.database.MinerDataManager;
import me.night.nullvalkyrie.ui.inventory.Miner;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

import static me.night.nullvalkyrie.miners.CryptoMiner.generate;

public class MinerCommand extends Command {

    public MinerCommand() {
        super(
                "miner",
                new String[]{"m", "miners"},
                "Miner list",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                new Miner().UI(player);
                int seconds = (int) (new Date().getTime() - MinerDataManager.getLastClaim(1)) / 1000;
                generate(50, seconds);
            } else if (args[0].equalsIgnoreCase("new")) {
                String name = args[1];
                Material pick = Material.NETHERITE_AXE;
                int level = 20;
                double rate = 0.4;
                long time = System.currentTimeMillis();
                MinerDataManager.setNPC(name, pick, level, rate, true, time);
            } else if (args[0].equalsIgnoreCase("claim")) {
                String minerIndex = args[1];
                MinerDataManager.setLastClaim(Long.parseLong(minerIndex));
                player.sendMessage("Claimed");
            }

        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
