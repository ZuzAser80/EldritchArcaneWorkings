package net.fabricmc.example.blocks.screen;

import net.fabricmc.example.item.AbstractMagicRodItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MagicTableStaffSlot extends Slot {
    private MagicTableScreenHandler handler;

    public MagicTableStaffSlot(MagicTableScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    public boolean canInsert(ItemStack stack) {
        return isStaff(stack);
    }

    public int getMaxItemCount(ItemStack stack) {
        return isStaff(stack) ? 1 : super.getMaxItemCount(stack);
    }

    public static boolean isStaff(ItemStack stack) {
        return stack.getItem() instanceof AbstractMagicRodItem;
    }
}
