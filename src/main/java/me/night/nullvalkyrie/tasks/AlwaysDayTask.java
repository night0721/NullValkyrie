package me.night.nullvalkyrie.tasks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class AlwaysDayTask extends BukkitRunnable {
    @Override
    public void run() {
        World world = Bukkit.getServer().getWorld("world");
        if (world != null) world.setTime(0L);
    }
}
