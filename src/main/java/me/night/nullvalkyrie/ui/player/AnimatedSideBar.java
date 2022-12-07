package me.night.nullvalkyrie.ui.player;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimatedSideBar {
    private static final Map<UUID, Integer> Tasks = new HashMap<>();
    private final UUID uuid;

    public AnimatedSideBar(UUID uuid) {
        this.uuid = uuid;
    }

    public void setID(int id) {
        Tasks.put(uuid, id);
    }

    public boolean hasID() {
        return Tasks.containsKey(uuid);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(Tasks.get(uuid));
    }
}
