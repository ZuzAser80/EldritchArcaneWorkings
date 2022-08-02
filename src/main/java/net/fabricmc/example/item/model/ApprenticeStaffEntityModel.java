package net.fabricmc.example.item.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ApprenticeStaffEntityModel extends AbstractStaffEntityModel {

    private final ModelPart crystal;
    private final ModelPart bb_main;
    public static EntityModelLayer layer;

    public ApprenticeStaffEntityModel(ModelPart root) {
        super(root);
        this.crystal = root.getChild("crystal");
        this.bb_main = root.getChild("bb_main");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData crystal = modelPartData.addChild("crystal", ModelPartBuilder.create().uv(20, 2).mirrored().cuboid(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 7.0F, 0.0F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(1, 0).cuboid(-0.5F, -13.0F, -0.5F, 1.0F, 13.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 0).cuboid(-1.0F, -14.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Environment(EnvType.CLIENT)
    public static void registerLayer() {
        layer = new EntityModelLayer(new Identifier("eaw", "apprentice_staff_item"), "main");
        EntityModelLayerRegistry.registerModelLayer(layer, ApprenticeStaffEntityModel::getTexturedModelData);
    }
    @Override
    public boolean rotateX(){
        return false;
    }
    @Override
    public boolean rotateY(){
        return true;
    }
    @Override
    public boolean rotateZ(){
        return false;
    }
    @Override
    public ModelPart getCrystal() {
        return crystal;
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
