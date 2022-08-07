package net.fabricmc.example.item;

import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.fabricmc.example.spell.spells.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class AbstractMagicRodItem extends Item {

    int maxManaCap, manaCount, maxSpellCount;
    SpellRank rank;
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
    }

    public int getCurrentSpellIndex() {
        return spellList.indexOf(currentSpell);
    }

    public void switchCurrentSpell() {
        if(this.getCurrentSpellIndex() < (spellList.size() - 1)) {
            currentSpell = spellList.get(this.getCurrentSpellIndex() + 1);
        } else {
            currentSpell = spellList.get(0);
        }
    }

    public void switchCurrentSpellDownwards() {
        if(getCurrentSpellIndex() > 0) {
            currentSpell = spellList.get(this.getCurrentSpellIndex() - 1);
        } else {
            currentSpell = spellList.get(spellList.size() - 1);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(stack.isOf(this) && stack.getNbt() == null) {
            stack.getOrCreateNbt().putFloat("manaCount", manaCount);
            stack.getOrCreateNbt().putString("rod", "");
            stack.getOrCreateNbt().putString("crystal", "crystal");
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getMainHandStack().getOrCreateNbt().getFloat("maxManaCap") == 0) {
            user.getMainHandStack().getOrCreateNbt().putFloat("maxManaCap", maxManaCap);
        }
        if(user.getMainHandStack().isOf(this) && !world.isClient && currentSpell != null) {
            if(manaCount > currentSpell.getManaCost()) {
                currentSpell.cast(user, world);
                manaCount -= currentSpell.getManaCost();
                user.getMainHandStack().getOrCreateNbt().putFloat("manaCount", manaCount);
            } else {
                user.getMainHandStack().getOrCreateNbt().putFloat("manaCount", maxManaCap);
                manaCount = maxManaCap;
            }
        } else if(currentSpell == null && spellList.size() > 0) {
            currentSpell = spellList.get(0);
        }
        return TypedActionResult.success(user.getMainHandStack(), true);
    }
}
