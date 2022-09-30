package net.fabricmc.eaw.util;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.SmithingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class EAWDatagenMain implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(EAWRecipeGenerator::new);
    }
}
class EAWRecipeGenerator extends FabricRecipeProvider {
    EAWRecipeGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        addItemToRecipe(exporter, Items.NETHERITE_SWORD);
        addItemToRecipe(exporter, Items.NETHERITE_SHOVEL);
        addItemToRecipe(exporter, Items.NETHERITE_AXE);
        addItemToRecipe(exporter, Items.NETHERITE_HOE);
        addItemToRecipe(exporter, Items.NETHERITE_PICKAXE);
        addItemToRecipe(exporter, Items.DIAMOND_SWORD);
        addItemToRecipe(exporter, Items.DIAMOND_SHOVEL);
        addItemToRecipe(exporter, Items.DIAMOND_AXE);
        addItemToRecipe(exporter, Items.DIAMOND_HOE);
        addItemToRecipe(exporter, Items.DIAMOND_PICKAXE);
        addItemToRecipe(exporter, Items.GOLDEN_SWORD);
        addItemToRecipe(exporter, Items.GOLDEN_SHOVEL);
        addItemToRecipe(exporter, Items.GOLDEN_AXE);
        addItemToRecipe(exporter, Items.GOLDEN_HOE);
        addItemToRecipe(exporter, Items.GOLDEN_PICKAXE);
        addItemToRecipe(exporter, Items.IRON_SWORD);
        addItemToRecipe(exporter, Items.IRON_SHOVEL);
        addItemToRecipe(exporter, Items.IRON_AXE);
        addItemToRecipe(exporter, Items.IRON_HOE);
        addItemToRecipe(exporter, Items.IRON_PICKAXE);
        addItemToRecipe(exporter, Items.STONE_SWORD);
        addItemToRecipe(exporter, Items.STONE_SHOVEL);
        addItemToRecipe(exporter, Items.STONE_AXE);
        addItemToRecipe(exporter, Items.STONE_HOE);
        addItemToRecipe(exporter, Items.STONE_PICKAXE);
        addItemToRecipe(exporter, Items.WOODEN_SWORD);
        addItemToRecipe(exporter, Items.WOODEN_SHOVEL);
        addItemToRecipe(exporter, Items.WOODEN_AXE);
        addItemToRecipe(exporter, Items.WOODEN_HOE);
        addItemToRecipe(exporter, Items.WOODEN_PICKAXE);
    }
    public void addItemToRecipe(Consumer<RecipeJsonProvider> exporter, Item item) {
        SmithingRecipeJsonBuilder.create(Ingredient.ofItems(item), Ingredient.fromTag(TagKey.of(Registry.ITEM_KEY, new Identifier("eaw", "runes"))), item).criterion("has_netherite_ingot", conditionsFromItem(Items.NETHERITE_INGOT)).offerTo(exporter, getItemPath(item) + "_rune_upgrade");
    }
}