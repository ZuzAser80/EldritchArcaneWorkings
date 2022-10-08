package net.fabricmc.eaw;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.eaw.armor.Knight1ArmorMaterial;
import net.fabricmc.eaw.armor.model.knight_1_chest;
import net.fabricmc.eaw.armor.model.knight_1_helm;
import net.fabricmc.eaw.blocks.MagicTableScreen;
import net.fabricmc.eaw.entity.fire.AdvancedFireballEntity;
import net.fabricmc.eaw.entity.generic.LandmineEntity;
import net.fabricmc.eaw.entity.ground.RunePebbleEntity;
import net.fabricmc.eaw.entity.misc.AbstractParticleLeavingEntity;
import net.fabricmc.eaw.entity.water.AdvancedSnowballEntity;
import net.fabricmc.eaw.item.model.*;
import net.fabricmc.eaw.networking.EAWEvents;
import net.fabricmc.eaw.util.EAWAnimationHelper;
import net.fabricmc.eaw.worldgen.EAWOres;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class EAWClient implements ClientModInitializer {

    private BipedEntityModel<LivingEntity> armorModel;

    @Override
    public void onInitializeClient() {

        //Mod Entities
        AdvancedFireballEntity.clientRegistry();
        AdvancedSnowballEntity.clientRegistry();
        AbstractParticleLeavingEntity.clientRegistry();
        LandmineEntity.clientRegistry();
        RunePebbleEntity.clientRegistry();

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

        renderArmor(ExampleMod.Knight1Chest, EquipmentSlot.CHEST, new Identifier("eaw", "textures/entity/armor/knight_1_chest_entity.png"), new Identifier("eaw", "knight_1_chest_entity"), "knight_1_chest_render_layer", knight_1_chest::getTexturedModelData);
        renderArmor(ExampleMod.Knight1Helm, EquipmentSlot.HEAD, new Identifier("eaw", "textures/entity/armor/knight_1_helm_entity.png"), new Identifier("eaw", "knight_1_head_entity"), "knight_1_head_render_layer", knight_1_helm::getTexturedModelData);
    }
    public void renderArmor(Item item, EquipmentSlot equipmentSlot, Identifier texture, Identifier layerId, String layerName, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer layer = new EntityModelLayer(layerId, layerName);
        EntityModelLayerRegistry.registerModelLayer(layer, provider);
        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, model) -> {
            armorModel = new BipedEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(layer));
            model.setAttributes(armorModel);
            armorModel.setVisible(slot == equipmentSlot);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, texture);
        }, item);
    }
}
