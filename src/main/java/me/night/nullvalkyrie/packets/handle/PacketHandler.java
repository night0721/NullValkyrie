package me.night.nullvalkyrie.packets.handle;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.entities.npcs.NPCManager;
import me.night.nullvalkyrie.events.custom.InteractHologramEvent;
import me.night.nullvalkyrie.events.custom.RightClickNPCEvent;
import me.night.nullvalkyrie.util.Util;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PacketHandler extends ChannelDuplexHandler {
    private final Player player;

    public PacketHandler(Player player) {
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        super.write(ctx, packet, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext c, Object packet) throws Exception {
        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            ServerboundInteractPacket pk = (ServerboundInteractPacket) packet;
            int entityID = (int) Util.getFieldValue(packet, "a");
            boolean sneak = (boolean) Util.getFieldValue(packet, "c");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                    if (entity.getEntityId() == entityID && entity.getType() == EntityType.ARMOR_STAND) {
                        Bukkit.getPluginManager().callEvent(new InteractHologramEvent(player, (ArmorStand) entity));
                    }
                }
            }, 0);
            Object data = Util.getFieldValue(pk, "b");
            if (data.toString().split("\\$")[1].charAt(0) == 'e')
                return;
            try {
                Object hand = Util.getFieldValue(data, "a");
                if (!hand.toString().equals("MAIN_HAND")) return;
                //Right Click
                for (ServerPlayer npcs : NPCManager.getNPCs()) {
                    if (npcs.getBukkitEntity().getEntityId() == entityID && sneak) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> Bukkit.getPluginManager().callEvent(new RightClickNPCEvent(player, npcs)), 0);

                    }
                }
            } catch (NoSuchFieldException x) {
                //Left Click
            }
        } else {
            super.channelRead(c, packet);
        }
    }
}