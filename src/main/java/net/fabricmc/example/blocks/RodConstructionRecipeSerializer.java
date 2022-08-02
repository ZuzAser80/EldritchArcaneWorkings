package net.fabricmc.example.blocks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RodConstructionRecipeSerializer implements RecipeSerializer<RodConstructionRecipe> {
    @Override
    // Turns json into Recipe
    public RodConstructionRecipe read(Identifier id, JsonObject json) {
        RodConstructionJsonFormat recipeJson = new Gson().fromJson(json, RodConstructionJsonFormat.class);
        if (recipeJson.inputA == null || recipeJson.inputB == null || recipeJson.outputItem == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        // We'll allow to not specify the output, and default it to 1.
        if (recipeJson.outputAmount == 0) recipeJson.outputAmount = 1;

        Ingredient inputA = Ingredient.fromJson(recipeJson.inputA);
        Ingredient inputB = Ingredient.fromJson(recipeJson.inputB);
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem))
                // Validate the inputted item actually exists
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new RodConstructionRecipe(id, output, inputA, inputB);
    }
    @Override
    // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf packetData, RodConstructionRecipe recipe) {
        recipe.getRod().write(packetData);
        recipe.getCrystal().write(packetData);
        packetData.writeItemStack(recipe.getOutput());
    }

    @Override
    // Turns PacketByteBuf into Recipe
    public RodConstructionRecipe read(Identifier id, PacketByteBuf packetData) {
        Ingredient inputA = Ingredient.fromPacket(packetData);
        Ingredient inputB = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        return new RodConstructionRecipe(id, output, inputA, inputB);
    }
}
