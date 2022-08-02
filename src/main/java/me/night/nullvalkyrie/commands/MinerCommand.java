package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.miners.MinerGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.night.nullvalkyrie.miners.*;

import java.util.List;

public class MinerCommand extends Command {
    private Main main;
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
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
