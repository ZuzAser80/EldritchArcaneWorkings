package net.fabricmc.eaw.armor.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class knight_1_chest extends BipedEntityModel<LivingEntity> {

	private final ModelPart bb_main;
	private final ModelPart cube_r1;
	private final ModelPart cube_r2;
	private final ModelPart cube_r3;
	private final ModelPart cube_r4;

	public knight_1_chest(ModelPart root) {
		super(root, RenderLayer::getArmorCutoutNoCull);

		this.bb_main = root.getChild("body");
		cube_r1 = bb_main.getChild("cube_r1");
		cube_r2 = bb_main.getChild("cube_r2");
		cube_r3 = bb_main.getChild("cube_r3");
		cube_r4 = bb_main.getChild("cube_r4");
	}
	public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("body", ModelPartBuilder.create().uv(25, 12).cuboid(2.0F, -1.0F, -2.0F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(25, 12).cuboid(-5.0F, -1.0F, -2.0F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(5, 4).cuboid(-5.0F, 2.6955F, -3.5307F, 10.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(0, 8).cuboid(-5.0F, -4.4151F, -1.1068F, 10.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.5026F, 2.6671F, -0.3927F, 3.1416F, 0.0F));

        ModelPartData cube_r2 = bb_main.addChild("cube_r2", ModelPartBuilder.create().uv(0, 12).cuboid(-5.0F, -0.8071F, -0.8637F, 10.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.5026F, 2.6671F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r3 = bb_main.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -1.0F, 0.0F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 6.6194F, -3.1481F, 0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r4 = bb_main.addChild("cube_r4", ModelPartBuilder.create().uv(21, 0).cuboid(-5.0F, 0.0F, -1.0F, 10.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.6173F, -1.0761F, -0.3927F, 0.0F, 0.0F));

		modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("hat", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.NONE);
		return TexturedModelData.of(modelData, 32, 32);
	}
}