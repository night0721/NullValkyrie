package me.night.nullvalkyrie.packets.protocol;

import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class PacketPlayOutEntityMetadata implements Packet {
    public PacketPlayOutEntityMetadata(Player player, Entity entity, List<SynchedEntityData.DataValue<?>> list) {
        ((CraftPlayer) player).getHandle().connection.send(new ClientboundSetEntityDataPacket(entity.getBukkitEntity().getEntityId(), list));
    }
}
