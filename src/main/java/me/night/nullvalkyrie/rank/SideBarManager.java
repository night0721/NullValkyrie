package me.night.nullvalkyrie.rank;

import me.night.nullvalkyrie.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.UUID;

public class SideBarManager {
    private final HashMap<UUID, Integer> deaths = new HashMap<>();
    private int taskID;
    private Main main;

    public SideBarManager(Main main) {
        this.main = main;
    }

    @SuppressWarnings("deprecation")
    public void setSideBar(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective obj;
        if (board.getObjective("Vanadium") != null) {
            obj = board.getObjective("Vanadium");
        } else {
            obj = board.registerNewObjective("Vanadium", "dummy");
        }
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + ">> Vanadium <<");
        Score hypens = obj.getScore(ChatColor.GOLD + "=-=-=-=-=-=-=-=");
        hypens.setScore(9);
        Score name = obj.getScore(ChatColor.BLUE + "Player Name: ");
        name.setScore(8);
        Score name2 = obj.getScore(ChatColor.WHITE + player.getName());
        name2.setScore(7);
        Score space1 = obj.getScore("  ");
        space1.setScore(6);
        Score players = obj.getScore(ChatColor.LIGHT_PURPLE + "Players Online:");
        players.setScore(5);
        Score playercount = obj.getScore(ChatColor.YELLOW.toString() + Bukkit.getServer().getOnlinePlayers().size());
        playercount.setScore(4);
        Score space2 = obj.getScore(" ");
        space2.setScore(2);
        Score website = obj.getScore(ChatColor.YELLOW + "cath.js.org");
        website.setScore(1);
        Team playersOnline;
        if(board.getTeam("deathsTotal") != null) {
            playersOnline = board.getTeam("deathsTotal");
        } else playersOnline = board.registerNewTeam("deathsTotal");
        playersOnline.addEntry(ChatColor.BOLD.toString());
        playersOnline.setPrefix(ChatColor.BLUE + "Deaths: ");
        playersOnline.setSuffix(ChatColor.YELLOW + "0");
        obj.getScore(ChatColor.BOLD.toString()).setScore(3);
        player.setScoreboard(board);
        deaths.put(player.getUniqueId(), 0);
    }

    public void start(Player player) {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            int count = 0;
            final AnimatedSideBar board = new AnimatedSideBar(player.getUniqueId());

            @Override
            public void run() {
                if (!board.hasID())
                    board.setID(taskID);
                if (count == 13)
                    count = 0;
                switch (count) {
                    case 0:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVanadium&1&l <<"));
                        break;
                    case 1:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&l>&1&l> &e&lVanadium &1&l<<")); //>
                        break;
                    case 2:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>&b&l> &e&lVanadium &1&l<<")); //>>
                        break;
                    case 3:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &b&lV&e&lanadium&1&l <<"));//V
                        break;
                    case 4:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lV&b&la&e&lnadium&1&l <<")); //Va
                        break;
                    case 5:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVa&b&ln&e&ladium&1&l <<")); //Van
                        break;
                    case 6:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVan&b&la&e&ldium&1&l <<")); //Vana
                        break;
                    case 7:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVana&b&ld&e&lium&1&l <<")); //Vanad
                        break;
                    case 8:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVanad&b&li&e&lum&1&l <<")); //Vanadi
                        break;
                    case 9:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVanadi&b&lu&e&lm&1&l <<")); // Vanadiu
                        break;
                    case 10:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVanadiu&b&lm&1&l <<")); //Vanadium
                        break;
                    case 11:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVanadium &b&l<&1&l<")); // <
                        break;
                    case 12:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&l>> &e&lVanadium &1&l<&b&l<")); // <<
                        setSideBar(player);
                        break;
                }
                count++;
            }
        }, 0, 10);
    }

    public void changeOnDeath(Player player) {
        int amount = deaths.get(player.getUniqueId());
        deaths.put(player.getUniqueId(), amount++);
        player.getScoreboard().getTeam("deathsTotal").setSuffix(ChatColor.YELLOW.toString() + amount);
    }
}
