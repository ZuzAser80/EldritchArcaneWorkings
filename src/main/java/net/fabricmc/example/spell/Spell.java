package net.fabricmc.example.spell;

import net.fabricmc.example.spell.spells.EmptySpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public abstract class Spell {

    private static final Map<String, Spell> spellHashMap = new HashMap<>();
    String name;
    SpellType type;
    SpellRank rank;
    int cooldown, manaCost;

    //NEVER INPUT 2 SPELLS WITH SAME NAME!
    //IT IS VERY IMPORTANT
    //IK IT IS SPAGHETTI
    public Spell(SpellType type, SpellRank rank, int cooldown, int manaCost, String name) {
        spellHashMap.put(name, this);
        this.type = type;
        this.rank = rank;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.name = name;
    }

    public SpellRank getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("spellName", this.getName());
        return nbt;
    }

    public static Spell fromNbt(NbtCompound nbt) {
        if(nbt != null) {
            return spellHashMap.get(nbt.getString("spellName"));
        } else {
            return new EmptySpell();
        }
    }

    public SpellType getType() {
        return type;
    }

    public int getManaCost() {
        return manaCost;
    }

    public abstract void cast(PlayerEntity user, World world);
}
