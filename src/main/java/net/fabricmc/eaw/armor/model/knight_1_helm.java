package net.fabricmc.eaw.armor.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class knight_1_helm extends BipedEntityModel<LivingEntity> {

    private final ModelPart bb_main;

    public knight_1_helm(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
        this.bb_main = root.getChild("head");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F))
                .uv(12, 22).cuboid(-2.0F, -10.0F, -5.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.NONE);
        modelPartData.addChild("hat", ModelPartBuilder.create(), ModelTransform.NONE);
        modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.NONE);
        modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.NONE);
        modelPartData.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.NONE);
        modelPartData.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }
}
