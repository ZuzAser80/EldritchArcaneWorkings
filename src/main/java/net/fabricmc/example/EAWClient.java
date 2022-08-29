package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.example.blocks.MagicTableScreen;
import net.fabricmc.example.entity.fire.AdvancedFireballEntity;
import net.fabricmc.example.entity.generic.LandmineEntity;
import net.fabricmc.example.entity.misc.AbstractParticleLeavingEntity;
import net.fabricmc.example.entity.water.AdvancedSnowballEntity;
import net.fabricmc.example.item.model.*;
import net.fabricmc.example.networking.EAWEvents;
import net.fabricmc.example.util.EAWAnimationHelper;
import net.fabricmc.example.worldgen.EAWOres;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

public class EAWClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        //Mod Entities
        AdvancedFireballEntity.clientRegistry();
        AdvancedSnowballEntity.clientRegistry();
        AbstractParticleLeavingEntity.clientRegistry();
        LandmineEntity.clientRegistry();

        //Keybindings
        EAWEvents.registerSwitchSpell1Keybinding();
        EAWEvents.registerSwitchSpell2Keybinding();

        //Item Models
        NoviceStaffEntityModel.registerLayer();
        ApprenticeStaffEntityModel.registerLayer();
        AverageStaffEntityModel.registerLayer();
        MasterStaffEntityModel.registerLayer();
        GodStaffEntityModel.registerLayer();

        //Transparent blocks
        BlockRenderLayerMap.INSTANCE.putBlock(EAWOres.magicFlower, RenderLayer.getCutout());

        //Animation Helper util
        EAWAnimationHelper.eventClient();

        //Screen(s)
        ScreenRegistry.register(ExampleMod.magicTableScreenHandler, MagicTableScreen::new);
    }
}
