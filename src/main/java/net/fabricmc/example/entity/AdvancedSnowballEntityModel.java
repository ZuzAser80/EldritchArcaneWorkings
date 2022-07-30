package net.fabricmc.example.entity;

import net.fabricmc.example.util.EAWAnimationHelper;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Direction;

public class AdvancedSnowballEntityModel extends EntityModel<AdvancedSnowballEntity> {

    private final ModelPart bb_main;
    private EAWAnimationHelper helper = EAWAnimationHelper.begin(0, 0, 0, 0);

    public AdvancedSnowballEntityModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 4).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if(!helper.getIsPlaying()) {
            helper = EAWAnimationHelper.begin(40, 0, 0, 4.5);
        }
        matrices.multiply(helper.getQuaternion(Direction.Axis.Z));
        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(AdvancedSnowballEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
