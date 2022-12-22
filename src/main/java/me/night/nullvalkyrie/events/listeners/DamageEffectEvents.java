package me.night.nullvalkyrie.events.listeners;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

public class DamageEffectEvents implements Listener {
    public final World world = Bukkit.getWorld("world");
    public final Map<Entity, Integer> indicators = new HashMap<>();
    private final DecimalFormat formatter = new DecimalFormat("#");

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        double damage = e.getFinalDamage();
        if (e.getEntity() instanceof Zombie) {
            Location loc = e.getEntity().getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
            assert world != null;
            world.spawn(loc, ArmorStand.class, armorStand -> {
                armorStand.setMarker(true);
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setSmall(true);
                armorStand.setCustomNameVisible(true);
                armorStand.setCustomName(Util.color("&c&l" + formatter.format(damage)));
                indicators.put(armorStand, 30);
            });
            removeStands();
        }
    }

    public void removeStands() {
        new BukkitRunnable() {
            final Set<Entity> stands = indicators.keySet();
            final List<Entity> removal = new ArrayList<>();

            @Override
            public void run() {
                for (Entity stand : stands) {
                    int ticksLeft = indicators.get(stand);
                    if (ticksLeft == 0) {
                        stand.remove();
                        removal.add(stand);
                        continue;
                    }
                    ticksLeft--;
                    indicators.put(stand, ticksLeft);
                }
                removal.forEach(stands::remove);
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
    }

    public boolean isSpawnable(Location loc) {
        Block feetBlock = loc.getBlock(), headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock = loc.clone().add(0, 2, 0).getBlock();
        return feetBlock.isPassable() && !feetBlock.isLiquid() && headBlock.isPassable() && !headBlock.isLiquid() && upperBlock.isPassable() && !upperBlock.isLiquid();
    }

    private double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    public int getRandomWithNeg(int size) {
        int random = (int) (Math.random() * (size + 1));
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    public Location generateRandomCoord(int size, World world) {
        int ranX = getRandomWithNeg(size), ranZ = getRandomWithNeg(size);
        Block block = world.getHighestBlockAt(ranX, ranZ);
        return block.getLocation();
    }

    public Location generateRandomCoordIsSpawnable(int size) {
        while (true) {
            assert world != null;
            Location coord = generateRandomCoord(size, world);
            boolean spawnable = isSpawnable(coord);
            if (spawnable) {
                return coord;
            }
        }
    }
}
//
//    @EventHandler
//    public void onEntityDeath(EntityDeathEvent event) {
//        if (!entities.containsKey(event.getEntity())) return;
//        event.setDroppedExp(0);
//        event.getDrops().clear();
//        entities.remove(event.getEntity()).tryDropLoot(event.getEntity().getLocation());
//    }