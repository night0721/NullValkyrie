package me.night.nullvalkyrie.miners;

import me.night.nullvalkyrie.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static me.night.nullvalkyrie.items.CustomItemManager.loadConfig;
import static me.night.nullvalkyrie.items.CustomItemManager.loadFile;

public class CryptoMiner {
    protected static Main main;
    protected String name;
    protected Material type;
    protected int level;
    protected double rate;
    protected static int generated;
    protected long lastclaim;
    public CryptoMiner(Main main, String name, Material type, int level, double rate, long lastclaim) {
        CryptoMiner.main = main;
        this.name = name; // Name of the miner
        this.type = type; // Material to mine
        this.level = level;
        this.rate = rate; // Percentage generate chance in each tick 20tick per sec
        this.lastclaim = lastclaim;
        FileConfiguration file = loadConfig("miners.yml");
        setMiner(Integer.toString(file.getKeys(false).size()), name, type.name(), level, rate, lastclaim);
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
    public long getLastclaim() {
        return lastclaim;
    }
    public void setLastClaim(long lastclaim) {
        this.lastclaim = lastclaim;
    }
    public static void generate(int pp, int times) {
        for (int counter = 0; counter < times; counter++) {
            int count = ThreadLocalRandom.current().nextInt(100);
            if(count > pp) generated++;
        }
        System.out.println(generated);
    }
    public List<CryptoMiner> getMiners() {
        List<CryptoMiner> arr = new ArrayList<>();
        FileConfiguration file = loadConfig("miners.yml");
        for(String c : file.getKeys(false)) {
            arr.add(new CryptoMiner(main, file.getString(c + ".name"), Material.matchMaterial(file.getString(c + ".material")), file.getInt(c + ".level"), file.getDouble(c + ".rate"), file.getLong(c + ".last-claim")));
        }
        return arr;
    }
    public static HashMap<String, Object> getMiner(String index) {
        FileConfiguration file = loadConfig("miners.yml");
        HashMap<String, Object> details = new HashMap<>();
        details.put("name", file.getString(index + ".name"));
        details.put("material", Material.matchMaterial(file.getString(index + ".material")));
        details.put("level", file.getInt(index + ".level"));
        details.put("rate", file.getDouble(index + ".rate"));
        details.put("lastclaim", file.getLong(index + ".last-claim"));
        return details;
    }
    public void setMiner(String index, String name, String material, int level, double rate, long time) {
        FileConfiguration file = loadConfig("miners.yml");
        file.createSection(index);
        file.set(index + ".name", name);
        file.set(index + ".material", material);
        file.set(index + ".level", level);
        file.set(index + ".rate", rate);
        file.set(index + ".enabled", true);
        file.set(index + ".last-claim", time);
        try {
            file.save(loadFile("miners.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
