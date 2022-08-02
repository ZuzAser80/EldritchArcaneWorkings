package net.fabricmc.example.mixin;

import net.fabricmc.example.item.AbstractMagicRodItem;
import net.fabricmc.example.item.model.AbstractStaffEntityModel;
import net.fabricmc.example.item.model.ApprenticeStaffEntityModel;
import net.fabricmc.example.item.model.NoviceStaffEntityModel;
import net.fabricmc.example.util.EAWAnimationHelper;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltInItemModelMixin {

    //hollow thing to make `helper != null` always true
    EAWAnimationHelper helper = EAWAnimationHelper.begin(0, 0, 0, 0);

    @Final @Shadow private EntityModelLoader entityModelLoader;

    protected NoviceStaffEntityModel noviceStaffModel;
    protected ApprenticeStaffEntityModel apprenticeStaffModel;

    @Inject(at = @At("HEAD"), method = "reload")
    public void reloadInject(ResourceManager manager, CallbackInfo ci) {
        noviceStaffModel = new NoviceStaffEntityModel(entityModelLoader.getModelPart(NoviceStaffEntityModel.layer));
        apprenticeStaffModel = new ApprenticeStaffEntityModel(entityModelLoader.getModelPart(ApprenticeStaffEntityModel.layer));
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void loadStaffModels(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if(stack.getItem() instanceof AbstractMagicRodItem) {
            matrices.multiply(new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(180)));
            matrices.multiply(new Quaternion(Vec3f.POSITIVE_Y.getDegreesQuaternion(180)));
            matrices.translate(-0.5, -1.5, 0.5);
            renderStaff(stack.getTranslationKey(), stack, matrices, light, overlay, vertexConsumers);
        }
    }
    public void renderStaff(String translationKey, ItemStack stack, MatrixStack matrices, int light, int overlay, VertexConsumerProvider vertexConsumers) {
        AbstractStaffEntityModel model;
        Identifier textureId;
        switch (translationKey) {
            case "item.eaw.novice_staff" -> {
                model = noviceStaffModel;
                textureId = new Identifier("eaw", "textures/entity/novice_staff_entity.png");
            }
            case "item.eaw.apprentice_staff" -> {
                model = apprenticeStaffModel;
                textureId = new Identifier("eaw", "textures/entity/apprentice_staff_entity.png");
            }
            default -> {
                model = null;
                textureId = null;
            }
        }
        if(model != null) {
            if (!helper.getIsPlaying()) {
                helper = EAWAnimationHelper.begin(1800, 0.1f, 0.1f, 0.1f);
            } else {
                if(model.rotateX()) {
                    model.getCrystal().pitch = helper.getOffset(Direction.Axis.X);
                }
                if(model.rotateY()) {
                    model.getCrystal().yaw = helper.getOffset(Direction.Axis.Y);
                }
                if(model.rotateZ()) {
                    model.getCrystal().roll = helper.getOffset(Direction.Axis.Z);
                }
            }
            VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.noviceStaffModel.getLayer(textureId), true, false);
            if(model == apprenticeStaffModel) {
                VertexConsumer crystalVertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.noviceStaffModel.getLayer(new Identifier("eaw", "textures/entity/crystal/apprentice_staff_green_crystal.png")), true, false);
                model.getCrystal().render(matrices, crystalVertexConsumer, light, overlay, 1, 1, 1, 1);
            }
            model.render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
