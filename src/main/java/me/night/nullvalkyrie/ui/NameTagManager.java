package me.night.nullvalkyrie.ui;

import me.night.nullvalkyrie.database.RankDataManager;
import me.night.nullvalkyrie.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class NameTagManager {
    public void setNametags(Player player) {
        Scoreboard newScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = newScoreboard.registerNewObjective("TabList", Criteria.DUMMY, "Test");
        obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        player.setScoreboard(newScoreboard);
        for (Rank rank : Rank.values()) {
            Team team = player.getScoreboard().registerNewTeam(rank.name());
            team.setPrefix(rank.getDisplay() + " ");
        }
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId() != target.getUniqueId()) {
                Rank rank = RankDataManager.getRank(target.getUniqueId());
                player.getScoreboard().getTeam(rank.name()).addEntry(target.getName());
            }
        }
    }

    public void newTag(Player player) {
        Rank rank = RankDataManager.getRank(player.getUniqueId());
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (rank == null) target.getScoreboard().getTeam(Rank.ROOKIE.name()).addEntry(player.getName());
            else target.getScoreboard().getTeam(rank.name()).addEntry(player.getName());
        }
    }

    public void removeTag(Player player) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            target.getScoreboard().getEntryTeam(player.getName()).removeEntry(player.getName());
        }
    }
}
