package net.fabricmc.example.item.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class AbstractStaffEntityModel extends Model {

    ModelPart root;

    public AbstractStaffEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public ModelPart getCrystal() {
        return null;
    }

    public boolean rotateX() {
        return true;
    }
    public boolean rotateY() {
        return true;
    }
    public boolean rotateZ() {
        return true;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
