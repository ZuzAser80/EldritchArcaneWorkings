package net.fabricmc.example.blocks.screen;

import net.fabricmc.example.ExampleMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;

public class MagicTableScreenHandler extends ScreenHandler {

    SimpleInventory inventory = new SimpleInventory(3);
    protected ScreenHandlerContext context;


    public MagicTableScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId);
    }

    public MagicTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext screenHandlerContext) {
        super(ExampleMod.magicTableScreenHandler, syncId);
        checkSize(inventory, 3);
        this.context = screenHandlerContext;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job

        int m;
        int l;
        //Our playerInventory
        this.addSlot(new MagicTableStaffSlot(this, inventory, 0, 80, 81 - 46));
        this.addSlot(new MagicTableBookSlot(this, inventory, 1, 55, -9 + 88));
        this.addSlot(new MagicTableBookSlot(this, inventory, 2, 34, -9 + (88 - 20)));
        //The player playerInventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 112 + m * 18));
            }
        }
        //The player Hotbar
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 170));
        }
    }

    public MagicTableScreenHandler(int i, PlayerInventory playerInventory) {
        this(i, playerInventory, ScreenHandlerContext.EMPTY);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}