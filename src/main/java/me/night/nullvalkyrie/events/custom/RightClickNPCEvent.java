package me.night.nullvalkyrie.events.custom;

import net.minecraft.server.level.ServerPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RightClickNPCEvent extends Event implements Cancellable {
    private final Player player;
    private final ServerPlayer npc;
    private boolean isCancelled;
    private static final HandlerList HANDLERS = new HandlerList();
    public RightClickNPCEvent(Player player, ServerPlayer npc) {
        this.player = player;
        this.npc = npc;
    }
    public Player getPlayer() {
        return player;
    }
    public ServerPlayer getNPC() {
        return npc;
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
