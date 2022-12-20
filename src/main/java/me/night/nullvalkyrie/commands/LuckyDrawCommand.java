package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.ui.inventory.LuckyDraw;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LuckyDrawCommand extends Command {
    public LuckyDrawCommand() {
        super("luckydraw", new String[]{"ld"}, "Generate a lucky draw", "");
    }
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            new LuckyDraw().UI(player);
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
