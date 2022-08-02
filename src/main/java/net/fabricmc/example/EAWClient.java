package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.example.blocks.RodConstructionScreenHandler;
import net.fabricmc.example.entity.misc.AbstractParticleLeavingEntity;
import net.fabricmc.example.entity.fire.AdvancedFireballEntity;
import net.fabricmc.example.entity.water.AdvancedSnowballEntity;
import net.fabricmc.example.entity.water.IceShieldEntity;
import net.fabricmc.example.item.model.ApprenticeStaffEntityModel;
import net.fabricmc.example.item.model.NoviceStaffEntityModel;
import net.fabricmc.example.networking.EAWEvents;
import net.fabricmc.example.util.EAWAnimationHelper;

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

        //Animation Helper util
        EAWAnimationHelper.eventClient();

        //Screens
        RodConstructionScreenHandler.register();
    }
}