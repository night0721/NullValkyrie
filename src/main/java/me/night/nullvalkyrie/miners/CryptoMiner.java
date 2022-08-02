package me.night.nullvalkyrie.miners;

import me.night.nullvalkyrie.Main;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static me.night.nullvalkyrie.Items.CustomItemManager.loadConfig;
import static me.night.nullvalkyrie.Items.CustomItemManager.loadFile;

public class CryptoMiner {
    protected Main main;
    protected String name;
    protected Material type;
    protected int level;
    protected double rate;
    protected int generated;
    public CryptoMiner(Main main, String name, Material type, int level, double rate) {
        this.main = main;
        this.name = name; // Name of the miner
        this.type = type; // Material to mine
        this.level = level;
        this.rate = rate; // Percentage generate chance in each tick 20tick per sec
        //generate(70);

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
    public Material getType() {
        return type;
    }
    public void setType(Material type) {
        this.type = type;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) { this.level = level; }
    public void generate(int pp) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int count = ThreadLocalRandom.current().nextInt(100);
                if(count > pp) generated++;
            }
        }.runTaskTimer(main, 0L, 1L);
    }
    public List<CryptoMiner> getMiners() {
        List<CryptoMiner> arr = new ArrayList<>();
        FileConfiguration file = loadConfig("miners.yml");
        for(String c : file.getKeys(false)) {
            arr.add(new CryptoMiner(main, file.getString(c + ".name"), Material.matchMaterial(file.getString(c + ".material")), file.getInt(c + ".level"), file.getDouble(c + ".rate")));
        }
        return arr;
    }
    public CryptoMiner getMiner(String index) {
        FileConfiguration file = loadConfig("miners.yml");
        return new CryptoMiner(main, file.getString(index + ".name"), Material.matchMaterial(file.getString(index + ".name")), file.getInt(index + ".level"), file.getDouble(index + ".rate"));
    }
    public void setMiner(String index, String name, String material, int level, double rate) {
        CryptoMiner miner = new CryptoMiner(main, name, Material.matchMaterial(material), level, rate);
        FileConfiguration file = loadConfig("miners.yml");
        file.createSection(index);
        file.set(index + ".name", name);
        file.set(index + ".material", material);
        file.set(index + ".level", level);
        file.set(index + ".rate", rate);
        file.set(index + ".enabled", true);
        try {
            file.save(loadFile("miners.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
