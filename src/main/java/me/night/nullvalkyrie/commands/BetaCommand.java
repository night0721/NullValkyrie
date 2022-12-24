package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.entities.npcs.NPCManager;
import me.night.nullvalkyrie.entities.pets.ZombiePet;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BetaCommand extends Command {

    public BetaCommand() {
        super("beta", new String[]{"b"}, "Beta", "");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            ZombiePet a = new ZombiePet(player.getLocation(), player);
            ((CraftWorld) player.getWorld()).getHandle().addFreshEntity(a);
            ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setCustomModelData(1010101);
            item.setItemMeta(itemMeta);
            player.getInventory().addItem(item);
            ItemStack item2 = new ItemStack(Material.GOLDEN_SWORD);
            ItemMeta itemMeta2 = item2.getItemMeta();
            assert itemMeta2 != null;
            itemMeta2.setCustomModelData(1010101);
            item2.setItemMeta(itemMeta2);
            player.getInventory().addItem(item2);
            NPCManager.createNPC(player, args[0]);
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}