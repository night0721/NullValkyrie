package com.night.nullvalkyrie.commands;

import com.night.nullvalkyrie.Items.CustomItem;
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
        List<String> arg = Arrays.asList(args);
        //arg.remove(0); //in case buggy bukkit make it bug again
        String name = String.join(" ", arg);
        Player player = (Player) sender;
        if(name.equalsIgnoreCase("Snow Gun")) {
            player.getInventory().addItem(CustomItem.SnowGun);
        } else if(name.equalsIgnoreCase("Grenade")) {
            player.getInventory().addItem(CustomItem.Grenade);
        } else if(name.equalsIgnoreCase("Widow Sword")) {
            player.getInventory().addItem(CustomItem.WidowSword);
        } else if(name.equalsIgnoreCase("Terminator")) {
            player.getInventory().addItem(CustomItem.Terminator);
        } else if(name.equalsIgnoreCase("Explosive Bow")) {
            player.getInventory().addItem(CustomItem.ExplosiveBow);
        } else if(name.equalsIgnoreCase("AOTV")) {
            player.getInventory().addItem(CustomItem.AOTV);
        } else if(name.equalsIgnoreCase("Grappling Hook")) {
            player.getInventory().addItem(CustomItem.GrapplingHook);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("Snow Gun", "Grenade", "Widow Sword", "Terminator", "Explosive Bow", "AOTV", "Grappling Hook"), new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
