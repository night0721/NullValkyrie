package me.night.nullvalkyrie.entities.miners;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import me.night.nullvalkyrie.util.Skin;
import me.night.nullvalkyrie.util.Util;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EnumItemSlot;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
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

    public static void spawn(Player player, String name, String url) {
        Location loc = player.getLocation();
        loc.setX(Math.round(loc.getX() * 2) / 2.0);
        loc.setZ(Math.round(loc.getZ() * 2) / 2.0);
        if (player.getLocation().getWorld() == null) return;
        ArmorStand stand = player.getLocation().getWorld().spawn(loc, ArmorStand.class);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setSmall(true);
        stand.setArms(true);
        stand.setVisible(true);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta == null) return;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
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
        List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> list = new ArrayList<>();
        list.add(new Pair<>(EnumItemSlot.a, CraftItemStack.asNMSCopy(pick)));
        list.add(new Pair<>(EnumItemSlot.c, CraftItemStack.asNMSCopy(boot)));
        list.add(new Pair<>(EnumItemSlot.d, CraftItemStack.asNMSCopy(leg)));
        list.add(new Pair<>(EnumItemSlot.e, CraftItemStack.asNMSCopy(chest)));
        list.add(new Pair<>(EnumItemSlot.f, CraftItemStack.asNMSCopy(head)));

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color(name));
        String[] skin = Skin.getSkin("Shiba_");
        gameProfile.getProperties().put("textures", new Property("textures", skin[0], skin[1]));
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer w = ((CraftWorld) player.getLocation().getWorld()).getHandle();
        EntityPlayer miner = new EntityPlayer(server, w, gameProfile, null);
        // TODO: remove the icon from tablist
        // TODO:  how to make a armor stand turn
        PlayerConnection pc = ((org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer) player).getHandle().b;
        pc.a(new PacketPlayOutEntityEquipment(stand.getEntityId(), list));
        pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, miner));
        double radius = 3;
        World world = miner.getBukkitEntity().getWorld();
        // write a loop to get nearby 24 blocks using radius 2
        int x, y, z = 0;
    }
}
