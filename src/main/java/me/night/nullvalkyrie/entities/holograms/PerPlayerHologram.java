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
        EntityPlayer splayer = ((CraftPlayer) player).getHandle();
        EntityArmorStand stand = new EntityArmorStand(splayer.s, player.getLocation().getX(), y, player.getLocation().getZ()); // creating armor stand by location and s(stands for nms world)
        stand.j(true); // set invisible
        PlayerConnection connection = splayer.b; //connection
        connection.a(new PacketPlayOutSpawnEntity(stand)); // sending packet to spawn the armor stand
        DataWatcher watcher = stand.ai(); // ai = getDataWatcher
        Optional<IChatBaseComponent> optional = Optional.of(IChatBaseComponent.a(line)); //according to wiki.vg, setting the custom name requires Optional<ChatComponent> if you are using mojang mappings, however i cant find out what a stands for, just find a method that returns ChatComponent which takes string as a parameter
        watcher.b(new DataWatcherObject<>(2, DataWatcherRegistry.f), optional); // f = ChatComponent, here we putting the optional<ChatComponent> into the datawatcher
        watcher.b(new DataWatcherObject<>(3, DataWatcherRegistry.i), true); // i = boolean, we setting show custom name to true
        // watcher.b = set
        connection.a(new PacketPlayOutEntityMetadata(stand.ae(), watcher, true)); // sending packet to update the metadata, ae=getEntityId
    }
}
