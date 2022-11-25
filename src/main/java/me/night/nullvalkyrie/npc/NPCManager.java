package me.night.nullvalkyrie.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.items.CustomItemManager;
import me.night.nullvalkyrie.util.Util;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NPCManager {
    private static final List<EntityPlayer> NPCs = new ArrayList<>();

    public static List<EntityPlayer> getNPCs() {
        return NPCs;
    }
    public static void reloadNPC() {
        loadNPC(CustomItemManager.loadConfig("npcs.yml"));
    }
    public static void createNPC(Player player, String name) { //name must be less than 16 characters including color codes **
        EntityPlayer sp = ((CraftPlayer) player).getHandle();
        MinecraftServer server = sp.c;
        WorldServer level = ((CraftWorld) player.getLocation().getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color(name));
        String[] skin = Skin.getSkin(player);
        gameProfile.getProperties().put("textures", new Property("textures", skin[0], skin[1]));
        EntityPlayer npc = new EntityPlayer(server, level, gameProfile, null);
        Location location = player.getLocation();
        npc.a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        addNPCPacket(npc);
        NPCs.add(npc);
        int var = 1;
        FileConfiguration npcFile = CustomItemManager.loadConfig("npcs.yml");
        if (npcFile.contains("data")) var = npcFile.getConfigurationSection("data").getKeys(false).size() + 1;
        npcFile.set("data." + var + ".x", (int) player.getLocation().getX());
        npcFile.set("data." + var + ".y", (int) player.getLocation().getY());
        npcFile.set("data." + var + ".z", (int) player.getLocation().getZ());
        npcFile.set("data." + var + ".pitch", (int) player.getLocation().getPitch());
        npcFile.set("data." + var + ".yaw", (int) player.getLocation().getYaw());
        npcFile.set("data." + var + ".world", player.getLocation().getWorld().getName());
        npcFile.set("data." + var + ".name", name);
        npcFile.set("data." + var + ".texture", skin[0]);
        npcFile.set("data." + var + ".signature", skin[1]);
        try {
            npcFile.save(CustomItemManager.loadFile("npcs.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadNPC(FileConfiguration npcFile) {
        npcFile.getConfigurationSection("data").getKeys(false).forEach(npc -> {
            Location location = new Location(Bukkit.getWorld(Objects.requireNonNull(npcFile.getString("data." + npc + ".world"))), npcFile.getInt("data." + npc + ".x"), npcFile.getInt("data." + npc + ".y"), npcFile.getInt("data." + npc + ".z"));
            location.setPitch((float) npcFile.getDouble("data." + npc + ".pitch"));
            location.setYaw((float) npcFile.getDouble("data." + npc + ".yaw"));
            String name = npcFile.getString("data." + npc + ".name");
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color(name));
            gameProfile.getProperties().put("textures", new Property("textures", npcFile.getString("data." + npc + ".texture"), npcFile.getString("data." + npc + ".signature")));
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
            EntityPlayer npcs = new EntityPlayer(server, world, gameProfile, null);
            npcs.a(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getPitch());
            addNPCPacket(npcs);
            NPCs.add(npcs);
        });

    }

    public static void addNPCPacket(EntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection pc = ((CraftPlayer) player).getHandle().b;
            pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            pc.a(new PacketPlayOutNamedEntitySpawn(npc));
            pc.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.getBukkitYaw() * 256 / 360)));
            DataWatcher watcher = npc.ai();
            watcher.b(new DataWatcherObject<>(17, DataWatcherRegistry.a), (byte) 127);
            pc.a(new PacketPlayOutEntityMetadata(npc.ae(), watcher, true));
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(Main.class), () -> pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, npc)), 50);
            ItemStack netheriteAxe = new ItemStack(Material.NETHERITE_AXE);
            ItemStack anotherAxe = new ItemStack(Material.NETHERITE_INGOT);
            List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> list = new ArrayList<>();
            list.add(new Pair<>(EnumItemSlot.a, CraftItemStack.asNMSCopy(netheriteAxe)));
            list.add(new Pair<>(EnumItemSlot.b, CraftItemStack.asNMSCopy(anotherAxe)));
            pc.a(new PacketPlayOutEntityEquipment(npc.ae(), list));
        }
    }

    public static void addJoinPacket(Player player) {
        for (EntityPlayer npc : NPCs) {
            PlayerConnection pc = ((CraftPlayer) player).getHandle().b;
            pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            pc.a(new PacketPlayOutNamedEntitySpawn(npc));
            pc.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.getBukkitYaw() * 256 / 360)));
            DataWatcher watcher = npc.ai();
            watcher.b(new DataWatcherObject<>(17, DataWatcherRegistry.a), (byte) 127);
            pc.a(new PacketPlayOutEntityMetadata(npc.ae(), watcher, true));
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(Main.class), () -> pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, npc)), 50);
            ItemStack netheriteAxe = new ItemStack(Material.NETHERITE_AXE);
            ItemStack anotherAxe = new ItemStack(Material.NETHERITE_INGOT);
            List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> list = new ArrayList<>();
            list.add(new Pair<>(EnumItemSlot.a, CraftItemStack.asNMSCopy(netheriteAxe)));
            list.add(new Pair<>(EnumItemSlot.b, CraftItemStack.asNMSCopy(anotherAxe)));
            pc.a(new PacketPlayOutEntityEquipment(npc.ae(), list));
        }
    }

}
