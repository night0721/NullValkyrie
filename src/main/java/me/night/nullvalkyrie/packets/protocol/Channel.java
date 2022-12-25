package me.night.nullvalkyrie.packets.protocol;

import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Channel {
    public static io.netty.channel.Channel getChannel(Player player) {
        return ((CraftPlayer) player).getHandle().connection.getConnection().channel; // NMS: 1.19.2 https://nms.screamingsandals.org/1.19.2/net/minecraft/server/network/ServerGamePacketListenerImpl.html PlayerConnection -> NetworkManager -> Channel
    }
}
