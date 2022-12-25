package me.night.nullvalkyrie.packets.protocol;

import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketPlayOutEntityMetadata implements Packet {
    public PacketPlayOutEntityMetadata(Player player, Entity entity, SynchedEntityData entityData) {
        ((CraftPlayer) player).getHandle().connection.send(new ClientboundSetEntityDataPacket(entity.getBukkitEntity().getEntityId(), entityData, true));
    }
}
