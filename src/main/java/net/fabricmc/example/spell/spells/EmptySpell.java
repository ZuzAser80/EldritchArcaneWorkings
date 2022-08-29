package net.fabricmc.example.spell.spells;

import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EmptySpell extends Spell {

    public EmptySpell() {
        super(SpellType.EMPTY, SpellRank.novice, 0, 0, "None");
    }

    @Override
    public void cast(PlayerEntity user, World world) {}
}