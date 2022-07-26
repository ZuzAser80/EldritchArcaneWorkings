package net.fabricmc.eaw.spell.spells;

import net.fabricmc.eaw.spell.Spell;
import net.fabricmc.eaw.spell.SpellRank;
import net.fabricmc.eaw.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class LeapSpell extends Spell {
    public LeapSpell() {
        super(SpellType.AIR, SpellRank.average, 20, 1, "Leap");
    }
    @Override
    public void cast(PlayerEntity user, World world) {
        if(!world.isClient) {
            //spawn particles
            ServerWorld server = (ServerWorld)world;
            for (int i = 0; i < 20; ++i) {
                double motionX = user.getRandom().nextGaussian() * 0.02;
                double motionY = user.getRandom().nextGaussian() * 0.02 + 0.20;
                double motionZ = user.getRandom().nextGaussian() * 0.02;
                server.spawnParticles(ParticleTypes.POOF, user.getX(), user.getY(), user.getZ(), 1, motionX, motionY, motionZ, 0.15);
            }
            //move player
            float f = user.getYaw();
            float g = user.getPitch();
            float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            user.addVelocity((double)h * 1.5, 1.5, (double)l * 1.5);
            user.velocityModified = true;
        }
    }
}
