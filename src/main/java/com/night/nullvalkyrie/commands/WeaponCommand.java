package com.night.nullvalkyrie.commands;

import com.night.nullvalkyrie.Items.CustomItemManager;
import com.night.nullvalkyrie.Main;
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

import static com.night.nullvalkyrie.Items.CustomItemManager.getAllFilesFromDirectory;
import static com.night.nullvalkyrie.Items.CustomItemManager.loadConfig;

public class WeaponCommand extends Command {
    private Main main;
    public WeaponCommand(Main main) {
        super(
                "weapon",
                new String[]{},
                "Give you a weapon",
                ""

        );
        this.main = main;
    }


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        StringBuilder s = new StringBuilder();
        List<String> b = Arrays.asList(args);
        for(String a: args) {
            if(a.equals(b.get(b.size() - 1))) {
                s.append(a);
            } else {
                s.append(a);
                s.append(" ");
            }

        }
        ItemStack item = main.getCustomItemManager().getItem(s.toString());
        if(item.hasItemMeta()) {
            player.getInventory().addItem(item);
        } else {
            player.sendMessage(ChatColor.RED + "This item doesn't exist");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            List<String> hh = getAllFilesFromDirectory("ItemData");
            ArrayList<String> cc = new ArrayList<>();
            for(int kk = 0; kk < hh.size(); kk++) {
                FileConfiguration c = loadConfig("ItemData\\" + hh.get(kk));
                cc.add(c.getString("name"));
            }
            return StringUtil.copyPartialMatches(args[0], cc, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
