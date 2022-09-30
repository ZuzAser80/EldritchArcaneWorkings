package net.fabricmc.eaw.spell.spells;

import net.fabricmc.eaw.entity.water.AdvancedSnowballEntity;
import net.fabricmc.eaw.spell.Spell;
import net.fabricmc.eaw.spell.SpellRank;
import net.fabricmc.eaw.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class AdvancedSnowballSpell extends Spell {

    public AdvancedSnowballSpell() {
        super(SpellType.WATER, SpellRank.average, 20, 10, "Advanced Snowball");
    }

    @Override
    public void cast(PlayerEntity user, World world) {
        AdvancedSnowballEntity fireballEntity = new AdvancedSnowballEntity(world);
        fireballEntity.setPosition(user.getX(), user.getY() + 0.5, user.getZ());
        fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 1, 1);
        fireballEntity.setNoGravity(true);
        world.spawnEntity(fireballEntity);
    }
}
