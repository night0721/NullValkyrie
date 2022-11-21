package me.night.nullvalkyrie.items;

import com.google.common.collect.ArrayListMultimap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Pickaxe {
    public ArrayListMultimap<Material, Material> multimap = ArrayListMultimap.create();
    public HashMap<Material, Long> phases = new HashMap<>();
    private final ItemStack itemStack;
    public Pickaxe(ItemStack item) {
        multimap.put(Material.STONE_PICKAXE, Material.IRON_ORE); //put some blocks and pickaxe to mine
        multimap.put(Material.STONE_PICKAXE, Material.DIAMOND_ORE);
        phases.put(Material.DIAMOND_ORE, 40L);
        phases.put(Material.IRON_ORE, 30L);
        itemStack = item;
    }
    public long getMiningPerPhase(Material material) {
        return phases.get(material);
    }
    public Material getMaterial() {
        return itemStack.getType();
    }
}
