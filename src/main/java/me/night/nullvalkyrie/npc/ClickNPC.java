package me.night.nullvalkyrie.npc;

import me.night.nullvalkyrie.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClickNPC implements Listener {
    @EventHandler
    public void onClick(RightClickNPC e) {
        Player player = e.getPlayer();
        if (e.getNPC().getBukkitEntity().getName().equalsIgnoreCase(Util.color("&a&lNK"))) {
            player.sendMessage(Util.color("Hi"));
        }
    }
}
