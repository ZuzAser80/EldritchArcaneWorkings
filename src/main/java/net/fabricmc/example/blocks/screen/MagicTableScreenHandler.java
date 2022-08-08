package net.fabricmc.example.blocks.screen;

import net.fabricmc.example.ExampleMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class MagicTableScreenHandler extends ScreenHandler {

    SimpleInventory inventory = new SimpleInventory(9);

    protected ScreenHandlerContext context;


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
        /*this.addSlot(new MagicTableBookSlot(this, inventory, 3, 55, -9 + 88));
        this.addSlot(new MagicTableBookSlot(this, inventory, 4, 34, -9 + (88 - 20)));
        this.addSlot(new MagicTableBookSlot(this, inventory, 5, 55, -9 + 88));
        this.addSlot(new MagicTableBookSlot(this, inventory, 6, 34, -9 + (88 - 20)));
        this.addSlot(new MagicTableBookSlot(this, inventory, 7, 55, -9 + 88));
        this.addSlot(new MagicTableBookSlot(this, inventory, 8, 34, -9 + (88 - 20)));*/

        //The player playerInventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 119 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 197));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        if(slotIndex >= 36 && slotIndex <= 44 && actionType == SlotActionType.THROW) {
            actionType = SlotActionType.PICKUP;
        }
        super.onSlotClick(slotIndex, button, actionType, player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
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

    public MagicTableScreenHandler(int i, PlayerInventory playerInventory) {
        this(i, playerInventory, ScreenHandlerContext.EMPTY);
    }
}