package me.night.nullvalkyrie.entities.pets;

import java.util.EnumSet;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;

public class PathFinderGoalPet extends Goal {
    private final Mob pet; // our pet
    private LivingEntity owner; // owner
    private final double speed; // pet's speed
    private final float distance; // distance between owner & pet
    private double x; // x
    private double y; // y
    private double z; // z


    public PathFinderGoalPet(Mob mob, double speed, float distance) {
        this.pet = mob;
        this.speed = speed;
        this.distance = distance;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        this.owner = this.pet.getTarget();
        if (this.owner == null) return false;
        else if (this.pet.getDisplayName() == null) return false;
        else if (!this.pet.getDisplayName().getString().contains(this.owner.getName().getString())) return false;
        else if (this.owner.distanceToSqr(this.pet) > (double) (this.distance * this.distance)) {
            pet.getBukkitEntity().teleport(this.owner.getBukkitEntity().getLocation());
            return false;
        } else {
            this.x = this.owner.getX();
            this.y = this.owner.getY();
            this.z = this.owner.getZ();
            return true;
        }
    }

    @Override
    public void start() {
        this.pet.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.pet.getNavigation().isDone() && this.owner.distanceToSqr(this.pet) < (double) (this.distance * this.distance);
    }

    @Override
    public void stop() {
        this.owner = null;
    }

}