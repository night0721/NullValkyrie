package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.database.CustomWeaponsDataManager;
import me.night.nullvalkyrie.items.CustomItemManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.*;

public class WeaponCommand extends Command {
    public WeaponCommand() {
        super("weapon", new String[]{}, "Give you a weapon", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        StringBuilder builder = new StringBuilder();
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "This item doesn't exist");
        } else {
            List<String> arglist = Arrays.asList(args);
            for (String arg : args) {
                if (arg.equals(arglist.get(arglist.size() - 1))) {
                    builder.append(arg);
                } else {
                    builder.append(arg);
                    builder.append(" ");
                }
            }
            ItemStack item = CustomItemManager.produceItem(builder.toString());
            if (item.hasItemMeta()) {
                player.getInventory().addItem(item);
            } else {
                player.sendMessage(ChatColor.RED + "This item doesn't exist");
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            HashMap<String, Object> hh = CustomWeaponsDataManager.getWeapons();
            ArrayList<String> cc = new ArrayList<>();
            for (String s : hh.keySet()) {
                HashMap<String, Object> item = (HashMap<String, Object>) hh.get(s);
                if (Objects.equals(item.get("Type"), "Weapon")) {
                    cc.add((String) item.get("Name"));
                }
            }
            return StringUtil.copyPartialMatches(args[0], cc, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
