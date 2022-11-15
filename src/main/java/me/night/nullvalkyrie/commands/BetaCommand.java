package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import org.apache.commons.lang3.time.StopWatch;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static me.night.nullvalkyrie.database.Client.getUser;

public class BetaCommand extends Command {
    private Main main;
    private BukkitScheduler scheduler;

    public BetaCommand(Main main) {
        super("beta", new String[]{"b"}, "Beta", "");
        this.main = main;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
//        getUser("Kaly15");
        if (sender instanceof Player) {
            Player pa = (Player) sender;
//            CraftPlayer cp = (CraftPlayer) sender;
//            EntityPlayer ep = cp.getHandle();
//            PacketPlayOutUpdateHealth packet = new PacketPlayOutUpdateHealth(20f, 20, 5.0f); // health, food, food saturation
//            ep.b.a(packet); // Sends the Packet

//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    pa.spigot().sendMessage(ChatMessageType.ACTION_BAR,
//                            TextComponent.fromLegacyText("§1NOT ENOUGH MANNER"));
//                }
//            }.runTaskTimer(main, 0L, 10);

            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
                int ms = 1;
                @Override
                public void run() {
                    while (true) {
                        try {
                            sleep(10); // will run every 0.1 seconds
                            long minutes = (ms / 1000) / 60;
                            long seconds = (ms / 100) % 60;
                            long milliseconds = (ms /10);
                            String secondsStr = Long.toString(seconds);
                            String secs;
                            if (secondsStr.length() >= 2) {
                                secs = secondsStr.substring(0, 2);
                            } else {
                                secs = "0" + secondsStr;
                            }
                            String minsStr = Long.toString(minutes);
                            String mins;
                            if (minsStr.length() >= 2) {
                                mins = minsStr.substring(0, 2);
                            } else {
                                mins = "0" + minsStr;
                            }
                            String sw = mins + ":" + secs + ":" + milliseconds;
                            System.out.println(sw);
                            pa.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§1"+ sw));
                            ms++;
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            }, 0L, 1);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
