package net.fabricmc.eaw.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Arrays;
import java.util.List;

public class EAWOres {

    public static Block magicOre = Registry.register(Registry.BLOCK, new Identifier("eaw", "magic_ore_block"), new Block(AbstractBlock.Settings.of(Material.STONE).strength(4, 5).requiresTool()));
    public static Block deepslateMagicOre = Registry.register(Registry.BLOCK, new Identifier("eaw", "deepslate_magic_ore_block"), new Block(AbstractBlock.Settings.of(Material.STONE).strength(6, 7).requiresTool()));
    public static Block magicFlower = Registry.register(Registry.BLOCK, new Identifier("eaw", "magic_flower_block"), new FlowerBlock(StatusEffects.NIGHT_VISION, 999, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(AbstractBlock.OffsetType.XZ).nonOpaque()));

    private static ConfiguredFeature<?, ?> M_O_O = new ConfiguredFeature
            (Feature.ORE, new OreFeatureConfig(
                    OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    magicOre.getDefaultState(),
                    9)); // vein size

    public static PlacedFeature M_O_O_P_F = new PlacedFeature(
            RegistryEntry.of(M_O_O),
            Arrays.asList(
                    CountPlacementModifier.of(10), // number of veins per chunk
                    SquarePlacementModifier.of(), // spreading horizontally
                    HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.fixed(128))
            )); // height


    //Code stolen from: https://github.com/Chalkboard-Mods/floral-flair-fabric/blob/1.18.x/src/main/java/chalkboardmods/floralflair/core/init/FloralFeatures.java
    private static ConfiguredFeature<?, ?> F_O = new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchFeatureConfig(64, 4, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(magicFlower)))));

    public static PlacedFeature F_O_P_F = new PlacedFeature(RegistryEntry.of(F_O), List.of(RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,  BiomePlacementModifier.of()));

    private static ConfiguredFeature<?, ?> M_O_D_O = new ConfiguredFeature
            (Feature.ORE, new OreFeatureConfig(
                    OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    deepslateMagicOre.getDefaultState(),
                    15)); // vein size

    public static PlacedFeature M_O_D_O_P_F = new PlacedFeature(
            RegistryEntry.of(M_O_D_O),
            Arrays.asList(
                    CountPlacementModifier.of(5), // number of veins per chunk
                    SquarePlacementModifier.of(), // spreading horizontally
                    HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.fixed(64))
            ));
    public static void registerOres() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new Identifier("eaw", "overworld_magic_ore"), M_O_O);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier("eaw", "overworld_magic_ore"),
                M_O_O_P_F);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier("eaw", "overworld_magic_ore")));
        //Deepslate
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new Identifier("eaw", "overworld_deepslate_magic_ore"), M_O_D_O);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier("eaw", "overworld_deepslate_magic_ore"),
                M_O_D_O_P_F);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier("eaw", "overworld_deepslate_magic_ore")));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new Identifier("eaw", "magic_flower"), F_O);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier("eaw", "magic_flower"),
                F_O_P_F);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier("eaw", "magic_flower")));
    }
}
