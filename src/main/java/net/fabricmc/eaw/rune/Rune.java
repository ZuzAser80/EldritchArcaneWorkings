package net.fabricmc.eaw.rune;

import net.minecraft.nbt.NbtCompound;

public class Rune {

    RuneStrength strength;
    RuneType type;
    String name;

    public Rune(RuneStrength strength, RuneType type, String name) {
        this.strength = strength;
        this.type = type;
        this.name = name;
    }

    public Rune fromNbt(NbtCompound nbt) {
        return new Rune(strength.runeStrength(nbt.getInt("strength")), type.fromString(nbt.getString("type")), nbt.getString("name"));
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("strength", strength.getStrength());
        nbt.putString("type", type.getName());
        nbt.putString("name", name);
        return nbt;
    }
}
