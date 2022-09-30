package net.fabricmc.eaw.spell.spells;

import net.fabricmc.eaw.spell.Spell;
import net.fabricmc.eaw.spell.SpellRank;
import net.fabricmc.eaw.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EmptySpell extends Spell {

    public EmptySpell() {
        super(SpellType.EMPTY, SpellRank.novice, 0, 0, "None");
    }

    @Override
    public void cast(PlayerEntity user, World world) {}
}