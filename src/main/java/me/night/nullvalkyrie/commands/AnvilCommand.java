package me.night.nullvalkyrie.commands;

import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Containers;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftChatMessage;

import org.bukkit.entity.Player;

import java.util.List;

public class AnvilCommand extends Command {

    public AnvilCommand() {
        super(
                "anvil",
                new String[]{"av"},
                "Open anvil",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CraftPlayer craftPlayer = (CraftPlayer) player;
//            EntityPlayer entityPlayer = craftPlayer.getHandle();
//            int id = 0;
//            PacketPlayOutOpenWindow OpenWindow = new PacketPlayOutOpenWindow(id, Containers.h, CraftChatMessage.fromStringOrNull("Test"));
//            PlayerConnection playerConnection = entityPlayer.b;
//            playerConnection.a(OpenWindow);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}