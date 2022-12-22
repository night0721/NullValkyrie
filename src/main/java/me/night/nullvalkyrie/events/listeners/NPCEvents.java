package me.night.nullvalkyrie.events.listeners;

import me.night.nullvalkyrie.events.custom.RightClickNPCEvent;
import me.night.nullvalkyrie.entities.npcs.NPCManager;
import me.night.nullvalkyrie.util.Util;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NPCEvents implements Listener {
    @EventHandler
    public void onClick(RightClickNPCEvent e) {
        Player player = e.getPlayer();
        if (e.getNPC().getBukkitEntity().getName().contains("SAI")) {
            player.sendMessage(Util.color("Hi"));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        NPCManager.getNPCs().forEach(npc -> {
            Location location = npc.getBukkitEntity().getLocation();
            location.setDirection(e.getPlayer().getLocation().subtract(location).toVector());
            float yaw = location.getYaw();
            float pitch = location.getPitch();
            ServerGamePacketListenerImpl con = ((CraftPlayer) e.getPlayer()).getHandle().connection;
            con.send(new ClientboundRotateHeadPacket(npc, (byte) ((yaw % 360) * 256 / 360)));
            con.send(new ClientboundMoveEntityPacket.Rot(npc.getBukkitEntity().getEntityId(), (byte) ((yaw % 360) * 256 / 360), (byte) ((pitch % 360) * 256 / 360), false));
        });
    }
}
