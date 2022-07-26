package net.fabricmc.eaw.spell.spells;

import net.fabricmc.eaw.entity.misc.AbstractParticleLeavingEntity;
import net.fabricmc.eaw.spell.Spell;
import net.fabricmc.eaw.spell.SpellRank;
import net.fabricmc.eaw.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class FlameSpell extends Spell {
    public FlameSpell() {
        super(SpellType.FIRE, SpellRank.novice, 0, 1, "Flame");
    }
    @Override
    public void cast(PlayerEntity user, World world) {
        AbstractParticleLeavingEntity fireballEntity = new AbstractParticleLeavingEntity(world, ParticleTypes.FLAME, true);
        fireballEntity.setPosition(user.getX(), user.getY() + 0.5, user.getZ());
        fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 1, 1);
        fireballEntity.setNoGravity(true);
        world.spawnEntity(fireballEntity);
    }
}
