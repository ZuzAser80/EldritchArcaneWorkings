package net.fabricmc.example.spell.spells;

import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.world.World;

public class SnowballSpell extends Spell {
    public SnowballSpell() {
        super(SpellType.WATER, SpellRank.novice, 20, 5, "Snowball");
    }

    @Override
    public void cast(PlayerEntity user, World world) {
        SnowballEntity fireballEntity = new SnowballEntity(world, user);
        fireballEntity.setPosition(user.getX(), user.getY() + 0.5, user.getZ());
        fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 1, 1);
        world.spawnEntity(fireballEntity);
    }
}
