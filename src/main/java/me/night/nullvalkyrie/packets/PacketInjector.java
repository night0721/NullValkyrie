package me.night.nullvalkyrie.packets;

import io.netty.channel.Channel;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketInjector {
    public void addPlayer(Player p) {
        try {
            Channel ch = ((CraftPlayer) p).getHandle().connection.getConnection().channel;
            if (ch.pipeline().get("PacketInjector") == null) {
                PacketHandler h = new PacketHandler(p);
                ch.pipeline().addBefore("packet_handler", "PacketInjector", h);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void removePlayer(Player p) {
        try {
            Channel ch = ((CraftPlayer) p).getHandle().connection.getConnection().channel; // NMS: 1.19.2 https://nms.screamingsandals.org/1.19.2/net/minecraft/server/network/ServerGamePacketListenerImpl.html PlayerConnection -> NetworkManager -> Channel
            if (ch.pipeline().get("PacketInjector") != null) {
                ch.pipeline().remove("PacketInjector");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
