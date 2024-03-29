package me.night.nullvalkyrie.entities.npcs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import me.night.nullvalkyrie.NullValkyrie;
import me.night.nullvalkyrie.database.NPCDataManager;
import me.night.nullvalkyrie.packets.protocol.PacketPlayOutEntityMetadata;
import me.night.nullvalkyrie.util.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
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
    private static final HashMap<Integer, ServerPlayer> NPCs = new HashMap<>();

    public static HashMap<Integer, ServerPlayer> getNPCs() {
        return NPCs;
    }
    @SuppressWarnings("ConstantConditions")
    public static void createNPC(Player player, String name) { // name must be less than 16 characters including color codes
        ServerPlayer sp = ((CraftPlayer) player).getHandle();
        MinecraftServer server = sp.server;
        ServerLevel level = ((CraftWorld) player.getLocation().getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color(name));
        String[] skin = Skin.getSkin(player);
        gameProfile.getProperties().put("textures", new Property("textures", skin[0], skin[1]));
        ServerPlayer npc = new ServerPlayer(server, level, gameProfile, null);
        Location location = player.getLocation();
        npc.setPos(location.getX(), location.getY(), location.getZ());
        addNPCPacket(npc);
        NPCs.put(npc.getId(), npc);
        NPCDataManager.setNPC(name, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), (int) player.getLocation().getPitch(), (int) player.getLocation().getYaw(), player.getLocation().getWorld().getName(), skin[0], skin[1]);
    }

    public static void addNPCPacket(ServerPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ServerGamePacketListenerImpl pc = ((CraftPlayer) player).getHandle().connection;
            pc.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));
            pc.send(new ClientboundAddPlayerPacket(npc));
            pc.send(new ClientboundRotateHeadPacket(npc, (byte) (npc.getBukkitYaw() * 256 / 360)));
            SynchedEntityData watcher = npc.getEntityData();
            watcher.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 127);
            new PacketPlayOutEntityMetadata(player, npc, watcher);
            Bukkit.getScheduler().runTaskLaterAsynchronously(NullValkyrie.getPlugin(NullValkyrie.class), () -> pc.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, npc)), 50);
            ItemStack netheriteAxe = new ItemStack(Material.NETHERITE_AXE);
            ItemStack anotherAxe = new ItemStack(Material.NETHERITE_INGOT);
            List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> itemList = new ArrayList<>();
            itemList.add(new Pair<>(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(netheriteAxe)));
            itemList.add(new Pair<>(EquipmentSlot.OFFHAND, CraftItemStack.asNMSCopy(anotherAxe)));
            pc.send(new ClientboundSetEquipmentPacket(npc.getBukkitEntity().getEntityId(), itemList));
        }
    }

    public static void addJoinPacket(Player player) {
        for (ServerPlayer npc : NPCs.values()) {
            ServerGamePacketListenerImpl pc = ((CraftPlayer) player).getHandle().connection;
            pc.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));
            pc.send(new ClientboundAddPlayerPacket(npc));
            pc.send(new ClientboundRotateHeadPacket(npc, (byte) (npc.getBukkitYaw() * 256 / 360)));
            SynchedEntityData watcher = npc.getEntityData();
            watcher.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 127);
            new PacketPlayOutEntityMetadata(player, npc, watcher);
            Bukkit.getScheduler().runTaskLaterAsynchronously(NullValkyrie.getPlugin(NullValkyrie.class), () -> pc.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, npc)), 50);
            ItemStack netheriteAxe = new ItemStack(Material.NETHERITE_AXE);
            ItemStack anotherAxe = new ItemStack(Material.NETHERITE_INGOT);
            List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> itemList = new ArrayList<>();
            itemList.add(new Pair<>(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(netheriteAxe)));
            itemList.add(new Pair<>(EquipmentSlot.OFFHAND, CraftItemStack.asNMSCopy(anotherAxe)));
            pc.send(new ClientboundSetEquipmentPacket(npc.getBukkitEntity().getEntityId(), itemList));
        }
    }
    @SuppressWarnings("ConstantConditions")
    public static void reloadNPC(List<HashMap<String, Object>> npcs) {
        for (HashMap<String, Object> npc : npcs) {
            Location location = new Location(Bukkit.getWorld((String) npc.get("world")), (double) npc.get("x"), (double) npc.get("y"), (double) npc.get("z"), (int) npc.get("yaw"), (int) npc.get("pitch"));
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color((String) npc.get("name")));
            gameProfile.getProperties().put("textures", new Property("textures", (String) npc.get("texture"), (String) npc.get("signature")));
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            ServerLevel w = ((CraftWorld) location.getWorld()).getHandle();
            ServerPlayer ep = new ServerPlayer(server, w, gameProfile, null);
            ep.setPos(location.getX(), location.getY(), location.getZ()); // NMS: 1.19.2 https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/Entity.html absMoveTo
            addNPCPacket(ep);
            NPCs.put(ep.getId(), ep);
        }
    }
    public static ServerPlayer getNPC(int id) {
        return NPCs.get(id);
    }
}
