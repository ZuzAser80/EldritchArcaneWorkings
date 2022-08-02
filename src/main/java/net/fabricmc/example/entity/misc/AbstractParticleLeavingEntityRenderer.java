package net.fabricmc.example.entity.misc;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class AbstractParticleLeavingEntityRenderer extends EntityRenderer<AbstractParticleLeavingEntity> {

    protected AbstractParticleLeavingEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(AbstractParticleLeavingEntity entity) {
        return null;
    }
}
