package net.fabricmc.eaw.item;

import net.fabricmc.eaw.spell.Spell;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractSpellBookItem extends Item {


    //TODO: MAKE EMPTY SPELLBOOK TO EXTRACT SPELLZ AND SET SPELL AFTERWARDS
    //TODO: FIX SCREEn

    public AbstractSpellBookItem() {
        super(new Settings().maxCount(1));
    }

    public void putSpell(Spell spell, ItemStack stack) {
        stack.getOrCreateNbt().put("spell", spell.toNbt());
    }
    public Spell getSpell(ItemStack stack) {
        return Spell.fromNbt((NbtCompound)stack.getOrCreateNbt().get("spell"));
    }

    public static void randomizeContents(ItemStack stack) {
        stack.getOrCreateNbt().put("spell", Spell.random());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Formatting formatting;
        Spell heldSpell = Spell.fromNbt((NbtCompound)stack.getOrCreateNbt().get("spell"));
        if(heldSpell != null) {
            switch (heldSpell.getType()) {
                case WATER -> formatting = Formatting.AQUA;
                case AIR -> formatting = Formatting.BOLD;
                case FIRE -> formatting = Formatting.RED;
                case GROUND -> formatting = Formatting.GREEN;
                case F0RB1DD3N -> formatting = Formatting.OBFUSCATED;
                case EMPTY -> formatting = Formatting.GRAY;
                default -> formatting = Formatting.WHITE;
            }
            tooltip.add(Text.translatable("item.eaw.spell_book_tooltip", heldSpell.getName()).formatted(formatting));
        } else {
            tooltip.add(Text.translatable("item.eaw.spell_book_tooltip", " None").formatted(Formatting.GRAY));
        }
    }
}
