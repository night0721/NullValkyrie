package me.night.nullvalkyrie;

import io.github.cdimascio.dotenv.Dotenv;
import me.night.nullvalkyrie.chests.MenuListener;
import me.night.nullvalkyrie.database.NPCDataManager;
import me.night.nullvalkyrie.discord.DiscordClientManager;
import me.night.nullvalkyrie.enchantments.EnchantmentManager;
import me.night.nullvalkyrie.events.*;
import me.night.nullvalkyrie.items.CustomItemManager;
import me.night.nullvalkyrie.npc.*;
import me.night.nullvalkyrie.ui.ScoreboardListener;
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
        env = Dotenv.configure().directory("E:\\Files\\SB\\plugins\\NullValkyrie").filename(".env").load();
        new DatabaseManager();
        new FileManager();
        new CommandManager();
        Bukkit.getPluginManager().registerEvents(new ServerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomItemEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageEffectEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new NPCEvents(), this);
        new DiscordClientManager();
        NPCDataManager.reloadNPC();
    }
}
