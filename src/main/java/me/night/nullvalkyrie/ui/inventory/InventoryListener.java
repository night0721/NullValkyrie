package me.night.nullvalkyrie.ui.inventory;

import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.database.UserDataManager;
import me.night.nullvalkyrie.enums.Items;
import me.night.nullvalkyrie.util.RandomCollection;
import me.night.nullvalkyrie.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@SuppressWarnings("ConstantConditions")
public class InventoryListener implements Listener {
    public static RandomCollection<String> randomCollection;

    public InventoryListener() {
        randomCollection = new RandomCollection<>();
        for (Items e : Items.values()) {
            randomCollection.add(e.getWeight(), e.getName());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getView().getTitle().equals(Menu.title)) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()) {
                case 0:
                    break;
                case 20:
                    player.setHealth(0);
                    player.sendMessage(ChatColor.RED + "又做兵 抵死");
                    break;
                case 22:
                    player.teleport(player.getWorld().getSpawnLocation());
                    break;
                case 24:
                    player.closeInventory();
                    player.openInventory(Shop.GUI);
                    return;
                default:
                    return;
            }
            player.closeInventory();
        }
        if (e.getView().getTitle().equals(Shop.title)) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getRawSlot() == 0) {
                player.closeInventory();
            }
        }
        if (e.getView().getTitle().equals(LuckyDraw.title)) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getRawSlot() == 0) {
                player.closeInventory();
            } else if (e.getRawSlot() == 22) {
                if (randomCollection.getAll().size() == 0) {
                    player.closeInventory();
                    player.sendMessage(ChatColor.RED + "You already got all the rewards!");
                    return;
                }
                new UserDataManager().updateUserBank(player.getUniqueId().toString(), -100);
                List<String> colors = List.of("WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "PINK", "GRAY", "LIGHT_GRAY", "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", "RED", "BLACK");
                List<String> slot1 = new ArrayList<>(colors);
                List<String> slot2 = new ArrayList<>(colors);
                List<String> slot3 = new ArrayList<>(colors);
                List<String> slot4 = new ArrayList<>(colors);
                List<String> slot5 = new ArrayList<>(colors);
                List<String> slot6 = new ArrayList<>(colors);
                List<String> slot7 = new ArrayList<>(colors);
                List<String> slot8 = new ArrayList<>(colors);
                Collections.shuffle(slot1);
                Collections.shuffle(slot2);
                Collections.shuffle(slot3);
                Collections.shuffle(slot4);
                Collections.shuffle(slot5);
                Collections.shuffle(slot6);
                Collections.shuffle(slot7);
                Collections.shuffle(slot8);
                int[] slots = new int[]{11, 13, 15, 20, 24, 29, 31, 33};
                new BukkitRunnable() {
                    int i = 0;
                    int ii = 0;
                    int time = 0;

                    @Override
                    public void run() {
                        if (colors.size() - 1 <= i) i = 0;
                        if (ii == 8) ii = 0;
                        if (time == 20) {
                            cancel();
                            return;
                        }
                        for (int slot : slots) {
                            if (slot == 11) {
                                ItemStack item = new ItemStack(Material.valueOf(slot1.get(i) + "_STAINED_GLASS_PANE"), 1);
                                LuckyDraw.GUI.setItem(slot, item);
                            }
                            if (slot == 13) {
                                ItemStack item = new ItemStack(Material.valueOf(slot2.get(i) + "_STAINED_GLASS_PANE"), 1);
                                LuckyDraw.GUI.setItem(slot, item);
                            }
                            if (slot == 15) {
                                ItemStack item = new ItemStack(Material.valueOf(slot3.get(i) + "_STAINED_GLASS_PANE"), 1);
                                LuckyDraw.GUI.setItem(slot, item);
                            }
                            if (slot == 20) {
                                ItemStack item = new ItemStack(Material.valueOf(slot4.get(i) + "_STAINED_GLASS_PANE"), 1);
                                LuckyDraw.GUI.setItem(slot, item);
                            }
                            if (slot == 24) {
                                ItemStack item = new ItemStack(Material.valueOf(slot5.get(i) + "_STAINED_GLASS_PANE"), 1);
                                LuckyDraw.GUI.setItem(slot, item);
                            }
                            if (slot == 29) {
                                ItemStack item = new ItemStack(Material.valueOf(slot6.get(i) + "_STAINED_GLASS_PANE"), 1);
                                LuckyDraw.GUI.setItem(slot, item);
                            }
                            if (slot == 31) {
                                ItemStack item = new ItemStack(Material.valueOf(slot7.get(i) + "_STAINED_GLASS_PANE"), 1);
                                LuckyDraw.GUI.setItem(slot, item);
                            }
                            if (slot == 33) {
                                ItemStack item = new ItemStack(Material.valueOf(slot8.get(i) + "_STAINED_GLASS_PANE"), 1);
                                LuckyDraw.GUI.setItem(slot, item);
                            }
                        }
                        i++;
                        ii++;
                        time++;
                    }
                }.runTaskTimer(Main.getPlugin(Main.class), 1L, 5L);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (int slot : slots) {
                            LuckyDraw.GUI.setItem(slot, new ItemStack(Material.AIR));
                        }
                        String s = randomCollection.getRandom();
                        if (s != null) {
                            randomCollection.remove(s);
                            int slot = 0;
                            for (Items e : Items.values())
                                if (e.getName().equals(s))
                                    slot = e.getSlot();
                            LuckyDraw.GUI.remove(Items.getByName(s).getMaterial());
                            ItemStack got = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                            ItemMeta gotmeta = got.getItemMeta();
                            gotmeta.setDisplayName(ChatColor.RED + "You already got this reward!");
                            got.setItemMeta(gotmeta);
                            LuckyDraw.GUI.setItem(slot, got);
                            for (String s1 : randomCollection.getAll()) {
                                ItemStack item = new ItemStack(Items.getByName(s1).getMaterial());
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + Items.getByName(s1).getName());
                                List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
                                lore.add(0, "");
                                lore.add(1, Util.color("&bChance: " + randomCollection.getChance(s1) + "%"));
                                lore.add(2, Items.getByName(s1).getRarity().getDisplay());
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                LuckyDraw.GUI.setItem(Items.getByName(s1).getSlot(), item);
                            }
                            Items it = Items.getByName(s);
                            ItemStack item = new ItemStack(it.getMaterial(), 1);
                            ItemMeta meta = item.getItemMeta();
                            if (meta == null) return;
                            meta.setDisplayName(ChatColor.GOLD + it.getName());
                            meta.setLore(List.of(it.getRarity().getDisplay()));
                            item.setItemMeta(meta);
                            player.getInventory().addItem(item);
                        } else player.closeInventory();
                    }
                }.runTaskLater(Main.getPlugin(Main.class), 5L * 20L);

            }
        }
    }
}
