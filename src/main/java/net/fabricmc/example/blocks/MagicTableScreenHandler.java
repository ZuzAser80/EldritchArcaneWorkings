package net.fabricmc.example.blocks;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.item.AbstractMagicRodItem;
import net.fabricmc.example.item.AbstractSpellBookItem;
import net.fabricmc.example.spell.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public class MagicTableScreenHandler extends ScreenHandler {

    final int offsetLeft = 33;
    final int offsetTop = -27;
    private final Inventory i;

    public MagicTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public MagicTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ExampleMod.magicTableScreenHandler, syncId);
        checkSize(inventory, 9);
        this.i = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m;
        int l;
        this.addSlot(new MagicTableStaffSlot(inventory, 0, 57 + offsetLeft - 32, 16 + offsetTop, false));
        this.addSlot(new MagicTableStaffSlot(inventory, 1, 101 + offsetLeft - 32, 16 + offsetTop, false));
        this.addSlot(new MagicTableStaffSlot(inventory, 2, 30 + offsetLeft - 32, 39 + offsetTop, false));
        this.addSlot(new MagicTableStaffSlot(inventory, 3, 128 + offsetLeft - 32, 39 + offsetTop, false));
        this.addSlot(new MagicTableStaffSlot(inventory, 4, 30 + offsetLeft - 32, 83 + offsetTop, false));
        this.addSlot(new MagicTableStaffSlot(inventory, 5, 128 + offsetLeft - 32, 83 + offsetTop, false));
        this.addSlot(new MagicTableStaffSlot(inventory, 6, 57 + offsetLeft - 32, 107 + offsetTop, false));
        this.addSlot(new MagicTableStaffSlot(inventory, 7, 101 + offsetLeft - 32, 107 + offsetTop, false));
        this.addSlot(new MagicTableStaffSlot(inventory, 8, 79 + offsetLeft - 32, 61 + offsetTop, true));

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, (103 + 9) + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, (161 + 9)));
        }
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        dropInventory(player, i);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.i.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.i.size()) {
                if (!this.insertItem(originalStack, this.i.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.i.size(), false)) {
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
}
class MagicTableStaffSlot extends Slot {

    boolean rod;

    public MagicTableStaffSlot(Inventory inventory, int index, int x, int y, boolean rod) {
        super(inventory, index, x, y);
        this.rod = rod;
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        if(stack.getItem() instanceof AbstractMagicRodItem rodItem && !player.world.isClient) {
            List<Spell> list = rodItem.getSpellList(stack);
            for (int i = 0; i < 8; i++) {
                if(inventory.getStack(i).getItem() instanceof AbstractSpellBookItem) {
                    list.add(Spell.fromNbt((NbtCompound)inventory.getStack(8).getOrCreateSubNbt("spells").get("currentSpell")));
                    if (list.get(i) != null) {
                        inventory.removeStack(i);
                    }
                } else if(list.get(i) != null) {
                    inventory.getStack(i).getOrCreateNbt().putString("spellType", list.get(i).getType().toString());
                    inventory.getStack(i).getOrCreateNbt().putString("spellName", list.get(i).getName());
                    inventory.getStack(8).getOrCreateSubNbt("spells").putString("spell_" + i, "None");
                }
            }
            if(!list.isEmpty()) {
                rodItem.setSpellList(list, stack);
            }
        }
        super.onTakeItem(player, stack);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if(rod) {
            return stack.getItem() instanceof AbstractMagicRodItem;
        } else {
            return stack.getItem() instanceof AbstractSpellBookItem;
        }
    }
}
