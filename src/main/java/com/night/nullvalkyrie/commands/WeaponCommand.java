package com.night.nullvalkyrie.commands;

import com.night.nullvalkyrie.Items.CustomItemManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeaponCommand extends Command {

    public WeaponCommand() {
        super(
                "weapon",
                new String[]{},
                "Give you a weapon",
                ""

        );
    }


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        List<String> ar = new ArrayList<>();
        ar.addAll(Arrays.asList(args));
        String name = String.join(" ", ar.remove(0));
        Player player = (Player) sender;
        if(name.equalsIgnoreCase("Snow Gun")) {
            player.getInventory().addItem(CustomItemManager.SnowGun);
        } else if(name.equalsIgnoreCase("Grenade")) {
            player.getInventory().addItem(CustomItemManager.Grenade);
        } else if(name.equalsIgnoreCase("Widow Sword")) {
            player.getInventory().addItem(CustomItemManager.WidowSword);
        } else if(name.equalsIgnoreCase("Terminator")) {
            player.getInventory().addItem(CustomItemManager.Terminator);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("Snow Gun", "Grenade", "Widow Sword", "Terminator"), new ArrayList<>());
        }
        return new ArrayList<>();
    }
}