package me.night.nullvalkyrie.entities.holograms;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
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
        EntityPlayer p = ((CraftPlayer) player).getHandle();
        EntityArmorStand stand = new EntityArmorStand(p.s, player.getLocation().getX(), y, player.getLocation().getZ());
        stand.j(true);
        PlayerConnection connection = p.b;
        connection.a(new PacketPlayOutSpawnEntity(stand));
        DataWatcher watcher = stand.ai();
        Optional<IChatBaseComponent> optional = Optional.of(IChatBaseComponent.a(line));
        watcher.b(new DataWatcherObject<>(2, DataWatcherRegistry.f), optional);
        watcher.b(new DataWatcherObject<>(3, DataWatcherRegistry.i), true);
        connection.a(new PacketPlayOutEntityMetadata(stand.ae(), watcher, true));
    }
}
