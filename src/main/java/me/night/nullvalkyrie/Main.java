package me.night.nullvalkyrie;

import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import me.night.nullvalkyrie.events.listeners.CustomItemEvents;
import me.night.nullvalkyrie.events.listeners.DamageEffectEvents;
import me.night.nullvalkyrie.events.listeners.NPCEvents;
import me.night.nullvalkyrie.events.listeners.ServerEvents;
import me.night.nullvalkyrie.ui.inventory.InventoryListener;
import me.night.nullvalkyrie.database.NPCDataManager;
import me.night.nullvalkyrie.discord.DiscordClientManager;
import me.night.nullvalkyrie.enchantments.EnchantmentManager;
import me.night.nullvalkyrie.ui.player.ScoreboardListener;
import me.night.nullvalkyrie.util.*;
import me.night.nullvalkyrie.commands.*;
import me.night.nullvalkyrie.database.DatabaseManager;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Dotenv env;
    public static MongoDatabase database;

    @Override
    public void onEnable() {
        EnchantmentManager.register();
        new FileManager();
        env = Dotenv.configure().directory("E:\\Files\\SB\\plugins\\NullValkyrie").filename(".env").load();
        new DatabaseManager();
        new CommandManager();
        Bukkit.getPluginManager().registerEvents(new ServerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageEffectEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new NPCEvents(), this);
        new DiscordClientManager();
        NPCDataManager.reloadNPC();
    }
}
