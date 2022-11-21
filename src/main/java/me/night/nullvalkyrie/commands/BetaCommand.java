package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BetaCommand extends Command {
    private Main main;

    public BetaCommand(Main main) {
        super("beta", new String[]{"b", "npc"}, "Beta", "");
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            NPC.createNPC(player, "&a&lNK");
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}