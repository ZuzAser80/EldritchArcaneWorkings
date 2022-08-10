package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.example.blocks.MagicTableScreen;
import net.fabricmc.example.entity.fire.AdvancedFireballEntity;
import net.fabricmc.example.entity.misc.AbstractParticleLeavingEntity;
import net.fabricmc.example.entity.water.AdvancedSnowballEntity;
import net.fabricmc.example.entity.water.IceShieldEntity;
import net.fabricmc.example.item.model.ApprenticeStaffEntityModel;
import net.fabricmc.example.item.model.AverageStaffEntityModel;
import net.fabricmc.example.item.model.MasterStaffEntityModel;
import net.fabricmc.example.item.model.NoviceStaffEntityModel;
import net.fabricmc.example.networking.EAWEvents;
import net.fabricmc.example.util.EAWAnimationHelper;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class EAWClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        //Mod Entities
        AdvancedFireballEntity.clientRegistry();
        AdvancedSnowballEntity.clientRegistry();
        AbstractParticleLeavingEntity.clientRegistry();
        IceShieldEntity.clientRegistry();

        //Keybindings
        EAWEvents.registerSwitchSpell1Keybinding();
        EAWEvents.registerSwitchSpell2Keybinding();

        //Item Models
        NoviceStaffEntityModel.registerLayer();
        ApprenticeStaffEntityModel.registerLayer();
        AverageStaffEntityModel.registerLayer();
        MasterStaffEntityModel.registerLayer();

        //Animation Helper util
        EAWAnimationHelper.eventClient();

        //Screen(s)
        ScreenRegistry.register(ExampleMod.magicTableScreenHandler, MagicTableScreen::new);
    }
}
