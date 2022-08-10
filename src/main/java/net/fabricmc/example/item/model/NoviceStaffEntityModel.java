package net.fabricmc.example.item.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class NoviceStaffEntityModel extends AbstractStaffEntityModel {
    private final ModelPart crystal;
    private final ModelPart bb_main;
    private final ModelPart cube_r1;
    private final ModelPart cube_r2;

    public static EntityModelLayer layer;
    public NoviceStaffEntityModel(ModelPart root) {
        super(root);
        this.crystal = root.getChild("crystal");
        this.bb_main = root.getChild("bb_main");
        cube_r1 = bb_main.getChild("cube_r1");
        cube_r2 = bb_main.getChild("cube_r2");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData crystal = modelPartData.addChild("crystal", ModelPartBuilder.create().uv(8, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 3).cuboid(-0.5F, -9.0F, -0.5F, 1.0F, 12.0F, 1.0F, new Dilation(0.0F))
                .uv(12, 12).cuboid(-0.5F, -13.4142F, 1.9142F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -14.8284F, -1.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(4, 13).cuboid(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(8, 13).cuboid(7.0F, -2.0142F, -0.6716F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-7.5F, -9.2222F, 1.2577F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r2 = bb_main.addChild("cube_r2", ModelPartBuilder.create().uv(3, 6).cuboid(7.0F, -2.0142F, -0.6716F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-7.5F, -13.1719F, 2.692F, 0.7854F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }
    @Environment(EnvType.CLIENT)
    public static void registerLayer() {
        layer = new EntityModelLayer(new Identifier("eaw", "novice_staff_item"), "main");
        EntityModelLayerRegistry.registerModelLayer(layer, NoviceStaffEntityModel::getTexturedModelData);
    }
    @Override
    public boolean rotateX(){
        return true;
    }
    @Override
    public boolean rotateY(){
        return true;
    }
    @Override
    public boolean rotateZ(){
        return true;
    }
    @Override
    public ModelPart getCrystal() {
        return crystal;
    }
    @Override
    public List<ModelPart> getChildCrystals() {
        return null;
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        crystal.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
