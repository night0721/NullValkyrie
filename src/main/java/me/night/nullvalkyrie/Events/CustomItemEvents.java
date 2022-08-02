package me.night.nullvalkyrie.Events;

import me.night.nullvalkyrie.Items.CustomItemManager;
import me.night.nullvalkyrie.Items.Rarity;
import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.commands.SpawnCommand;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CustomItemEvents implements Listener {
    private Main main;
    public CustomItemEvents(Main main) {
        this.main = main;
    }
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType().equals(EntityType.SNOWBALL)) {
            Snowball sb = (Snowball) e.getDamager();
            Player pl = (Player) sb.getShooter();
            if (pl.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = pl.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if (name.equalsIgnoreCase(Rarity.ULTRA.getColor() + "Snow Gun")) {
                    ((Snowball) e.getDamager()).setShooter(pl.getPlayer());
                    e.setDamage(2000);
                } else if (name.equalsIgnoreCase("AA-12")) {
                    e.setDamage(7);
                } else {
                    e.setDamage(0);
                }
            }
        } else if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if(name.equalsIgnoreCase(Rarity.MYTHIC.getColor() + "Fabled Widow Sword")) {
                    if (e.getEntity() instanceof Zombie) {
                        int zombie = CustomItemManager.loadConfig("ItemData\\WidowSword.yml").getInt("zombie");
                        e.setDamage(e.getDamage() * (1 + zombie / 100));
                    } else if (e.getEntity() instanceof Skeleton) {
                        int skeleton = CustomItemManager.loadConfig("ItemData\\WidowSword.yml").getInt("skeleton");
                        e.setDamage(e.getDamage() * (1 + skeleton / 100));
                    } else if (e.getEntity() instanceof Spider) {
                        int spider = CustomItemManager.loadConfig("ItemData\\WidowSword.yml").getInt("spider");
                        e.setDamage(e.getDamage() * (1 + spider / 100));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (name.equalsIgnoreCase(Rarity.RARE.getColor() + "Grappling Hook")) {
                if(e.getState().equals(PlayerFishEvent.State.REEL_IN)) {
                    Location change = e.getHook().getLocation().subtract(player.getLocation());
                    player.setVelocity(change.toVector().multiply(0.4));
                }
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (name.equalsIgnoreCase(Rarity.GRAND.getColor() + "Teleport Door")) {
                    Block block = player.getTargetBlock((Set<Material>) null, 12);
                    Location l = block.getLocation();
                    l.add(0, 1, 0);
                    float yaw = player.getEyeLocation().getYaw();
                    float pitch = player.getEyeLocation().getPitch();
                    l.setYaw(yaw);
                    l.setPitch(pitch);
                    player.teleport(l);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 10);
                } else if (name.equalsIgnoreCase(Rarity.ULTRA.getColor() + "Snow Gun")) {
                    Snowball s = player.launchProjectile(Snowball.class, player.getLocation().getDirection());
                    s.setVelocity(player.getLocation().getDirection().multiply(10));
                } else if (name.equalsIgnoreCase(Rarity.MYTHIC.getColor() + "Terminator")) {
                    Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    arrow.setVelocity(arrow.getVelocity().multiply(5));
                    arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    arrow.setShooter(player);
                    arrow.setDamage(50);
                    Arrow a1 = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    a1.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(5)).multiply(5));
                    a1.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    a1.setShooter(player);
                    a1.setDamage(50);
                    Arrow a2 = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    a2.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(-5)).multiply(5));
                    a2.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    a2.setShooter(player);
                    a2.setDamage(50);
                    e.setCancelled(true);
                } else if(name.equalsIgnoreCase(Rarity.LEGENDARY.getColor() + "Explosive Bow")) {
                    Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    arrow.setVelocity(arrow.getVelocity().multiply(5));
                    arrow.setShooter(player);
                    arrow.setDamage(50);
                    e.setCancelled(true);
                }
            } else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (name.equalsIgnoreCase(Rarity.MYTHIC.getColor() + "Terminator")) {
                    Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    arrow.setVelocity(arrow.getVelocity().multiply(5));
                    arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    arrow.setShooter(player);
                    arrow.setDamage(50);
                    Arrow a1 = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    a1.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(5)).multiply(5));
                    a1.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    a1.setShooter(player);
                    a1.setDamage(50);
                    Arrow a2 = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    a2.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(-5)).multiply(5));
                    a2.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
                    a2.setShooter(player);
                    a2.setDamage(50);
                    e.setCancelled(true);
                } else if(name.equalsIgnoreCase(Rarity.LEGENDARY.getColor() + "Explosive Bow")) {
                    Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection());
                    arrow.setVelocity(arrow.getVelocity().multiply(5));
                    arrow.setShooter(player);
                    arrow.setDamage(50);
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onEntityShoot(EntityShootBowEvent e) {
        if (e.getProjectile() instanceof Arrow) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                    String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                    if (name.equalsIgnoreCase(Rarity.MYTHIC.getColor() + "Terminator")) {
                        e.setCancelled(true);
                    } else if(name.equalsIgnoreCase(Rarity.LEGENDARY.getColor() + "Explosive Bow")) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if(e.getEntity().getShooter() instanceof Player) {
            Player shooter = (Player) e.getEntity().getShooter();
            if(shooter.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = shooter.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if(name.equalsIgnoreCase(Rarity.LEGENDARY.getColor() + "Frag Grenade")) {
                    if(e.getHitBlock() == null) {
                        Location l = e.getHitEntity().getLocation();
                        e.getEntity().setShooter(shooter);
                        e.getHitEntity().getWorld().createExplosion(l.getX(),l.getY(),l.getZ(),100,false,false);
                    } else if(e.getHitEntity() == null) {
                        Location l = e.getHitBlock().getLocation();
                        e.getHitBlock().getWorld().createExplosion(l.getX(),l.getY(),l.getZ(),100,false,false);
                    }
                } else if(name.equalsIgnoreCase(Rarity.LEGENDARY.getColor() + "Explosive Bow")) {
                    Arrow arrow = (Arrow) e.getEntity();
                    Location al = arrow.getLocation();
                    arrow.setShooter(shooter);
                    shooter.getWorld().createExplosion(al, 100, false, false);
                }
            }

        }
    }
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void Projectile(ProjectileLaunchEvent e) {
        if(e.getEntity().getShooter() instanceof Player) {
            Player player = (Player) e.getEntity().getShooter();
            if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                if (name.equalsIgnoreCase(Rarity.LEGENDARY.getColor() + "Frag Grenade")) {
                    Egg s = (Egg) e.getEntity();
                    s.setVelocity(player.getLocation().getDirection().multiply(10));
                }
            }
        }
    }
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        int x = e.getBlockClicked().getX() + e.getBlockFace().getModX();
        int y = e.getBlockClicked().getY() + e.getBlockFace().getModY();
        int z = e.getBlockClicked().getZ() + e.getBlockFace().getModZ();
        Player player = e.getPlayer();
        if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
            String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (name.equalsIgnoreCase(Rarity.EPIC.getColor() + "Infinite Water Bucket")) {
                e.getPlayer().getWorld().getBlockAt(x,y,z).setType(Material.WATER);
                e.setCancelled(true);
            } else if (name.equalsIgnoreCase(Rarity.EPIC.getColor() + "Infinite LAVA Bucket")) {
                e.getPlayer().getWorld().getBlockAt(x,y,z).setType(Material.LAVA);
                e.setCancelled(true);
            }
        }
    }
    private Location l;
    private boolean spawnable = false;
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
                if((player.getHealth() - e.getDamage()) <= 0) {
                    e.setCancelled(true);
                    Location loc = player.getWorld().getBlockAt(-3,23,-3).getLocation();
                    player.teleport(loc);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(e.getDamager() instanceof Player ? ChatColor.RED + player.getName() + " has been killed by " + e.getDamager().getName() : ChatColor.RED + player.getName() + " died");
                        p.hidePlayer(player);
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.showPlayer(player);
                            }
                            player.setHealth(20);
