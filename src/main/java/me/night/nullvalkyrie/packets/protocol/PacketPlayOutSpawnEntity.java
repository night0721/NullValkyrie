package me.night.nullvalkyrie.packets.protocol;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketPlayOutSpawnEntity implements Packet {
    public PacketPlayOutSpawnEntity(Player player, Entity entity) {
        ClientboundAddEntityPacket packet = new ClientboundAddEntityPacket(entity);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }
}
