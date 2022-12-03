package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.database.UserDataManager;
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
            UserDataManager.updateUserBank(player.getUniqueId().toString(), 10);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}