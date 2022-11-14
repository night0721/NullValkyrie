package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static me.night.nullvalkyrie.database.Client.getUser;

public class BetaCommand extends Command {
    private Main main;

    public BetaCommand(Main main) {
        super("beta", new String[]{"b"}, "Beta", "");
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        getUser("Kaly15");
        if (sender instanceof Player) {
            Player pa = (Player) sender;
            CraftPlayer cp = (CraftPlayer) sender;
            EntityPlayer ep = cp.getHandle();
            Entity e = (Entity) ep;
            PacketPlayOutUpdateHealth packet = new PacketPlayOutUpdateHealth(20f, 20, 5.0f); // health, food, food
            // saturation
            ep.b.a(packet); // Sends the Packet

            new BukkitRunnable() {
                @Override
                public void run() {
                    pa.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            TextComponent.fromLegacyText("§1NOT ENOUGH MANNER"));
                }
            }.runTaskTimer(main, 0L, 10);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
