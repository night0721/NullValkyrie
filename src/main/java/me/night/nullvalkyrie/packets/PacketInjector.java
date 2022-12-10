package me.night.nullvalkyrie.packets;

import io.netty.channel.Channel;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketInjector {
    public void addPlayer(Player p) {
        try {
            Channel ch = nms(p).b.b.m;
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
            Channel ch = nms(p).b.b.m;
            if (ch.pipeline().get("PacketInjector") != null) {
                ch.pipeline().remove("PacketInjector");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static EntityPlayer nms(Player p) {
        return ((CraftPlayer) p).getHandle();
    }
}
