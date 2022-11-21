package me.night.nullvalkyrie.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import me.night.nullvalkyrie.Main;
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
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC {
    private static Main main;
    public NPC(Main main) {
        NPC.main = main;
    }
    private static final List<EntityPlayer> NPCs = new ArrayList<>();
    public static List<EntityPlayer> getNPCs() {
        return NPCs;
    }
    public static void createNPC(Player player, String name) { //name must be less than 16 characters including color codes **
        CraftPlayer creaftPlayer = (CraftPlayer) player;
        EntityPlayer sp = creaftPlayer.getHandle();
        MinecraftServer server = sp.c;
        WorldServer level = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color(name));
        String[] skin = Skin.getSkin("Lendortv");
        gameProfile.getProperties().put("textures", new Property("textures", skin[0], skin[1]));
        EntityPlayer npc = new EntityPlayer(server, level, gameProfile, null);
        Location location = player.getLocation();
        npc.a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        addNPCPacket(npc);
        NPCs.add(npc);
    }
    public static void addNPCPacket(EntityPlayer npc) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            CraftPlayer creaftPlayer = (CraftPlayer) player;
            EntityPlayer sp = creaftPlayer.getHandle();
            PlayerConnection pc = sp.b;
            pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            pc.a(new PacketPlayOutNamedEntitySpawn(npc));
            pc.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.getBukkitYaw() * 256 / 360)));
            DataWatcher watcher = npc.ai();
            watcher.b(new DataWatcherObject<>(17, DataWatcherRegistry.a), (byte) 127);
            pc.a(new PacketPlayOutEntityMetadata(npc.ae(), watcher, true));
            Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, npc)), 50);
            ItemStack netheriteAxe = new ItemStack(Material.NETHERITE_AXE);
            ItemStack anotherAxe = new ItemStack(Material.NETHERITE_INGOT);
            List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> list = new ArrayList<>();
            list.add(new Pair<>(EnumItemSlot.a, CraftItemStack.asNMSCopy(netheriteAxe)));
            list.add(new Pair<>(EnumItemSlot.b, CraftItemStack.asNMSCopy(anotherAxe)));
            pc.a(new PacketPlayOutEntityEquipment(npc.ae(), list));
        }
    }
    public static void addJoinPacket(Player player) {
        for(EntityPlayer npc : NPCs) {
            CraftPlayer creaftPlayer = (CraftPlayer) player;
            EntityPlayer sp = creaftPlayer.getHandle();
            PlayerConnection pc = sp.b;
            pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            pc.a(new PacketPlayOutNamedEntitySpawn(npc));
            pc.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.getBukkitYaw() * 256 / 360)));
            DataWatcher watcher = npc.ai();
            watcher.b(new DataWatcherObject<>(17, DataWatcherRegistry.a), (byte) 127);
            pc.a(new PacketPlayOutEntityMetadata(npc.ae(), watcher, true));
            Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> pc.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, npc)), 50);
        }
    }
}
