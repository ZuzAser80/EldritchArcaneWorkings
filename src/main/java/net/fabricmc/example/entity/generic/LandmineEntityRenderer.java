package net.fabricmc.example.entity.generic;

import net.fabricmc.example.entity.water.AdvancedSnowballEntity;
import net.fabricmc.example.entity.water.AdvancedSnowballEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class LandmineEntityRenderer extends EntityRenderer<LandmineEntity> {

    LandmineEntityModel model = new LandmineEntityModel(LandmineEntityModel.getTexturedModelData().createModel());

    protected LandmineEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(LandmineEntity tridentEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(tridentEntity)), false, false);
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(LandmineEntity entity) {
        Identifier id;
        if(entity.spell != null) {
            switch (entity.spell.getType()) {
                case FIRE -> id = new Identifier("eaw", "textures/entity/fire_landmine_entity.png");
                case WATER -> id = new Identifier("eaw", "textures/entity/water_landmine_entity.png");
                default -> throw new IllegalStateException("Unexpected value: " + entity.spell);
            }
            return id;
        }else {
            return new Identifier("minecraft", "textures/block/obsidian");
        }
    }
}
