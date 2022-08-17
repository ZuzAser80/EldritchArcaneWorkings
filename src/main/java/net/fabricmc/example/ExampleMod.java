package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.blocks.MagicTableBlock;
import net.fabricmc.example.blocks.MagicTableBlockEntity;
import net.fabricmc.example.blocks.MagicTableScreenHandler;
import net.fabricmc.example.entity.fire.AdvancedFireballEntity;
import net.fabricmc.example.entity.generic.LandmineEntity;
import net.fabricmc.example.entity.misc.AbstractParticleLeavingEntity;
import net.fabricmc.example.entity.water.AdvancedSnowballEntity;
import net.fabricmc.example.item.AbstractMagicRodItem;
import net.fabricmc.example.item.AbstractSpellBookItem;
import net.fabricmc.example.networking.EAWEvents;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.spells.*;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {

	public static BlockEntityType<MagicTableBlockEntity> magicTableEntity;
	public static Block magicTable;
	public static ScreenHandlerType<MagicTableScreenHandler> magicTableScreenHandler;
	public static AbstractSpellBookItem flameSpellBook;

	@Override
	public void onInitialize() {

		Registry.register(Registry.ITEM, new Identifier("eaw", "novice_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1), 20, SpellRank.NOVICE, 3, 3));
		Registry.register(Registry.ITEM, new Identifier("eaw", "apprentice_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1), 50, SpellRank.APPRENTICE, 5, 4));
		Registry.register(Registry.ITEM, new Identifier("eaw", "average_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON), 75, SpellRank.AVERAGE, 5, 5));
		Registry.register(Registry.ITEM, new Identifier("eaw", "master_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE), 75, SpellRank.MASTER, 7, 7));

		Registry.register(Registry.ITEM, new Identifier("eaw", "empty_spell_book"), new AbstractSpellBookItem(null));

		Registry.register(Registry.ITEM, new Identifier("eaw", "crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "red_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "blue_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "green_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "lime_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "light_gray_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "light_blue_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "cyan_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "orange_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "yellow_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "white_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "gray_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "black_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "pink_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "magenta_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "purple_crystal"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "brown_crystal"), new Item(new Item.Settings().maxCount(1)));

		Registry.register(Registry.ITEM, new Identifier("eaw", "oak_rod"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "spruce_rod"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "birch_rod"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "jungle_rod"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "acacia_rod"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "dark_oak_rod"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "crimson_rod"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "warped_rod"), new Item(new Item.Settings().maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier("eaw", "mangrove_rod"), new Item(new Item.Settings().maxCount(1)));

		flameSpellBook = Registry.register(Registry.ITEM, new Identifier("eaw", "flame_spell_book"), new AbstractSpellBookItem(new FlameSpell()));

		AdvancedFireballEntity.registry();
		AdvancedSnowballEntity.registry();
		AbstractParticleLeavingEntity.registry();
		LandmineEntity.registry();

		ServerPlayNetworking.registerGlobalReceiver(new Identifier("eaw", "switch_spell_1_packet"), EAWEvents::SpellSwitch1S2CPacket);
        ServerPlayNetworking.registerGlobalReceiver(new Identifier("eaw", "switch_spell_2_packet"), EAWEvents::SpellSwitch2S2CPacket);

        magicTable = Registry.register(Registry.BLOCK, new Identifier("eaw", "magic_table_block"), new MagicTableBlock(AbstractBlock.Settings.of(Material.CACTUS)));

        magicTableEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("eaw", "magic_table_block_entity"), FabricBlockEntityTypeBuilder.create(MagicTableBlockEntity::new, magicTable).build(null));

        magicTableScreenHandler = ScreenHandlerRegistry.registerSimple(new Identifier("eaw", "magic_table_block"), MagicTableScreenHandler::new);

        //Spell registry
		new FlameSpell();
		new FireLandmineSpell();
		new LeapSpell();
		new AdvancedFireballSpell();
		new AdvancedSnowballSpell();
		new IceShieldSpell();
	}
}
