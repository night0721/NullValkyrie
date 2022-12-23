package me.night.nullvalkyrie.entities.pets;

import java.util.EnumSet;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;

public class PathFinderGoalPet extends Goal {

    private final Mob pet; // our pet
    private LivingEntity owner; // owner

    private final double f; // pet's speed
    private final float g; // distance between owner & pet

    private double c; // x
    private double d; // y
    private double e; // z


    public PathFinderGoalPet(Mob mob, double speed, float distance) {
        this.pet = mob;
        this.f = speed;
        this.g = distance;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        this.owner = this.pet.getTarget();
        if (this.owner == null) return false;
        else if (this.pet.getDisplayName() == null) return false;
        else if (!this.pet.getDisplayName().getString().contains(this.owner.getName().getString())) return false;
        else if (this.owner.distanceToSqr(this.pet) > (double) (this.g * this.g)) {
            pet.getBukkitEntity().teleport(this.owner.getBukkitEntity().getLocation());
            return false;
        } else {
            this.c = this.owner.getX();
            this.d = this.owner.getY();
            this.e = this.owner.getZ();
            return true;
        }
    }

    @Override
    public void start() {
        this.pet.getNavigation().moveTo(this.c, this.d, this.e, this.f);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.pet.getNavigation().isDone() && this.owner.distanceToSqr(this.pet) < (double) (this.g * this.g);
    }

    @Override
    public void stop() {
        this.owner = null;
    }

}