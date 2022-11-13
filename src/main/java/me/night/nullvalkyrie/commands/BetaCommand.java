package me.night.nullvalkyrie.commands;

import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

import static me.night.nullvalkyrie.database.Client.getUser;

public class BetaCommand extends Command {
    public BetaCommand() {
        super(
                "beta",
                new String[]{"b"},
                "Beta",
                ""
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        getUser("Kaly15");
        if(sender instanceof Player) {
            CraftPlayer p = (CraftPlayer) sender;
            ClientboundSetHealthPacket packet = new ClientboundSetHealthPacket(19.5f, 20, 0.0f);
            p.getHandle().connection.send(packet);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
