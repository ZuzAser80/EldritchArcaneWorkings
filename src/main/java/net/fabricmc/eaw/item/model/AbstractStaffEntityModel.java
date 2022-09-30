package net.fabricmc.eaw.item.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public abstract class AbstractStaffEntityModel extends Model {

    ModelPart root;

    public AbstractStaffEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public abstract ModelPart getCrystal();
    public abstract List<ModelPart> getChildCrystals();

    public abstract boolean rotateX();
    public abstract boolean rotateY();
    public abstract boolean rotateZ();
    @Override
    public abstract void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha);
}
