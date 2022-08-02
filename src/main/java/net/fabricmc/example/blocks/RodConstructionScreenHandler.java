package net.fabricmc.example.blocks;

import net.fabricmc.example.ExampleMod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RodConstructionScreenHandler extends ForgingScreenHandler {
    private final World world;
    @Nullable
    private RodConstructionRecipe currentRecipe;
    private final List<RodConstructionRecipe> recipes;

    static ScreenHandlerType<RodConstructionScreenHandler> rodConstructionType;

    public static void register() {
        rodConstructionType = Registry.register(Registry.SCREEN_HANDLER, new Identifier("eaw", "rod_construction_screen_handler"), new ScreenHandlerType<>(RodConstructionScreenHandler::new));
    }

    public RodConstructionScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public RodConstructionScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(rodConstructionType, syncId, playerInventory, context);
        this.world = playerInventory.player.world;
        this.recipes = this.world.getRecipeManager().listAllOfType(ExampleMod.rodConstructionType);
    }

    protected boolean canUse(BlockState state) {
        return state.isOf(ExampleMod.rodConstructionStation);
    }

    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return this.currentRecipe != null && this.currentRecipe.matches((CraftingInventory) this.input, this.world);
    }

    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        stack.onCraft(player.world, player, stack.getCount());
        this.output.unlockLastRecipe(player);
        this.decrementStack(0);
        this.decrementStack(1);
        this.context.run((world, pos) -> {
            world.syncWorldEvent(1044, pos, 0);
        });
    }

    private void decrementStack(int slot) {
        ItemStack itemStack = this.input.getStack(slot);
        itemStack.decrement(1);
        this.input.setStack(slot, itemStack);
    }

    public void updateResult() {
        List<RodConstructionRecipe> list = this.world.getRecipeManager().getAllMatches(ExampleMod.rodConstructionType, (CraftingInventory)this.input, this.world);
        if (list.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
        } else {
            this.currentRecipe = list.get(0);
            ItemStack itemStack = this.currentRecipe.craft((CraftingInventory)this.input);
            this.output.setLastRecipe(this.currentRecipe);
            this.output.setStack(0, itemStack);
        }

    }

    protected boolean isUsableAsAddition(ItemStack stack) {
        return this.recipes.stream().anyMatch((recipe) -> recipe.testCrystal(stack));
    }

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }
}
