package com.night.nullvalkyrie.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.List;

public class SpawnCommand extends Command {
    public SpawnCommand() {
        super(
                "spawn",
                new String[]{},
                "Spawn a custom mob",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Entity ent = player.getWorld().spawnEntity((player.getLocation().add(0, 2, 0)), EntityType.ZOMBIE);
        ent.setCustomName("Gay");
        ent.setCustomNameVisible(true);
        double ourHealth = 20 * 5;
        Damageable ente = (Damageable) ent;
        ente.setMaxHealth(ourHealth);
        ente.setHealth(ourHealth);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
