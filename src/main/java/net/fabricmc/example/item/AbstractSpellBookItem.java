package net.fabricmc.example.item;

import net.fabricmc.example.spell.Spell;
import net.minecraft.item.Item;

public class AbstractSpellBookItem extends Item {

    Spell spell;

    public AbstractSpellBookItem(Settings settings, Spell spell) {
        super(settings);
        this.spell = spell;
    }
}
