package net.fabricmc.example.spell.spells;

import net.fabricmc.example.entity.generic.LandmineEntity;
import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class FireLandmineSpell extends Spell {

    public FireLandmineSpell() {
        super(SpellType.FIRE, SpellRank.novice, 0, 1, "Fire Landmine");
    }

    public void cast(PlayerEntity user, World world) {
        LandmineEntity landmineEntity = new LandmineEntity(world, this);
        landmineEntity.setNoGravity(false);
        landmineEntity.setPos(user.getX(), user.getY(), user.getZ());
        world.spawnEntity(landmineEntity);
    }
}
