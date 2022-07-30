package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.example.entity.AdvancedFireballEntity;
import net.fabricmc.example.entity.AdvancedSnowballEntity;
import net.fabricmc.example.item.model.ApprenticeStaffEntityModel;
import net.fabricmc.example.item.model.NoviceStaffEntityModel;
import net.fabricmc.example.util.EAWAnimationHelper;

public class EAWClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AdvancedFireballEntity.clientRegistry();
        AdvancedSnowballEntity.clientRegistry();
        NoviceStaffEntityModel.registerLayer();
        ApprenticeStaffEntityModel.registerLayer();
        EAWAnimationHelper.eventClient();
    }
}
