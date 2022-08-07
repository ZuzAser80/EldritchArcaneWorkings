package net.fabricmc.example.blocks.screen;

import net.fabricmc.example.blocks.screen.MagicTableScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MagicTableBookSlot extends Slot {

    MagicTableScreenHandler handler;

    public MagicTableBookSlot(MagicTableScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity)
    {
        return canMoveStack(getStack());
    }

    @Override
    public boolean canInsert(ItemStack stack)
    {
        return isMagicalBook(stack);
    }

    // Prevents items that override canBeNested() from being inserted into backpack
    public boolean canMoveStack(ItemStack stack)
    {
        return stack.getItem().canBeNested();
    }
    public int getMaxItemCount(ItemStack stack) {
        return isMagicalBook(stack) ? 1 : super.getMaxItemCount(stack);
    }

    public static boolean isMagicalBook(ItemStack stack) {
        return stack.isIn(TagKey.of(Registry.ITEM_KEY, new Identifier("eaw", "spell_books")));
    }
}
