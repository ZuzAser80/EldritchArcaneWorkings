package net.fabricmc.eaw.rune;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RuneItem extends Item {

    //TagKey<Item> target;
    Rune rune;

    public RuneItem(Settings settings, Rune heldRune) {
        super(settings);

        rune = heldRune;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(stack.getOrCreateNbt().get("rune") == null) {
            stack.getOrCreateNbt().put("rune", rune.toNbt());
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
