package me.night.nullvalkyrie.miners;

import org.bukkit.Material;

import java.util.concurrent.ThreadLocalRandom;

public class CryptoMiner {
    protected String name;
    protected Material type;
    protected int level;
    protected double rate;
    protected long lastclaim;

    public CryptoMiner(String name, Material type, int level, double rate, long lastclaim) {
        this.name = name; // Name of the miner
        this.type = type; // Material to mine
        this.level = level;
        this.rate = rate; // Percentage generate chance in each tick 20tick per sec
        this.lastclaim = lastclaim;

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

    public void setLevel(int level) {
        this.level = level;
    }

    public long getLastclaim() {
        return lastclaim;
    }

    public static void generate(int pp, int times) {
        int generated = 0;
        for (int counter = 0; counter < times; counter++) {
            int count = ThreadLocalRandom.current().nextInt(100);
            if (count > pp) generated++;
        }
        System.out.println(generated);
    }

}
