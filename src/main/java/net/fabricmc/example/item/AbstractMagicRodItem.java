package net.fabricmc.example.item;

import net.fabricmc.example.entity.AdvancedFireballEntity;
import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.fabricmc.example.spell.spells.AdvancedFireballSpell;
import net.fabricmc.example.spell.spells.AdvancedSnowballSpell;
import net.fabricmc.example.spell.spells.LeapSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class AbstractMagicRodItem extends Item {

    int maxManaCap, manaCount, maxSpellCount;
    SpellRank rank;
    SpellType element;
    List<Spell> spellList = new LinkedList<>();
    public Spell currentSpell;

    public AbstractMagicRodItem(Settings settings, int maxManaCapacity, SpellRank rodRank, int maxSpellCount) {
        super(settings);
        this.maxManaCap = maxManaCapacity;
        if(manaCount < maxManaCapacity) {
            manaCount = maxManaCapacity;
        }
        rank = rodRank;
        this.maxSpellCount = maxSpellCount;
        currentSpell = new AdvancedFireballSpell();
    }
    public AbstractMagicRodItem(Settings settings, int maxManaCapacity, SpellRank rodRank, SpellType typeToDiscountMana) {
        super(settings);
        this.maxManaCap = maxManaCapacity;
        manaCount = maxManaCapacity;
        rank = rodRank;
        element = typeToDiscountMana;
    }

    /*public int getCurrentSpellIndex() {
        return spellList.indexOf(currentSpell);
    }

    public void switchCurrentSpell() {
        currentSpell = spellList.get(this.getCurrentSpellIndex() + 1);
    }

    public void switchCurrentSpellDownwards() {
        currentSpell = spellList.get(this.getCurrentSpellIndex() - 1);
    }*/

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getMainHandStack().getOrCreateNbt().getFloat("maxManaCap") == 0) {
            user.getMainHandStack().getOrCreateNbt().putFloat("maxManaCap", maxManaCap);
        }
        if(user.getMainHandStack().isOf(this) && !world.isClient) {
            if(manaCount > currentSpell.getManaCost()) {
                if(currentSpell instanceof AdvancedFireballSpell) {
                    System.out.println("sex");
                    currentSpell = new AdvancedSnowballSpell();
                } else if(currentSpell instanceof LeapSpell) {
                    currentSpell = new AdvancedFireballSpell();
                } else if(currentSpell instanceof AdvancedSnowballSpell) {
                    currentSpell = new LeapSpell();
                }
                currentSpell.cast(user, world);
                manaCount -= currentSpell.getManaCost();
                user.getMainHandStack().getOrCreateNbt().putFloat("manaCount", manaCount);
            } else {
                user.getMainHandStack().getOrCreateNbt().putFloat("manaCount", maxManaCap);
                manaCount = maxManaCap;
            }
        }
        return TypedActionResult.success(user.getMainHandStack(), true);
    }
}
