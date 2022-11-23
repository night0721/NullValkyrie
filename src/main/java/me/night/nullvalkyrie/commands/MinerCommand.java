package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.miners.CryptoMiner;
import me.night.nullvalkyrie.miners.MinerGUI;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

import static me.night.nullvalkyrie.miners.CryptoMiner.generate;
import static me.night.nullvalkyrie.miners.CryptoMiner.getMiner;

public class MinerCommand extends Command {
    private final Main main;

    public MinerCommand(Main main) {
        super(
                "miner",
                new String[]{"m", "miners"},
                "Miner list",
                ""
        );
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if(args.length == 0) {
                Player player = (Player) sender;
                new MinerGUI(main, player);
                int seconds = (int) (new Date().getTime() - getMiner("0").getLastclaim()) / 1000;
                System.out.println("Seconds" + seconds);
                generate(50, seconds);
            } else if (args[0].equalsIgnoreCase("new")) {
                String name = args[1];
                Material pick = Material.STONE_PICKAXE;
                int level = 20;
                double rate = 0.4;
                long time = System.currentTimeMillis();
                CryptoMiner miner = new CryptoMiner(main, name, pick, level, rate, time);
                miner.setMiner(name, pick.name(), level, rate, time);
            } else if (args[0].equalsIgnoreCase("claim")) {
                String minerIndex = args[1];
                CryptoMiner miner = getMiner(minerIndex);
                miner.setLastClaim(minerIndex, System.currentTimeMillis());
                System.out.println("Done");
            }

        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
