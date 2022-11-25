package me.night.nullvalkyrie.ui;

import me.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static me.night.nullvalkyrie.ui.ScoreboardListener.nameTagManager;

public class RankManager {
    private final File file;
    private final YamlConfiguration config;
    public RankManager(Main main) {
        if(!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }
        file = new File(main.getDataFolder(), "ranks.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
    public void setRank(UUID uuid, Rank rank)  {
        config.set(uuid.toString(), rank.name());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.hasPlayedBefore()) {
                nameTagManager.removeTag(player);
                nameTagManager.newTag(player);
            }
        }
    }
    public Rank getRank(UUID uuid) {
        return Rank.valueOf(config.getString(uuid.toString()));
    }
}
