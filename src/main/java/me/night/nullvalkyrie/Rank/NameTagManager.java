package me.night.nullvalkyrie.Rank;

import me.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NameTagManager {
    private Main main;

    public NameTagManager(Main main) {
        this.main = main;
    }
    @SuppressWarnings("deprecation")
    public void setNametags(Player player) {
        Scoreboard newScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = newScoreboard.registerNewObjective("TabList", "dummy");
        obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        player.setScoreboard(newScoreboard);
        for (Rank rank : Rank.values()) {
            Team team = player.getScoreboard().registerNewTeam(rank.name());
            team.setPrefix(rank.getDisplay() + " ");
        }
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId() != target.getUniqueId()) {
                Rank rank = ScoreboardListener.rankManager.getRank(target.getUniqueId());
                player.getScoreboard().getTeam(rank.name()).addEntry(target.getName());
            }
        }
    }
    public void newTag(Player player) {
        Rank rank = ScoreboardListener.rankManager.getRank(player.getUniqueId());
        for(Player target : Bukkit.getOnlinePlayers()) {
            target.getScoreboard().getTeam(rank.name()).addEntry(player.getName());
        }
    }
    public void removeTag(Player player) {
        for(Player target : Bukkit.getOnlinePlayers()) {
            target.getScoreboard().getEntryTeam(player.getName()).removeEntry(player.getName());
        }
    }
}
