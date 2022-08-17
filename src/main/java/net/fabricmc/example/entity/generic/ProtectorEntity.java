package net.fabricmc.example.entity.generic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class ProtectorEntity extends HostileEntity {

    protected ProtectorEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initCustomGoals() {
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge(ZombifiedPiglinEntity.class));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DataTracker.registerData(ProtectorEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID), Optional.empty());
    }

    @Override
    protected void initGoals() {
        //this.goalSelector.add(6, new FollowZombieSummonerGoal(this, this.getSummoner(), this.world, 1.0, this.getNavigation(), 90.0F, 3.0F, true));
        this.initCustomGoals();
    }


    private void setSummonerUuid(UUID uuid) {
        this.dataTracker.set(DataTracker.registerData(ProtectorEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID), Optional.ofNullable(uuid));
    }

    public Optional<UUID> getSummonerUuid() {
        return this.dataTracker.get(DataTracker.registerData(ProtectorEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID));
    }

    public void setSummoner(Entity player) {
        this.setSummonerUuid(player.getUuid());
    }



    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putUuid("SummonerUUID", getSummonerUuid().get());
    }


    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        UUID id;
        if (tag.contains("SummonerUUID")) {
            id = tag.getUuid("SummonerUUID");
        } else {
            id = tag.getUuid("SummonerUUID");
        }
        if (id != null) {
            this.setSummonerUuid(tag.getUuid("SummonerUUID"));
        }
    }

    public static DefaultAttributeContainer.Builder createProtectorAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D).add(EntityAttributes.GENERIC_ARMOR, 2.0D).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    @Override
    public void tickMovement() {
        if (this.isAlive()) {
            if (getSummoner() != null) {
                if (getSummoner().getAttacker() != null) {
                    this.setTarget(getSummoner().getAttacker());
                } else if (getSummoner().getAttacking() != null) {
                    this.setTarget(getSummoner().getAttacking());
                }
            }
        }
        super.tickMovement();
    }

    @Override
    public void setAttacker(@Nullable LivingEntity attacker) {
        if(attacker == getSummoner()) {
            return;
        }
        super.setAttacker(attacker);
    }

    public LivingEntity getSummoner() {
        try {
            Optional<UUID> uUID = this.getSummonerUuid();
            return uUID.map(value -> this.world.getPlayerByUuid(value)).orElse(null);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }
}