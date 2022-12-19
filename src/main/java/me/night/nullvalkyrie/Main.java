package me.night.nullvalkyrie;

import io.github.cdimascio.dotenv.Dotenv;
import me.night.nullvalkyrie.events.listeners.*;
import me.night.nullvalkyrie.tasks.AlwaysDayTask;
import me.night.nullvalkyrie.ui.inventory.InventoryListener;
import me.night.nullvalkyrie.database.NPCDataManager;
import me.night.nullvalkyrie.discord.DiscordClientManager;
import me.night.nullvalkyrie.util.enchantments.EnchantmentManager;
import me.night.nullvalkyrie.ui.player.ScoreboardListener;
import me.night.nullvalkyrie.util.*;
import me.night.nullvalkyrie.commands.*;
import me.night.nullvalkyrie.database.DatabaseManager;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {
    public static Dotenv env;

    @Override
    public void onEnable() {
        EnchantmentManager.register();
        new FileManager();
        env = Dotenv.configure().directory("E:\\Files\\SB\\plugins\\NullValkyrie").filename(".env").load();
        new DatabaseManager();
        new CommandManager();
        Bukkit.getPluginManager().registerEvents(new ServerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemEvents(), this);
        Bukkit.getPluginManager().registerEvents(new DamageEffectEvents(), this);
        Bukkit.getPluginManager().registerEvents(new NPCEvents(), this);
        new DiscordClientManager();
        NPCDataManager.reloadNPC();
        new AlwaysDayTask().runTaskTimer(this, 0, 100);
    }
}
