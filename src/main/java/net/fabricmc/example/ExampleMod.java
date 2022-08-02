package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.blocks.RodConstructionRecipe;
import net.fabricmc.example.blocks.RodConstructionScreenHandler;
import net.fabricmc.example.blocks.RodConstructionTable;
import net.fabricmc.example.entity.misc.AbstractParticleLeavingEntity;
import net.fabricmc.example.entity.fire.AdvancedFireballEntity;
import net.fabricmc.example.entity.water.AdvancedSnowballEntity;
import net.fabricmc.example.entity.water.IceShieldEntity;
import net.fabricmc.example.item.AbstractMagicRodItem;
import net.fabricmc.example.networking.EAWEvents;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {

	public static Block rodConstructionStation;
	public static RecipeType<RodConstructionRecipe> rodConstructionType;

	@Override
	public void onInitialize() {

		Registry.register(Registry.ITEM, new Identifier("eaw", "novice_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1), 20, SpellRank.NOVICE, 3));
		Registry.register(Registry.ITEM, new Identifier("eaw", "apprentice_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1), 50, SpellRank.APPRENTICE, 5));
		rodConstructionStation = Registry.register(Registry.BLOCK, new Identifier("eaw", "rod_construction_station_block"), new RodConstructionTable(FabricBlockSettings.of(Material.METAL).strength(10, 10).requiresTool()));
		Registry.register(Registry.ITEM, new Identifier("eaw", "rod_construction_station_item"), new BlockItem(rodConstructionStation, new Item.Settings().maxCount(1)));

		AdvancedFireballEntity.registry();
		AdvancedSnowballEntity.registry();
		AbstractParticleLeavingEntity.registry();
		IceShieldEntity.registry();

		ServerPlayNetworking.registerGlobalReceiver(new Identifier("eaw", "switch_spell_1_packet"), EAWEvents::SpellSwitch1S2CPacket);
        ServerPlayNetworking.registerGlobalReceiver(new Identifier("eaw", "switch_spell_2_packet"), EAWEvents::SpellSwitch2S2CPacket);

		Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("eaw:rod_construction"),
				RodConstructionRecipe.INSTANCE);
		rodConstructionType = Registry.register(Registry.RECIPE_TYPE, new Identifier("eaw", "two_slot_recipe"), RodConstructionRecipe.Type.INSTANCE);
    }
}
