package me.night.nullvalkyrie.commands;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CraftCommand extends Command {

    public CraftCommand() {
        super(
                "craft",
                new String[]{"ct", "crafting", "craftingtable"},
                "Open crafting table",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.openWorkbench(null, true);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
