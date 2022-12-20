package me.night.nullvalkyrie.util;

import java.util.*;

public class RandomCollection<E> {

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final HashMap<E, Double> chance = new HashMap<>();
    private final Random random = new Random();
    private double total = 0;

    public void add(Double weight, E value) {
        total += weight;
        map.put(total, value);
        chance.put(value, weight);
    }

    public void remove(E value) {
        if (chance.containsKey(value)) {
            chance.remove(value);
            total = 0;
            map.clear();
            for (E e : chance.keySet()) {
                total += chance.get(e);
                map.put(total, e);
            }
        }
    }

    public E getRandom() {
        if (total == 0) return null;
        double value = random.nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }

    public long getChance(E v) {
        double c = 0;
        for (E d : chance.keySet()) {
            if (d == v) {
                c = chance.get(d);
                break;
            }
        }
        return Math.round((c / total) * 100);
    }
    // write a method to get all the values in the collection so you can iterate over them
    public List<E> getAll() {
        return new ArrayList<>(map.values());
    }
}