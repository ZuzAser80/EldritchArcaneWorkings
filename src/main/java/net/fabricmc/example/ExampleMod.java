package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.entity.AdvancedFireballEntity;
import net.fabricmc.example.entity.AdvancedSnowballEntity;
import net.fabricmc.example.item.AbstractMagicRodItem;
import net.fabricmc.example.spell.SpellRank;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {


	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("eaw", "novice_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1), 20, SpellRank.NOVICE, 3));
		Registry.register(Registry.ITEM, new Identifier("eaw", "apprentice_staff"), new AbstractMagicRodItem(new Item.Settings().maxCount(1), 50, SpellRank.APPRENTICE, 5));
		AdvancedFireballEntity.registry();
		AdvancedSnowballEntity.registry();
	}
}
