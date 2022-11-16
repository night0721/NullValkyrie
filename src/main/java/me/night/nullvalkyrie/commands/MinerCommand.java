package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.miners.MinerGUI;
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
        if(sender instanceof Player) {
            Player player = (Player) sender;
            new MinerGUI(main, player);
            int seconds = Math.round((new Date().getTime() - (long) getMiner("1").get("lastclaim")) / 1000);
            System.out.println("Seconds" + seconds);
            generate(50, seconds);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
