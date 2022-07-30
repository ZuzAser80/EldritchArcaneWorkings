package net.fabricmc.example.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class Spell {

    SpellType type;
    SpellRank rank;
    int cooldown, manaWaste;

    public Spell(SpellType type, SpellRank rank, int cooldown, int manaWaste) {
        this.type = type;
        this.rank = rank;
        this.cooldown = cooldown;
        this.manaWaste = manaWaste;
    }

    public int getManaCost() {
        return manaWaste;
    }

    public void cast(PlayerEntity user, World world) {
    }
}