//                            while(!spawnable) {
//                                l = generateRandomCoord(9, Bukkit.getWorld("world"));
//                                if(isSpawnable(l)) {
//                                    spawnable = true;
//                                    player.teleport(l);
//                                }
//                            }
                            player.teleport(generateRandomCoord(9, Bukkit.getWorld("world")));
                        }
                    }.runTaskLater(main, 100L);
                    run(player, new int[]{5});
                }
        }

    }
    public Location generateRandomCoord(int size, World world) {
        int ranX = SpawnCommand.getRandomWithNeg(size), ranZ = SpawnCommand.getRandomWithNeg(size);
        Block block = world.getHighestBlockAt(ranX, ranZ);
        return block.getLocation();
    }
    public boolean isSpawnable(Location loc) {
        Block feetBlock = loc.getBlock(), headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock = loc.clone().add(0, 2, 0).getBlock();
        return feetBlock.isPassable() && !feetBlock.isLiquid() && headBlock.isPassable() && !headBlock.isLiquid() && upperBlock.isPassable() && !upperBlock.isLiquid();
    }
    private int taskID;
    public void run(Player player, int[] a) {
        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, () -> {
            player.sendTitle(ChatColor.RED +"YOU DIED!",ChatColor.GREEN +"You will revive in " + a[0] + " seconds",0,20,0);
            a[0]--;
            if (a[0] == 0) {
                Bukkit.getScheduler().cancelTask(taskID);
            }
        }, 0L, 20L);
    }

    private final Map<UUID, Merchant> villagerlist = new HashMap<>();
    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity clickedEntity = e.getRightClicked();
        if (clickedEntity instanceof Creeper) {
            if (p.getInventory().getItemInMainHand().getType() != Material.STICK) return;
            clickedEntity.remove();
            Location loc = clickedEntity.getLocation();
            Villager villager = (Villager) p.getWorld().spawnEntity(loc, EntityType.VILLAGER);
            villager.setProfession(Villager.Profession.TOOLSMITH);
            List<MerchantRecipe> recipes = new ArrayList<>();
            MerchantRecipe bread = new MerchantRecipe(new ItemStack(Material.BREAD, 3), 10);
            bread.addIngredient(new ItemStack(Material.EMERALD, 10));
            recipes.add(bread);

            MerchantRecipe tntStick = new MerchantRecipe(CustomItemManager.getItem("Terminator"), 10);
            tntStick.addIngredient(CustomItemManager.getItem("Widow Sword"));
            recipes.add(tntStick);
            Merchant merchant = Bukkit.createMerchant("Yum Yum In The Tum Tum");
            merchant.setRecipes(recipes);

            villagerlist.put(villager.getUniqueId(), merchant);

            p.spawnParticle(Particle.END_ROD, loc, 30, 0, 1, 0, 0.2);
            p.openMerchant(merchant, true);
        }
        if (e.getRightClicked() instanceof Villager) {
            Merchant merchant = villagerlist.get(clickedEntity.getUniqueId());
            if (merchant == null) return;
            e.setCancelled(true);
            p.openMerchant(merchant, true);
        }
    }

}