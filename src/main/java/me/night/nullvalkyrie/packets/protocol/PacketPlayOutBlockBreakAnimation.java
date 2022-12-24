package me.night.nullvalkyrie.packets.protocol;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketPlayOutBlockBreakAnimation implements Packet {
    public PacketPlayOutBlockBreakAnimation(Player player, Location x, int stage) {
        ClientboundBlockDestructionPacket packet = new ClientboundBlockDestructionPacket(player.getEntityId(), new BlockPos(x.getBlockX(), x.getBlockY(), x.getBlockZ()), stage);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }
}
