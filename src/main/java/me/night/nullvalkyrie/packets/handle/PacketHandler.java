package me.night.nullvalkyrie.packets.handle;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.night.nullvalkyrie.NullValkyrie;
import me.night.nullvalkyrie.entities.holograms.PerPlayerHologram;
import me.night.nullvalkyrie.entities.npcs.NPCManager;
import me.night.nullvalkyrie.events.custom.InteractHologramEvent;
import me.night.nullvalkyrie.events.custom.RightClickNPCEvent;
import me.night.nullvalkyrie.util.Util;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PacketHandler extends ChannelDuplexHandler {
    private final Player player;

    public PacketHandler(Player player) {
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayOutEntityMetadat")) {
            ClientboundSetEntityDataPacket pk = (ClientboundSetEntityDataPacket) packet;
            int entityID = pk.getId();
            final Entity[] entity = {null};
            // get entity from id
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Entity e : Bukkit.getWorld("world").getEntities()) {
                        if (e.getEntityId() == entityID && e.getType() == EntityType.PLAYER) {
                            entity[0] = e;
                        }
                    }
                }
            }.runTaskLater(NullValkyrie.getPlugin(NullValkyrie.class), 0);
            if (entity[0] == null) return;
            List<SynchedEntityData.DataItem<?>> list = pk.getUnpackedData();
            SynchedEntityData.DataItem<Float> value = (SynchedEntityData.DataItem<Float>) list.get(9);
            System.out.println(value.getAccessor());
            float health = ThreadLocalRandom.current().nextInt(5, 20);
            list.set(9, new SynchedEntityData.DataItem<>(new EntityDataAccessor<>(value.getAccessor().getId(), EntityDataSerializers.FLOAT), health));
        }
        super.write(ctx, packet, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext c, Object packet) throws Exception {
        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            ServerboundInteractPacket pk = (ServerboundInteractPacket) packet;
            int entityID = (int) Util.getFieldValue(packet, "a");
//            if (pk.getType() == ServerboundInteractPacket.Action.INTERACT) {
//                Entity entity = ((CraftWorld) player.getWorld()).getHandle().getEntity(entityID);
//                if (entity == null) return;
//                if (entity instanceof ArmorStand) {
//                    Bukkit.getPluginManager().callEvent(new InteractHologramEvent(player, entity));
//                }
//                if (NPCManager.isNPC(entity)) {
//                    Bukkit.getPluginManager().callEvent(new RightClickNPCEvent(player, entity));
//                }
//            }
            boolean sneak = (boolean) Util.getFieldValue(packet, "c");
            Bukkit.getScheduler().scheduleSyncDelayedTask(NullValkyrie.getPlugin(NullValkyrie.class), () -> {
                net.minecraft.world.entity.decoration.ArmorStand[] stands = PerPlayerHologram.getHolograms().get(entityID);
                if (stands == null) return;
                Bukkit.getPluginManager().callEvent(new InteractHologramEvent(player, (ArmorStand) stands[stands.length - 1].getBukkitEntity()));
                for (net.minecraft.world.entity.decoration.ArmorStand i : stands) {
                    ((CraftPlayer) player).getHandle().connection.send(new ClientboundRemoveEntitiesPacket(i.getId()));
                }
            }, 0);
            Object data = Util.getFieldValue(pk, "b");
            if (data.toString().split("\\$")[1].charAt(0) == 'e')
                return;
            try {
                Object hand = Util.getFieldValue(data, "a");
                if (!hand.toString().equals("MAIN_HAND")) return;
                //Right Click
                for (ServerPlayer npcs : NPCManager.getNPCs().values()) {
                    if (npcs.getBukkitEntity().getEntityId() == entityID && sneak) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(NullValkyrie.getPlugin(NullValkyrie.class), () -> Bukkit.getPluginManager().callEvent(new RightClickNPCEvent(player, npcs)), 0);

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