package me.night.nullvalkyrie.npc;

import me.night.nullvalkyrie.util.Util;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NPCEvents implements Listener {
    @EventHandler
    public void onClick(RightClickNPC e) {
        Player player = e.getPlayer();
        if (e.getNPC().getBukkitEntity().getName().equalsIgnoreCase(Util.color("&1&lRB18"))) {
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
            PlayerConnection con = ((CraftPlayer) e.getPlayer()).getHandle().b;
            con.a(new PacketPlayOutEntityHeadRotation(npc, (byte) ((yaw % 360) * 256 / 360)));
            con.a(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.ae(), (byte) ((yaw % 360) * 256 / 360), (byte) ((pitch % 360) * 256 / 360), false));
        });
    }
}
