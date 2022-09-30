package net.fabricmc.eaw;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.eaw.blocks.MagicTableBlock;
import net.fabricmc.eaw.blocks.MagicTableBlockEntity;
import net.fabricmc.eaw.blocks.MagicTableScreenHandler;
import net.fabricmc.eaw.entity.fire.AdvancedFireballEntity;
import net.fabricmc.eaw.entity.generic.LandmineEntity;
import net.fabricmc.eaw.entity.ground.RunePebbleEntity;
import net.fabricmc.eaw.entity.misc.AbstractParticleLeavingEntity;
import net.fabricmc.eaw.entity.water.AdvancedSnowballEntity;
import net.fabricmc.eaw.item.AbstractMagicRodItem;
import net.fabricmc.eaw.item.AbstractSpellBookItem;
import net.fabricmc.eaw.networking.EAWEvents;
import net.fabricmc.eaw.rune.Rune;
import net.fabricmc.eaw.rune.RuneItem;
import net.fabricmc.eaw.rune.RuneStrength;
import net.fabricmc.eaw.rune.RuneType;
import net.fabricmc.eaw.spell.SpellRank;
import net.fabricmc.eaw.spell.spells.*;
import net.fabricmc.eaw.worldgen.EAWOres;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {

	public static Item emptySpellBook;
	public static BlockEntityType<MagicTableBlockEntity> magicTableEntity;
	public static Block magicTable;
	public static ScreenHandlerType<MagicTableScreenHandler> magicTableScreenHandler;
	public static Item magicCrystal;
	public static Item icon;
	public static Item n_s, app_S, av_S, m_S, g_S;
	public static ItemGroup group = FabricItemGroupBuilder.build(
			new Identifier("eaw", "main"),
        () -> new ItemStack(icon));

	public static void doThing(ItemStack stack){
		stack.getOrCreateNbt().putString("rod", "oak_rod");
		stack.getOrCreateNbt().putString("crystal", "crystal");
	}

	ItemGroup OTHER_GROUP = FabricItemGroupBuilder.create(
			new Identifier("eaw", "rods"))
			.icon(() -> new ItemStack(magicCrystal))
			.appendItems(stacks -> {
				ItemStack n_st = new ItemStack(n_s), app_St = new ItemStack(app_S), av_St = new ItemStack(av_S), m_St = new ItemStack(m_S), g_St = new ItemStack(g_S);

				doThing(n_st);
				doThing(app_St);
				doThing(av_St);
				doThing(m_St);
				doThing(g_St);
				stacks.add(n_st);
				stacks.add(app_St.copy());
				stacks.add(av_St.copy());
				stacks.add(m_St.copy());
				stacks.add(g_St);
			})
			.build();

	@Override
	public void onInitialize() {

		magicTable = Registry.register(Registry.BLOCK, new Identifier("eaw", "magic_table_block"), new MagicTableBlock(AbstractBlock.Settings.of(Material.METAL).strength(8, 15).requiresTool()));

		magicTableEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("eaw", "magic_table_block_entity"), FabricBlockEntityTypeBuilder.create(MagicTableBlockEntity::new, magicTable).build(null));

		magicTableScreenHandler = ScreenHandlerRegistry.registerSimple(new Identifier("eaw", "magic_table_block"), MagicTableScreenHandler::new);

		n_s = Registry.register(Registry.ITEM, new Identifier("eaw", "novice_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1), 20, SpellRank.novice));
		app_S = Registry.register(Registry.ITEM, new Identifier("eaw", "apprentice_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1), 50, SpellRank.apprentice));
		av_S = Registry.register(Registry.ITEM, new Identifier("eaw", "average_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON), 75, SpellRank.average));
		m_S = Registry.register(Registry.ITEM, new Identifier("eaw", "master_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE), 75, SpellRank.master));
		g_S = Registry.register(Registry.ITEM, new Identifier("eaw", "god_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), 150, SpellRank.god));

		emptySpellBook = Registry.register(Registry.ITEM, new Identifier("eaw", "empty_spell_book"), new AbstractSpellBookItem());

		magicCrystal = Registry.register(Registry.ITEM, new Identifier("eaw", "magic_crystal"), new Item(new Item.Settings().group(group).maxCount(64)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "red_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "blue_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "green_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "lime_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "light_gray_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "light_blue_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "cyan_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "orange_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "yellow_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "white_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "gray_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "black_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "pink_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "magenta_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "purple_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "brown_crystal"), new Item(new Item.Settings().group(group).maxCount(1)));
		Item fire_rune_1_1 = Registry.register(Registry.ITEM, new Identifier("eaw", "fire_rune_1_1"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.WEAK, RuneType.FIRE, "Weak Fiery Touch")));
		Item fire_rune_1_2 = Registry.register(Registry.ITEM, new Identifier("eaw", "fire_rune_1_2"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.NORMAL, RuneType.FIRE, "Normal Fiery Touch")));
		Item fire_rune_1_3 = Registry.register(Registry.ITEM, new Identifier("eaw", "fire_rune_1_3"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.STRONG, RuneType.FIRE, "Strong Fiery Touch")));
		Item water_rune_1_1 = Registry.register(Registry.ITEM, new Identifier("eaw", "water_rune_1_1"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.WEAK, RuneType.WATER, "Weak Pocket Riptide")));
		Item water_rune_1_2 = Registry.register(Registry.ITEM, new Identifier("eaw", "water_rune_1_2"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.NORMAL, RuneType.WATER, "Normal Pocket Riptide")));
		Item water_rune_1_3 = Registry.register(Registry.ITEM, new Identifier("eaw", "water_rune_1_3"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.STRONG, RuneType.WATER, "Strong Pocket Riptide")));
		Item ground_rune_1_1 = Registry.register(Registry.ITEM, new Identifier("eaw", "ground_rune_1_1"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.WEAK, RuneType.GROUND, "Weak Rocky Storm")));
		Item ground_rune_1_2 = Registry.register(Registry.ITEM, new Identifier("eaw", "ground_rune_1_2"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.NORMAL, RuneType.GROUND, "Normal Rocky Storm")));
		Item ground_rune_1_3 = Registry.register(Registry.ITEM, new Identifier("eaw", "ground_rune_1_3"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.STRONG, RuneType.GROUND, "Strong Rocky Storm")));
		Item air_rune_1_1 = Registry.register(Registry.ITEM, new Identifier("eaw", "air_rune_1_1"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.WEAK, RuneType.AIR, "Weak Light Punch")));
		Item air_rune_1_2 = Registry.register(Registry.ITEM, new Identifier("eaw", "air_rune_1_2"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.NORMAL, RuneType.AIR, "Normal Light Punch")));
		Item air_rune_1_3 = Registry.register(Registry.ITEM, new Identifier("eaw", "air_rune_1_3"), new RuneItem(new Item.Settings().group(group).maxCount(1), new Rune(RuneStrength.STRONG, RuneType.AIR, "Strong Light Punch")));

		Item rune_core = Registry.register(Registry.ITEM, new Identifier("eaw", "rune_core"), new Item(new Item.Settings().fireproof().group(group)));

		Registry.register(Registry.ITEM, new Identifier("eaw", "oak_rod"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "spruce_rod"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "birch_rod"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "jungle_rod"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "acacia_rod"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "dark_oak_rod"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "crimson_rod"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "warped_rod"), new Item(new Item.Settings().group(group).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "mangrove_rod"), new Item(new Item.Settings().group(group).maxCount(1)));

		Registry.register(Registry.ITEM, new Identifier("eaw", "magic_table"), new BlockItem(magicTable, new Item.Settings().group(group).rarity(Rarity.UNCOMMON)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "magic_ore"), new BlockItem(EAWOres.magicOre, new Item.Settings()));
		icon = Registry.register(Registry.ITEM, new Identifier("eaw", "deepslate_magic_ore"), new BlockItem(EAWOres.deepslateMagicOre, new Item.Settings()));
		Registry.register(Registry.ITEM, new Identifier("eaw", "magic_flower"), new BlockItem(EAWOres.magicFlower, new Item.Settings()));

		AdvancedFireballEntity.registry();
		AdvancedSnowballEntity.registry();
		RunePebbleEntity.registry();
		AbstractParticleLeavingEntity.registry();
		LandmineEntity.registry();

		ServerPlayNetworking.registerGlobalReceiver(new Identifier("eaw", "switch_spell_1_packet"), EAWEvents::SpellSwitch1S2CPacket);
        ServerPlayNetworking.registerGlobalReceiver(new Identifier("eaw", "switch_spell_2_packet"), EAWEvents::SpellSwitch2S2CPacket);

		EAWOres.registerOres();
		//Spell registry
		//Necessary
		new AdvancedFireballSpell();
		new AdvancedSnowballSpell();
		new EmptySpell();
		new FireballSpell();
		new FireLandmineSpell();
		new FlameSpell();
		new IceShieldSpell();
		new LeapSpell();
		new SnowballSpell();

		ItemStack spellBook = new ItemStack(emptySpellBook);
		spellBook.getOrCreateNbt().put("spell", new LeapSpell().toNbt());

		changeLoot(LootTables.END_CITY_TREASURE_CHEST, magicCrystal);
		changeLoot(LootTables.BASTION_BRIDGE_CHEST, magicCrystal);
		changeLoot(LootTables.END_CITY_TREASURE_CHEST, air_rune_1_3);
		changeLoot(LootTables.BASTION_BRIDGE_CHEST, fire_rune_1_2);
		changeLoot(LootTables.END_CITY_TREASURE_CHEST, air_rune_1_1);
		changeLoot(LootTables.BASTION_BRIDGE_CHEST, fire_rune_1_1);
		changeLoot(LootTables.IGLOO_CHEST_CHEST, water_rune_1_2);
		changeLoot(LootTables.IGLOO_CHEST_CHEST, water_rune_1_3);
		changeLoot(LootTables.RUINED_PORTAL_CHEST, ground_rune_1_2);
		changeLoot(LootTables.BASTION_BRIDGE_CHEST, rune_core);
		changeLoot(LootTables.END_CITY_TREASURE_CHEST, rune_core);
		changeLoot(LootTables.BASTION_BRIDGE_CHEST, rune_core);
		changeLoot(LootTables.IGLOO_CHEST_CHEST, rune_core);
		changeLoot(LootTables.RUINED_PORTAL_CHEST, rune_core);
		changeLoot(LootTables.BASTION_BRIDGE_CHEST, spellBook);
		changeLoot(LootTables.END_CITY_TREASURE_CHEST, spellBook);
		spellBook.getOrCreateNbt().put("spell", new IceShieldSpell().toNbt());
		changeLoot(LootTables.END_CITY_TREASURE_CHEST, spellBook);
		changeLoot(LootTables.IGLOO_CHEST_CHEST, spellBook);
		spellBook.getOrCreateNbt().put("spell", new AdvancedSnowballSpell().toNbt());
		changeLoot(LootTables.IGLOO_CHEST_CHEST, spellBook);
		spellBook.getOrCreateNbt().put("spell", new AdvancedFireballSpell().toNbt());
		changeLoot(LootTables.NETHER_BRIDGE_CHEST, spellBook);
		changeLoot(LootTables.RUINED_PORTAL_CHEST, spellBook);
		changeLoot(LootTables.BASTION_BRIDGE_CHEST, spellBook);
		spellBook.getOrCreateNbt().put("spell", new FlameSpell().toNbt());
		changeLoot(LootTables.NETHER_BRIDGE_CHEST, spellBook);
		changeLoot(LootTables.RUINED_PORTAL_CHEST, spellBook);
		changeLoot(LootTables.BASTION_BRIDGE_CHEST, spellBook);
	}
	public static void changeLoot(Identifier identifier, Item replacement) {
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (identifier.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.builder()
						.with(ItemEntry.builder(replacement))
						.conditionally(SurvivesExplosionLootCondition.builder());
				tableBuilder.pool(pool);
			}
		});
	}
	public static void changeLoot(Identifier identifier, ItemStack replacement) {
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (identifier.equals(id) && source.isBuiltin()) {
				LootPool.Builder pool = LootPool.builder()
						.with(addSpellBook(replacement))
						.conditionally(SurvivesExplosionLootCondition.builder());
				tableBuilder.pool(pool);
			}
		});
	}
	public static ItemEntry.Builder<?> addSpellBook(ItemStack stack)
	{
		ItemEntry.Builder<?> builder = ItemEntry.builder(stack.getItem());
		if (stack.getNbt() != null) {
			builder.apply(SetNbtLootFunction.builder(stack.getNbt()));
		}
		if (stack.getCount() > 1) {
			builder.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(stack.getCount())));
		}
		return builder;
	}

}
