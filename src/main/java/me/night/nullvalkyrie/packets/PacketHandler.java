package me.night.nullvalkyrie.packets;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.entities.npcs.NPCManager;
import me.night.nullvalkyrie.events.custom.InteractHologramEvent;
import me.night.nullvalkyrie.events.custom.RightClickNPCEvent;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PacketHandler extends ChannelDuplexHandler {
    private final Player player;

    public PacketHandler(Player player) {
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext c, Object packet) throws Exception {
        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            PacketPlayInUseEntity pk = (PacketPlayInUseEntity) packet;
            int entityID = (int) PacketInjector.getFieldValue(packet, "a");
            boolean sneak = (boolean) PacketInjector.getFieldValue(packet, "c");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                @Override
                public void run() {
                    for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                        if (entity.getEntityId() == entityID && entity.getType() == EntityType.ARMOR_STAND){
                            Bukkit.getPluginManager().callEvent(new InteractHologramEvent(player, (ArmorStand) entity));
                        }
                    }
                }
            }, 0);

            Field type = pk.getClass().getDeclaredField("b");
            type.setAccessible(true);
            Object data = type.get(pk);
            if (data.toString().split("\\$")[1].charAt(0) == 'e') {
                return;
            }
            try {
                Field hand = data.getClass().getDeclaredField("a");
                hand.setAccessible(true);
                if (!hand.get(data).toString().equals("MAIN_HAND")) {
                    return;
                }
                //Right Click
                for (EntityPlayer npcs : NPCManager.getNPCs()) {
                    if (npcs.ae() == entityID && sneak) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.getPluginManager().callEvent(new RightClickNPCEvent(player, npcs));
                            }
                        }, 0);

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