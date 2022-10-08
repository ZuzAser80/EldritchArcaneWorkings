package net.fabricmc.eaw.item;

import net.fabricmc.eaw.spell.Spell;
import net.fabricmc.eaw.spell.SpellRank;
import net.fabricmc.eaw.spell.spells.EmptySpell;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class AbstractMagicRodItem extends Item {

    int maxManaCap, manaCount;
    SpellRank rank;

    public AbstractMagicRodItem(Settings settings, int maxManaCapacity, SpellRank rodRank) {
        super(settings);
        this.maxManaCap = maxManaCapacity;
        if(manaCount < maxManaCapacity) {
            manaCount = maxManaCapacity;
        }
        rank = rodRank;
    }

    public List<Spell> getSpellList(ItemStack stack) {
        List<Spell> spellList = new LinkedList<>();
        NbtCompound spellsSubNbt = stack.getOrCreateSubNbt("spells");
        for(int i = 0; i < 8; i++) {
            spellList.add(Spell.fromNbt((NbtCompound)spellsSubNbt.get("spell_" + i)));
        }
        return spellList;
    }

    public void setSpellList(List<Spell> list, ItemStack stack) {
        int maxSpellSlots = stack.getOrCreateNbt().getInt("maxSpellSlot");
        for(int i = 0; i <= (maxSpellSlots); i++) {
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
        int currentSpellIndex = stack.getOrCreateNbt().getInt("currentSpellIndex");
        List<Spell> spellList = this.getSpellList(stack);
        if(currentSpellIndex < 8) {
            System.out.println("Moving Up");
            stack.getOrCreateSubNbt("spells").put("currentSpell", spellList.get(currentSpellIndex).toNbt());
        } else {
            stack.getOrCreateSubNbt("spells").put("currentSpell", spellList.get(0).toNbt());
            stack.getOrCreateNbt().putInt("currentSpellIndex", 0);
            currentSpellIndex = 0;
            System.out.println("changed to 0");
        }
        currentSpellIndex++;
        stack.getOrCreateNbt().putInt("currentSpellIndex", currentSpellIndex);
    }

    public void switchCurrentSpellDownwards(ItemStack stack) {
        int currentSpellIndex = stack.getOrCreateNbt().getInt("currentSpellIndex");
        List<Spell> spellList = this.getSpellList(stack);
        if(currentSpellIndex > 0) {
            System.out.println("Moving Down");
            stack.getOrCreateSubNbt("spells").put("currentSpell", spellList.get(currentSpellIndex).toNbt());
            currentSpellIndex--;
        } else {
            stack.getOrCreateSubNbt("spells").put("currentSpell", spellList.get(7).toNbt());
            System.out.println("changed to " + (7));
            currentSpellIndex = 7;
        }
        stack.getOrCreateNbt().putInt("currentSpellIndex", currentSpellIndex);
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(stack.isOf(this) && selected) {
            if(stack.getNbt() != null) {
                if(stack.getNbt().getString("rod") == null || stack.getNbt().getString("rod").equals("")) {
                    stack.getOrCreateNbt().putString("rod", "oak_rod");
                } if(stack.getNbt().getString("crystal") == null || stack.getNbt().getString("crystal").equals("")) {
                    stack.getOrCreateNbt().putString("crystal", "crystal");
                }
            } else {
                stack.getOrCreateNbt().putString("rod", "oak_rod");
                stack.getOrCreateNbt().putString("crystal", "crystal");
                stack.getOrCreateNbt().putFloat("manaCount", manaCount);
                stack.getOrCreateNbt().putInt("currentSpellIndex", 0);
            }
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
