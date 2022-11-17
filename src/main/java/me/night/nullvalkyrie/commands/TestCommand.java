package me.night.nullvalkyrie.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TestCommand extends Command {
    public TestCommand() {
        super(
                "test",
                new String[]{},
                "Test",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("hello")) {
                    Player player = (Player) sender;
                    player.sendMessage(player.getAddress().getHostString());
                }
            }

        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
