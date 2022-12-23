package me.night.nullvalkyrie.entities.pets;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

public class ZombiePet extends Zombie {
    public ZombiePet(Location location, Player player) {
        super(EntityType.ZOMBIE, ((CraftWorld) location.getWorld()).getHandle());
        this.setBaby(true); // https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/monster/Zombie.html setBaby
        this.setInvulnerable(true);
        this.setPos(location.getX(), location.getY(), location.getZ()); // https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/Entity.html setPos
        this.setCustomName(Component.nullToEmpty(ChatColor.DARK_PURPLE + player.getName() + "'s Zombie")); //https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/Entity.html setCustomName
        this.setCustomNameVisible(true); // https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/Entity.html setCustomNameVisible
        this.setTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true); // https://nms.screamingsandals.org/1.19.2/net/minecraft/world/entity/monster/Zombie.html setGoalTarget
    }
    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new PathFinderGoalPet(this, 1.0D, 15F));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 8.0F));
    }
}
