package me.night.nullvalkyrie.entities.holograms;

import me.night.nullvalkyrie.packets.protocol.PacketPlayOutEntityMetadata;
import me.night.nullvalkyrie.packets.protocol.PacketPlayOutSpawnEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;

public class PerPlayerHologram {
    public static HashMap<Integer, ArmorStand[]> holograms = new HashMap<>();

    public PerPlayerHologram(Player player, String[] lines) {
        spawnLine(player, lines);
    }

    private void spawnLine(Player player, String[] lines) {
        double c = (lines.length) * 0.3 - 0.8;
        ArmorStand[] stands = new ArmorStand[lines.length];
        for (int i = 0; i < lines.length; i++) {
            ServerPlayer p = ((CraftPlayer) player).getHandle();
            ArmorStand stand = new ArmorStand(p.getLevel(), player.getLocation().getX(), player.getLocation().getY() + c, player.getLocation().getZ());
            stand.setInvisible(true);
            new PacketPlayOutSpawnEntity(player, stand);
            stands[i] = stand;
            SynchedEntityData watcher = stand.getEntityData();
            watcher.set(new EntityDataAccessor<>(2, EntityDataSerializers.OPTIONAL_COMPONENT), Optional.of(Component.nullToEmpty(lines[i])));
            watcher.set(new EntityDataAccessor<>(3, EntityDataSerializers.BOOLEAN), true);
            new PacketPlayOutEntityMetadata(player, stand, watcher);
            c -= 0.3;
            if (lines.length == i + 1)
                holograms.put(stand.getBukkitEntity().getEntityId(), stands);
        }
    }

    public static HashMap<Integer, ArmorStand[]> getHolograms() {
        return holograms;
    }
}
