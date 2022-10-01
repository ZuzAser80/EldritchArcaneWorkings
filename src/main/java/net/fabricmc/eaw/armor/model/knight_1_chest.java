package net.fabricmc.eaw.armor.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class knight_1_chest extends BipedEntityModel<LivingEntity> {
	private final ModelPart bb_main;
	public knight_1_chest(ModelPart root) {
		super(root, RenderLayer::getArmorCutoutNoCull);
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, -3.0F, 10.0F, 15.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-5.0F, 0.0F, 2.0F, 10.0F, 15.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(2.0F, 0.0F, -2.0F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-5.0F, 0.0F, -2.0F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(0.0112F, -3.8268F, -2.5F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		ModelPartData cube_r2 = bb_main.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -4.0F, -2.5F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));
		modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("hat", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 64, 64);
	}
}