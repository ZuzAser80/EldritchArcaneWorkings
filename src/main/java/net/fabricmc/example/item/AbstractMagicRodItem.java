package net.fabricmc.example.item;

import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.spells.EmptySpell;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AbstractMagicRodItem extends Item {

    int maxManaCap, manaCount;
    SpellRank rank;
    private int maxSpells;

    public AbstractMagicRodItem(Settings settings, int maxManaCapacity, SpellRank rodRank, int maxSpellSlot) {
        super(settings);
        this.maxManaCap = maxManaCapacity;
        if(manaCount < maxManaCapacity) {
            manaCount = maxManaCapacity;
        }
        rank = rodRank;
        this.maxSpells = maxSpellSlot;
    }

    public List<Spell> getSpellList(ItemStack stack) {
        List<Spell> spellList = new LinkedList<>();
        int maxSpellSlots = stack.getOrCreateNbt().getInt("maxSpellSlot");
        NbtCompound spellsSubNbt = stack.getOrCreateSubNbt("spells");
        for(int i = 0; i < maxSpellSlots; i++) {
            spellList.add(Spell.fromNbt((NbtCompound)spellsSubNbt.get("spell_" + i)));
        }
        return spellList;
    }

    public void setSpellList(List<Spell> list, ItemStack stack) {
        int maxSpellSlots = stack.getOrCreateNbt().getInt("maxSpellSlot");
        for(int i = 0; i < (maxSpellSlots); i++) {
            if(list.get(i) != null) {
                stack.getOrCreateSubNbt("spells").put("spell_" + i, list.get(i).toNbt());
            } else {
                stack.getOrCreateSubNbt("spells").put("spell_" + i, new EmptySpell().toNbt());
            }
        }
    }

    public int getCurrentSpellIndex(ItemStack stack) {
        return this.getSpellList(stack).indexOf(stack.getOrCreateSubNbt("spells").get("currentSpell"));
    }

    public void switchCurrentSpell(ItemStack stack) {
        int maxSpellSlots = stack.getOrCreateNbt().getInt("maxSpellSlot");
        List<Spell> spellList = this.getSpellList(stack);
        if(getCurrentSpellIndex(stack) < (maxSpellSlots - 1)) {
            stack.getOrCreateSubNbt("spells").put("currentSpell", spellList.get(getCurrentSpellIndex(stack) + 1).toNbt());
        } else {
            stack.getOrCreateSubNbt("spells").put("currentSpell", spellList.get(0).toNbt());
        }
    }

    public void switchCurrentSpellDownwards(ItemStack stack) {
        int maxSpellSlots = stack.getOrCreateNbt().getInt("maxSpellSlot");
        List<Spell> spellList = this.getSpellList(stack);
        if(getCurrentSpellIndex(stack) > 0) {
            stack.getOrCreateSubNbt("spells").put("currentSpell", spellList.get(getCurrentSpellIndex(stack) - 1).toNbt());
        } else {
            stack.getOrCreateSubNbt("spells").put("currentSpell", spellList.get(maxSpellSlots - 1).toNbt());
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(stack.isOf(this) && stack.getNbt() == null ||stack.getNbt().getString("rod") == null && stack.getNbt().getString("crystal") == null) {
            stack.getOrCreateNbt().putFloat("manaCount", manaCount);
            stack.getOrCreateNbt().putString("rod", "oak_rod");
            stack.getOrCreateNbt().putString("crystal", "crystal");
            stack.getOrCreateNbt().putInt("maxSpellSlot", ((AbstractMagicRodItem)stack.getItem()).maxSpells);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack mainHand = user.getMainHandStack();
        NbtCompound nbt = mainHand.getOrCreateNbt();
        float temp;
        if(nbt.getFloat("maxManaCap") == 0) {
            nbt.putFloat("maxManaCap", maxManaCap);
        }
        if(mainHand.isOf(this) && !world.isClient) {
            Spell currentSpell;
            NbtCompound spells = user.getMainHandStack().getOrCreateSubNbt("spells");
            if(spells.get("currentSpell") != null) {
                currentSpell = Spell.fromNbt((NbtCompound) spells.get("currentSpell"));
            } else {
                currentSpell = new EmptySpell();
            }
            if(nbt.getFloat("manaCount") > currentSpell.getManaCost() && rank.getId() >= currentSpell.getRank().getId()) {
                temp = nbt.getFloat("manaCount");
                currentSpell.cast(user, world);
                nbt.putFloat("manaCount", temp -= currentSpell.getManaCost());
            }
        }
        return TypedActionResult.success(user.getMainHandStack(), true);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.eaw.rod_item.rank_tooltip", this.rank.name()));
    }
}
