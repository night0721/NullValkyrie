package me.night.nullvalkyrie.rank;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimatedSideBar {
    private static Map<UUID, Integer> Tasks = new HashMap<>();
    private final UUID uuid;

    public AnimatedSideBar(UUID uuid) {
        this.uuid = uuid;
    }
    public void setID(int id) {
        Tasks.put(uuid, id);
    }
    public boolean hasID() {
        if (Tasks.containsKey(uuid))
            return true;
        return false;
    }
    public void stop() {
        Bukkit.getScheduler().cancelTask(Tasks.get(uuid));
    }
}
