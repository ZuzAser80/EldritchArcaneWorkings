package net.fabricmc.example.spell.spells;

import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class EmptySpell extends Spell {

    public EmptySpell() {
        super(SpellType.FIRE, SpellRank.NOVICE, 0, 0, "None");
    }

    @Override
    public void cast(PlayerEntity user, World world) {
        user.sendMessage(Text.translatable("E"));
    }
}