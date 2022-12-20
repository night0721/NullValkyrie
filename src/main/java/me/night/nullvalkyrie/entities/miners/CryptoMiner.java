package me.night.nullvalkyrie.entities.miners;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.night.nullvalkyrie.enums.MinerType;
import me.night.nullvalkyrie.util.Skin;
import me.night.nullvalkyrie.util.Util;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class CryptoMiner {
    protected String name;
    protected MinerType type;
    protected int level;
    protected double rate;
    protected final long lastclaim;

    public CryptoMiner(String name, MinerType type, int level, double rate, long lastclaim) {
        this.name = name; // Name of the miner
        this.type = type; // Type of the miner
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
        return this.type.getMaterial();
    }

    public void setType(MinerType type) {
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

    public void spawn(Player player) {
        Location loc = player.getLocation().getWorld().getBlockAt(player.getLocation()).getLocation().add(0.5, 0, 0.5);
        if (player.getLocation().getWorld() == null) return;
        ArmorStand stand = player.getLocation().getWorld().spawn(loc, ArmorStand.class);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setSmall(true);
        stand.setArms(true);
        stand.setVisible(true);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta == null) return;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", this.type.getHeadTexture()).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        try {
            Util.setFieldValue(meta, "profile", profile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        head.setItemMeta(meta);

        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestdata = (LeatherArmorMeta) chest.getItemMeta();
        if (chestdata == null) return;
        chestdata.setColor(org.bukkit.Color.fromRGB(2, 2, 58));
        chest.setItemMeta(chestdata);
        ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta legdata = (LeatherArmorMeta) leg.getItemMeta();
        if (legdata == null) return;
        legdata.setColor(org.bukkit.Color.fromRGB(2, 2, 58));
        leg.setItemMeta(legdata);
        ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootdata = (LeatherArmorMeta) boot.getItemMeta();
        if (bootdata == null) return;
        bootdata.setColor(org.bukkit.Color.fromRGB(2, 2, 58));
        boot.setItemMeta(bootdata);
        ItemStack pick = new ItemStack(Material.GOLDEN_PICKAXE);
        stand.getEquipment().setItemInMainHand(pick);
        stand.getEquipment().setHelmet(head);
        stand.getEquipment().setChestplate(chest);
        stand.getEquipment().setLeggings(leg);
        stand.getEquipment().setBoots(boot);
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color(this.name));
        String[] skin = Skin.getSkin("Shiba_");
        gameProfile.getProperties().put("textures", new Property("textures", skin[0], skin[1]));
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer w = ((CraftWorld) player.getLocation().getWorld()).getHandle();
        EntityPlayer miner = new EntityPlayer(server, w, gameProfile, null);
        // TODO:  how to make a armor stand turn
        PlayerConnection pc = ((org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer) player).getHandle().b;
        pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, miner));
        World world = miner.getBukkitEntity().getWorld();
        List<Location> locs = new ArrayList<>();
        for (int x = (int) stand.getLocation().getX() - 3; x <= stand.getLocation().getX() + 2; x++) {
            for (int z = (int) stand.getLocation().getZ() - 2; z <= stand.getLocation().getZ() + 2; z++) {
                for (int y = (int) stand.getLocation().getY() - 1; y <= stand.getLocation().getY() - 1; y++) {
                    if (world.getBlockAt(x, y, z).getType() == this.getType())
                        locs.add(world.getBlockAt(x, y, z).getLocation());
                }
            }
        }
        locs.remove(world.getBlockAt(stand.getLocation().subtract(0, -1, 0)).getLocation());
        if (locs.size() != 0) {
            Location closest = locs.get(0);
            for (Location location : locs) {
                if (location.distance(stand.getLocation()) < closest.distance(stand.getLocation())) closest = location;
            }
            ArrayList<ItemStack> items = new ArrayList<>();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            if (closest.getBlock().getType() == this.getType()) {
                closest.getBlock().getDrops().clear();
                int lower = 0;
                int upper = 0;
                if (this.level == 1) {
                    lower = 1;
                    upper = 3;
                } else if (this.level == 2) {
                    lower = 2;
                    upper = 5;
                } else if (this.level == 3) {
                    lower = 3;
                    upper = 7;
                } else if (this.level == 4) {
                    lower = 4;
                    upper = 9;
                } else if (this.level == 5) {
                    lower = 5;
                    upper = 11;
                } else if (this.level == 6) {
                    lower = 6;
                    upper = 13;
                } else if (this.level == 7) {
                    lower = 7;
                    upper = 15;
                } else if (this.level == 8) {
                    lower = 8;
                    upper = 17;
                } else if (this.level == 9) {
                    lower = 9;
                    upper = 19;
                } else if (this.level == 10) {
                    lower = 10;
                    upper = 21;
                } else if (this.level == 11) {
                    lower = 11;
                    upper = 23;
                } else if (this.level == 12) {
                    lower = 12;
                    upper = 25;
                } else if (this.level == 13) {
                    lower = 13;
                    upper = 27;
                } else if (this.level == 14) {
                    lower = 14;
                    upper = 29;
                } else if (this.level == 15) {
                    lower = 15;
                    upper = 31;
                } else if (this.level == 16) {
                    lower = 16;
                    upper = 33;
                } else if (this.level == 17) {
                    lower = 17;
                    upper = 35;
                } else if (this.level == 18) {
                    lower = 18;
                    upper = 37;
                } else if (this.level == 19) {
                    lower = 19;
                    upper = 39;
                } else if (this.level == 20) {
                    lower = 20;
                    upper = 41;
                }
                items.add(new ItemStack(this.getType(), random.nextInt(lower, upper)));
                closest.getBlock().setType(Material.AIR);
            }
            // drop the items
            for (ItemStack item : items) {
                world.dropItemNaturally(closest, item);
            }
        }

    }
}
