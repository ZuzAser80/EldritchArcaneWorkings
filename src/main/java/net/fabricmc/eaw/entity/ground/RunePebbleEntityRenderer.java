package net.fabricmc.eaw.entity.ground;

import net.fabricmc.eaw.entity.fire.AdvancedFireballEntityModel;
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

public class RunePebbleEntityRenderer extends EntityRenderer<RunePebbleEntity> {

    AdvancedFireballEntityModel model = new AdvancedFireballEntityModel(AdvancedFireballEntityModel.getTexturedModelData().createModel());

    public RunePebbleEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(RunePebbleEntity tridentEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(tridentEntity)), false, false);

        matrixStack.scale(tridentEntity.size * 0.5f, tridentEntity.size * 0.5f, tridentEntity.size * 0.5f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, tridentEntity.prevYaw, tridentEntity.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, tridentEntity.prevPitch, tridentEntity.getPitch()) + 90.0F));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(RunePebbleEntity entity) {
        return new Identifier("eaw", "textures/entity/rune_pebble.png");
    }

}
