package net.fabricmc.example.item;

import net.fabricmc.example.spell.Spell;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractSpellBookItem extends Item {


    //TODO: MAKE EMPTY SPELLBOOK TO EXTRACT SPELLZ AND SET SPELL AFTERWARDS
    //TODO: FIX SCREEn
    Spell spell;

    public AbstractSpellBookItem(Spell spell) {
        super(new Settings().maxCount(1));
        this.spell = spell;
    }
    public void setSpell(Spell spell) {
        this.spell = spell;
    }
    public Spell getSpell() {
        return spell;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Formatting formatting;
        if(stack.getNbt() != null) {
            switch (stack.getNbt().getString("spellType")) {
                case "WATER" -> formatting = Formatting.AQUA;
                case "AIR" -> formatting = Formatting.BOLD;
                case "FIRE" -> formatting = Formatting.RED;
                case "GROUND" -> formatting = Formatting.GREEN;
                case "F0RB1DD3N" -> formatting = Formatting.OBFUSCATED;
                default -> formatting = Formatting.WHITE;
            }
            tooltip.add(Text.translatable("item.eaw.spell_book_tooltip", stack.getNbt().getString("spellName")).formatted(formatting));
        } else {
            tooltip.add(Text.translatable("item.eaw.spell_book_tooltip", " None").formatted(Formatting.GRAY));
        }
    }
}
