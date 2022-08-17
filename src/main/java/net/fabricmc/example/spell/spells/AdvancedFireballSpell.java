package net.fabricmc.example.spell.spells;

import net.fabricmc.example.entity.fire.AdvancedFireballEntity;
import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class AdvancedFireballSpell extends Spell {

    public AdvancedFireballSpell() {
        super(SpellType.FIRE, SpellRank.NOVICE, 20, 5, "Advanced Fireball");
    }

    @Override
    public void cast(PlayerEntity user, World world) {
        AdvancedFireballEntity fireballEntity = new AdvancedFireballEntity(world);
        fireballEntity.setPosition(user.getX(), user.getY() + 0.5, user.getZ());
        fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 1, 1);
        fireballEntity.setNoGravity(true);
        world.spawnEntity(fireballEntity);
    }
}
