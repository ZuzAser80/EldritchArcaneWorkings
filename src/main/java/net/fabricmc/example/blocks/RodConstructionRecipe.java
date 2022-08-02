package net.fabricmc.example.blocks;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class RodConstructionRecipe implements Recipe<CraftingInventory> {
    private final Ingredient rod;
    private final Ingredient crystal;
    private final ItemStack result;
    private final Identifier id;

    public RodConstructionRecipe(Identifier id, ItemStack result, Ingredient inputA, Ingredient inputB) {
        this.id = id;
        this.rod = inputA;
        this.crystal = inputB;
        this.result = result;
    }

    public Ingredient getRod() {
        return this.rod;
    }

    public Ingredient getCrystal() {
        return this.crystal;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    public boolean testCrystal(ItemStack stack) {
        if (stack == null) {
            return false;
        } else return stack.isIn(TagKey.of(Registry.ITEM_KEY, new Identifier("eaw", "crystals")));
    }

    @Override
    public Identifier getId() {
        return this.id;
    }
    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        if(inventory.size() < 2) return false;
        return rod.test(inventory.getStack(0)) && crystal.test(inventory.getStack(1));    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        return this.getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    public static RodConstructionRecipeSerializer INSTANCE = new RodConstructionRecipeSerializer();

    // This will be the "type" field in the json
    @Override
    public RecipeSerializer<?> getSerializer() {
        return INSTANCE;
    }
    public static class Type implements RecipeType<RodConstructionRecipe> {
        // Define ExampleRecipe.Type as a singleton by making its constructor private and exposing an instance.
        private Type() {}
        public static final Type INSTANCE = new Type();

        // This will be needed in step 4
        public static final String ID = "two_slot_recipe";
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
}
