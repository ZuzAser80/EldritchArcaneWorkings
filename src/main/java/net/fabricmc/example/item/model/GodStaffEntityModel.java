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

public class GodStaffEntityModel extends AbstractStaffEntityModel {

    private final ModelPart crystal;
    private final ModelPart separate_crystal_1;
    private final ModelPart separate_crystal_0;
    private final ModelPart separate_crystal_3;
    private final ModelPart separate_crystal_2;
    private final ModelPart separate_crystal_4;
    private final ModelPart separate_crystal_5;
    private final ModelPart bb_main;
    List<ModelPart> crystals;

    public static EntityModelLayer layer;

    public GodStaffEntityModel(ModelPart root) {
        super(root);
        this.crystal = root.getChild("crystal");
        this.separate_crystal_0 = crystal.getChild("separate_crystal_0");
        this.separate_crystal_1 = crystal.getChild("separate_crystal_1");
        this.separate_crystal_2 = crystal.getChild("separate_crystal_2");
        this.separate_crystal_3 = crystal.getChild("separate_crystal_3");
        this.separate_crystal_4 = crystal.getChild("separate_crystal_4");
        this.separate_crystal_5 = crystal.getChild("separate_crystal_5");
        crystals = List.of(separate_crystal_0, separate_crystal_1, separate_crystal_2, separate_crystal_3, separate_crystal_4, separate_crystal_5);
        this.bb_main = root.getChild("bb_main");
    }

    @Override
    public List<ModelPart> getChildCrystals() {
        return crystals;
    }

    @Override
    public boolean rotateX() {
        return true;
    }

    @Override
    public boolean rotateY() {
        return true;
    }

    @Override
    public boolean rotateZ() {
        return true;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData crystal = modelPartData.addChild("crystal", ModelPartBuilder.create().uv(20, 2).cuboid(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 6.5F, 0.0F));

        ModelPartData separate_crystal_1 = crystal.addChild("separate_crystal_1", ModelPartBuilder.create().uv(22, 3).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.5F, 0.0F, 0.0F));

        ModelPartData separate_crystal_0 = crystal.addChild("separate_crystal_0", ModelPartBuilder.create().uv(22, 3).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -4.5F));

        ModelPartData separate_crystal_3 = crystal.addChild("separate_crystal_3", ModelPartBuilder.create().uv(22, 3).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 4.5F));

        ModelPartData separate_crystal_2 = crystal.addChild("separate_crystal_2", ModelPartBuilder.create().uv(22, 3).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, 0.0F, 0.0F));

        ModelPartData separate_crystal_4 = crystal.addChild("separate_crystal_4", ModelPartBuilder.create().uv(22, 3).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.5F, 0.0F));

        ModelPartData separate_crystal_5 = crystal.addChild("separate_crystal_5", ModelPartBuilder.create().uv(22, 3).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.5F, 0.0F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(5, 19).cuboid(-1.0F, -10.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5F, -11.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    public ModelPart getCrystal() {
        return crystal;
    }

    @Environment(EnvType.CLIENT)
    public static void registerLayer() {
        layer = new EntityModelLayer(new Identifier("eaw", "god_staff_item"), "main");
        EntityModelLayerRegistry.registerModelLayer(layer, GodStaffEntityModel::getTexturedModelData);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
