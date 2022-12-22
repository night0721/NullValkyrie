package me.night.nullvalkyrie.events.custom;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
public class InteractHologramEvent extends Event implements Cancellable {
    private boolean isCancelled;
    public final Player player;
    public final ArmorStand hologram;
    private static final HandlerList HANDLERS = new HandlerList();
    public InteractHologramEvent(Player player, ArmorStand hologram) {
        this.player = player;
        this.hologram = hologram;
    }
    public Player getPlayer() {
        return player;
    }
    public ArmorStand getHologram() {
        return hologram;
    }
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() { return HANDLERS; }
}
