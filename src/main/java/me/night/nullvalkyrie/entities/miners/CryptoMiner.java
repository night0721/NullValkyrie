package me.night.nullvalkyrie.entities.miners;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import me.night.nullvalkyrie.NullValkyrie;
import me.night.nullvalkyrie.database.MinerDataManager;
import me.night.nullvalkyrie.enums.MinerType;
import me.night.nullvalkyrie.packets.protocol.PacketPlayOutEntityMetadata;
import me.night.nullvalkyrie.util.Skin;
import me.night.nullvalkyrie.util.Util;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

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
    protected final double x;
    protected final double y;
    protected final double z;
    protected final Location location;

    public CryptoMiner(String name, MinerType type, int level, double rate, long lastclaim, Location location) {
        this.name = name; // Name of the miner
        this.type = type; // Type of the miner
        this.level = level;
        this.rate = rate; // Percentage generate chance in each tick 20tick per sec
        this.lastclaim = lastclaim;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.location = location;
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

    public Material getType() {
        return this.type.getMaterial();
    }

    public MinerType getMinerType() {
        return this.type;
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

    public Location getLocation() {
        return location;
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
        ArmorStand stand = new ArmorStand(((CraftWorld) this.getLocation().getWorld()).getHandle(), this.getLocation().getX() + 0.5, this.getLocation().getY(), this.getLocation().getZ() + 0.5);
        stand.setInvulnerable(true);
        stand.setPos(this.getLocation().getX() + 0.5, this.getLocation().getY(), this.getLocation().getZ() + 0.5);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta == null) return;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", this.getMinerType().getHeadTexture()).getBytes());
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
        List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> list = new ArrayList<>();
        list.add(new Pair<>(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(head)));
        list.add(new Pair<>(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(chest)));
        list.add(new Pair<>(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(leg)));
        list.add(new Pair<>(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(boot)));
        list.add(new Pair<>(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(pick)));

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color(this.name));
        String[] skin = Skin.getSkin("Shiba_");
        gameProfile.getProperties().put("textures", new Property("textures", skin[0], skin[1]));
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel w = ((CraftWorld) player.getLocation().getWorld()).getHandle();
        ServerPlayer m = new ServerPlayer(server, w, gameProfile, null);
        ServerGamePacketListenerImpl pc = ((CraftPlayer) player).getHandle().connection;
        pc.send(new ClientboundAddEntityPacket(stand));
        pc.send(new ClientboundSetEquipmentPacket(stand.getId(), list));
        pc.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, m));
        SynchedEntityData watcher = stand.getEntityData();
        watcher.set(new EntityDataAccessor<>(0, EntityDataSerializers.BYTE), (byte) 20);
        watcher.set(new EntityDataAccessor<>(5, EntityDataSerializers.BOOLEAN), true);
        watcher.set(new EntityDataAccessor<>(15, EntityDataSerializers.BYTE), (byte) 13);
        new PacketPlayOutEntityMetadata(player, stand, watcher);
        new BukkitRunnable() {
            @Override
            public void run() {
                generate();
            }
        }.runTaskTimer(NullValkyrie.getPlugin(NullValkyrie.class), 0, 40);
    }
    public void generate() {
        List<Location> locs = new ArrayList<>();
        for (int x = (int) this.getLocation().getX() - 2; x <= this.getLocation().getX() + 2; x++) {
            for (int z = (int) this.getLocation().getZ() - 2; z <= this.getLocation().getZ() + 2; z++) {
                for (int y = (int) this.getLocation().getY() - 1; y <= this.getLocation().getY() - 1; y++) {
                    this.getLocation().setY(17.0F);
                    System.out.println(x + " " + y + " " + z);
                    if (this.getLocation().getWorld().getBlockAt(x, y, z).getType() == this.getType()) {
                        locs.add(this.getLocation().getWorld().getBlockAt(x, y, z).getLocation());
                    }
                }
            }
        }
        if (locs.size() != 0) {
            Location closest = locs.get(0);
            for (Location location : locs)
                if (location.distance(this.getLocation()) < closest.distance(this.getLocation()))
                    closest = location;
            ArrayList<ItemStack> items = new ArrayList<>();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            closest.getBlock().getDrops().clear();
            List<int[]> levels = List.of(new int[]{1, 3}, new int[]{2, 5}, new int[]{3, 7}, new int[]{4, 9}, new int[]{5, 11}, new int[]{6, 13}, new int[]{7, 15}, new int[]{8, 17}, new int[]{9, 19}, new int[]{10, 21}, new int[]{11, 23}, new int[]{12, 25}, new int[]{13, 27}, new int[]{14, 29}, new int[]{15, 31}, new int[]{16, 33}, new int[]{17, 35}, new int[]{18, 37}, new int[]{19, 39}, new int[]{20, 41}, new int[]{21, 43}, new int[]{22, 45}, new int[]{23, 47}, new int[]{24, 49}, new int[]{25, 51}, new int[]{26, 53}, new int[]{27, 55}, new int[]{28, 57}, new int[]{29, 59}, new int[]{30, 61}, new int[]{31, 63}, new int[]{32, 65}, new int[]{33, 67}, new int[]{34, 69}, new int[]{35, 71}, new int[]{36, 73}, new int[]{37, 75}, new int[]{38, 77}, new int[]{39, 79}, new int[]{40, 81}, new int[]{41, 83}, new int[]{42, 85}, new int[]{43, 87}, new int[]{44, 89}, new int[]{45, 91}, new int[]{46, 93}, new int[]{47, 95}, new int[]{48, 97}, new int[]{49, 99}, new int[]{50, 100});
            items.add(new ItemStack(this.getType(), random.nextInt(levels.get(this.getLevel())[0], levels.get(this.getLevel())[1])));
            closest.getBlock().setType(Material.AIR);
            // drop the items
            for (ItemStack item : items) {
                this.getLocation().add(0, 2, 0).getWorld().dropItemNaturally(closest, item);
            }
        }
    }
    public static void reloadMiner() {
        for (Player player : Bukkit.getOnlinePlayers())
            for (CryptoMiner miner : MinerDataManager.getMiners().values()) {
                miner.spawn(player);
            }
    }

    public static void onJoin(Player player) {
        for (CryptoMiner miner : MinerDataManager.getMiners().values()) {
            System.out.println(miner.getName());
            miner.spawn(player);
        }
    }
}
