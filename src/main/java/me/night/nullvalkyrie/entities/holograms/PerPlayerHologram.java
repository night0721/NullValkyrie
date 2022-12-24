package me.night.nullvalkyrie.entities.holograms;

import me.night.nullvalkyrie.packets.protocol.PacketPlayOutEntityMetadata;
import me.night.nullvalkyrie.packets.protocol.PacketPlayOutSpawnEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PerPlayerHologram {
    public PerPlayerHologram(Player player, String[] lines) {
        double c = (lines.length) * 0.3 - 0.15;
        for (String line : lines) {
            spawnLine(player.getLocation().getY() + c, player, line);
            c -= 0.3;
        }
    }
    private void spawnLine(double y, Player player, String line) {
        ServerPlayer p = ((CraftPlayer) player).getHandle();
        ArmorStand stand = new ArmorStand(p.level, player.getLocation().getX(), y, player.getLocation().getZ());
        stand.setInvisible(true);
        new PacketPlayOutSpawnEntity(player, stand);
        SynchedEntityData watcher = stand.getEntityData();
        watcher.set(new EntityDataAccessor<>(2, EntityDataSerializers.OPTIONAL_COMPONENT), Optional.of(Component.nullToEmpty(line)));
        watcher.set(new EntityDataAccessor<>(3, EntityDataSerializers.BOOLEAN), true);
        new PacketPlayOutEntityMetadata(player, stand, watcher.getNonDefaultValues());
    }
}
