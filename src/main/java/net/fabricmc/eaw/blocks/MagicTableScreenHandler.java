package net.fabricmc.eaw.blocks;

import net.fabricmc.eaw.ExampleMod;
import net.fabricmc.eaw.item.AbstractMagicRodItem;
import net.fabricmc.eaw.item.AbstractSpellBookItem;
import net.fabricmc.eaw.spell.spells.EmptySpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class MagicTableScreenHandler extends ScreenHandler {

    final int offsetLeft = 33;
    final int offsetTop = -27;
    private final Inventory inv;

    public MagicTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public MagicTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ExampleMod.magicTableScreenHandler, syncId);
        checkSize(inventory, 9);
        this.inv = inventory;
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
        this.addSlot(new MagicTableStaffSlot(inventory, 8, 79 + offsetLeft - 32, 61 + offsetTop, true){
            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                MagicTableScreenHandler.this.manage(player, stack);
            }
        });

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
        dropInventory(player, inv);
    }

    public void manage(PlayerEntity user, ItemStack stack) {
        for(int i = 0; i < inv.size(); i++) {
            if(stack.getItem() instanceof AbstractMagicRodItem rodItem) {
                if (inv.getStack(i).getItem() instanceof AbstractSpellBookItem) {
                    if (inv.getStack(i).getNbt() != null) {
                        NbtElement intoRod = inv.getStack(i).getOrCreateNbt().get("spell");
                        if (!((NbtCompound) intoRod).getString("spellName").equals("None")) {
                            stack.getOrCreateSubNbt("spells").put("spell_" + i, intoRod);
                            inv.getStack(i).getOrCreateNbt().put("spell", new EmptySpell().toNbt());
                            System.out.println("intoRod: " + intoRod + " Boo: " + rodItem.getSpellList(stack));
                        } else {
                            inv.getStack(i).getOrCreateNbt().put("spell", stack.getOrCreateSubNbt("spells").get("spell_" + i));
                            stack.getOrCreateSubNbt("spells").put("spell_" + i, new EmptySpell().toNbt());
                        }
                    }
                } else if (inv.getStack(i).isOf(ExampleMod.magicCrystal)) {
                    float crystalCount = ((stack.getOrCreateNbt().getFloat("maxManaCap") - stack.getOrCreateNbt().getFloat("manaCount")) / 10);
                    stack.getOrCreateNbt().putFloat("manaCount", stack.getOrCreateNbt().getFloat("manaCount") + (10 * crystalCount));
                    inv.getStack(i).decrement((int)crystalCount);
                }
            }
        }
        stack.getOrCreateNbt().put("currentSpell", stack.getOrCreateSubNbt("spells").get("spell_0"));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inv.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inv.size()) {
                if (!this.insertItem(originalStack, this.inv.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inv.size(), false)) {
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
    public boolean canInsert(ItemStack stack) {
        if(rod) {
            return stack.getItem() instanceof AbstractMagicRodItem;
        } else {
            return stack.getItem() instanceof AbstractSpellBookItem || stack.isOf(ExampleMod.magicCrystal);
        }
    }
}
