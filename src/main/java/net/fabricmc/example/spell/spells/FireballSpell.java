package net.fabricmc.example.spell.spells;

import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.world.World;

public class FireballSpell extends Spell {
    public FireballSpell() {
        super(SpellType.FIRE, SpellRank.novice, 20, 5, "Fireball");
    }

    @Override
    public void cast(PlayerEntity user, World world) {
        SmallFireballEntity fireballEntity = new SmallFireballEntity(world, user, user.getPitch(), user.getYaw(), 0);
        fireballEntity.setPosition(user.getX(), user.getY() + 0.5, user.getZ());
        fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 1, 1);
        world.spawnEntity(fireballEntity);
    }
}
