package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.util.components.CustomMob;
import me.night.nullvalkyrie.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

public class SpawnCommand extends Command implements Listener {
    private final Main main;
    public World world;
    public final Map<Entity, Integer> indicators = new HashMap<>();
    public final Map<Entity, CustomMob> entities = new HashMap<>();
    private final DecimalFormat formatter = new DecimalFormat("#");
    public SpawnCommand(Main main) {
        super(
                "spawn",
                new String[]{},
                "Spawn a custom mob",
                ""
        );
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        world = Bukkit.getWorld("world");

        spawnMobs(9, 10, 5 * 20);
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
                stands.removeAll(removal);
            }
        }.runTaskTimer(main, 0L, 1L);

    }
    public void spawnMobs(int size, int mobCap, int spawnTime) {
        CustomMob[] mobTypes = CustomMob.values();
        new BukkitRunnable() {
            final Set<Entity> spawned = entities.keySet();
            final List<Entity> removal = new ArrayList<>();
            @Override
            public void run() {
                for (Entity entity : spawned) {
                    if (!entity.isValid() || entity.isDead()) removal.add(entity);
                }
                spawned.removeAll(removal);

                // Spawning Algorithm
                int diff = mobCap - entities.size();
                if (diff <= 0) return;
                int spawnAmount = (int) (Math.random() * (diff + 1)), count = 0;
                while (count <= spawnAmount) {
                    count++;
                    int ranX = getRandomWithNeg(size), ranZ = getRandomWithNeg(size);
                    Block block = world.getHighestBlockAt(ranX, ranZ);
                    double xOffset = getRandomOffset(), zOffset = getRandomOffset();
                    Location loc = block.getLocation().clone().add(xOffset, 1, zOffset);
                    if (!isSpawnable(loc)) continue;
                    double random = Math.random() * 101, previous = 0;
                    CustomMob typeToSpawn = mobTypes[0];
                    for (CustomMob type : mobTypes) {
                        previous += type.getSpawnChance();
                        if (random <= previous) {
                            typeToSpawn = type;
                            break;
                        }
                    }
                    entities.put(typeToSpawn.spawn(loc), typeToSpawn);
                }
            }
        }.runTaskTimer(main, 0L, spawnTime);
    }

    public static boolean isSpawnable(Location loc) {
        Block feetBlock = loc.getBlock(), headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock = loc.clone().add(0, 2, 0).getBlock();
        return feetBlock.isPassable() && !feetBlock.isLiquid() && headBlock.isPassable() && !headBlock.isLiquid() && upperBlock.isPassable() && !upperBlock.isLiquid();
    }

    private static double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    public static int getRandomWithNeg(int size) {
        int random = (int) (Math.random() * (size + 1));
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity rawEntity = event.getEntity();
        if (!entities.containsKey(rawEntity)) return;
        CustomMob mob = entities.get(rawEntity);
        LivingEntity entity = (LivingEntity) rawEntity;
        double damage = event.getFinalDamage(), health = entity.getHealth() + entity.getAbsorptionAmount();
        if (health > damage) {
            // If the entity survived the hit
            health -= damage;
            entity.setCustomName(Utils.color(mob.getName() + " &r&c" + (int) health + "/" + (int) mob.getMaxHealth() + "❤"));
        }
        Location loc = entity.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
        world.spawn(loc, ArmorStand.class, armorStand -> {
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(Utils.color("&c" + formatter.format(damage)));
            indicators.put(armorStand, 30);
        });
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!entities.containsKey(event.getEntity())) return;
        event.setDroppedExp(0);
        event.getDrops().clear();
        entities.remove(event.getEntity()).tryDropLoot(event.getEntity().getLocation());
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
