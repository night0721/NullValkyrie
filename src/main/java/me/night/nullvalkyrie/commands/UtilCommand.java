package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.items.CustomItemManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UtilCommand extends Command {

    public UtilCommand() {
        super(
                "util",
                new String[]{},
                "Give you a tool",
                ""

        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        StringBuilder s = new StringBuilder();
        List<String> b = Arrays.asList(args);
        for (String a : args) {
            if (a.equals(b.get(b.size() - 1))) {
                s.append(a);
            } else {
                s.append(a);
                s.append(" ");
            }

        }
        ItemStack item = CustomItemManager.getItem(s.toString());
        if (item.hasItemMeta()) {
            player.getInventory().addItem(item);
        } else {
            player.sendMessage(ChatColor.RED + "This item doesn't exist");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> hh = CustomItemManager.getAllFilesFromDirectory("ItemData");
            ArrayList<String> cc = new ArrayList<>();
            for (String s : hh) {
                FileConfiguration c = CustomItemManager.loadConfig("ItemData\\" + s);
                if (Objects.equals(c.getString("type"), "Util")) {
                    cc.add(c.getString("name"));
                }
            }
            return StringUtil.copyPartialMatches(args[0], cc, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
