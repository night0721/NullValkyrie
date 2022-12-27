package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.entities.npcs.NPCManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class NPCCommand extends Command {
    public NPCCommand() {
        super("npc", new String[]{}, "NPCs", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Invalid command");
                return;
            }
            if (args[0].equalsIgnoreCase("new")) {
                NPCManager.createNPC(player, args[1]);
            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> cc = List.of("new", "list");
            return StringUtil.copyPartialMatches(args[0], cc, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
