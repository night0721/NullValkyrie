package me.night.nullvalkyrie.packets.handle;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;

public class PacketInjector {
    public void addPlayer(Player p) {
        try {
            Channel ch = me.night.nullvalkyrie.packets.protocol.Channel.getChannel(p);
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
            Channel ch = me.night.nullvalkyrie.packets.protocol.Channel.getChannel(p);
            if (ch.pipeline().get("PacketInjector") != null) {
                ch.pipeline().remove("PacketInjector");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
