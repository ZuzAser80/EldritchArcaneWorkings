package net.fabricmc.eaw.spell.spells;

import net.fabricmc.eaw.entity.fire.AdvancedFireballEntity;
import net.fabricmc.eaw.spell.Spell;
import net.fabricmc.eaw.spell.SpellRank;
import net.fabricmc.eaw.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class AdvancedFireballSpell extends Spell {

    public AdvancedFireballSpell() {
        super(SpellType.FIRE, SpellRank.average, 20, 5, "Advanced Fireball");
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
