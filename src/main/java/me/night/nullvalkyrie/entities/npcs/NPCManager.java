package me.night.nullvalkyrie.entities.npcs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.database.NPCDataManager;
import me.night.nullvalkyrie.util.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NPCManager {
    private static final List<EntityPlayer> NPCs = new ArrayList<>();

    public static List<EntityPlayer> getNPCs() {
        return NPCs;
    }
    public static void createNPC(Player player, String name) { // name must be less than 16 characters including color codes
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
        NPCDataManager.setNPC(name, (int) player.getLocation().getX(), (int) player.getLocation().getY(), (int) player.getLocation().getZ(), (int) player.getLocation().getPitch(), (int) player.getLocation().getYaw(), player.getLocation().getWorld().getName(), skin[0], skin[1]);
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
    public static void reloadNPC(List<HashMap<String, Object>> npcs) {
        for (HashMap<String, Object> npc : npcs) {
            Location location = new Location(Bukkit.getWorld((String) npc.get("world")), (int) npc.get("x"), (int) npc.get("y"), (int) npc.get("z"), (int) npc.get("yaw"), (int) npc.get("pitch"));
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color((String) npc.get("name")));
            gameProfile.getProperties().put("textures", new Property("textures", (String) npc.get("texture"), (String) npc.get("signature")));
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer w = ((CraftWorld) location.getWorld()).getHandle();
            EntityPlayer ep = new EntityPlayer(server, w, gameProfile, null);
            ep.a(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getPitch()); // NMS: 1.19.2 https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/Entity.html absMoveTo
            addNPCPacket(ep);
            NPCs.add(ep);
        }
    }

}
