package net.fabricmc.eaw.entity.generic;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.eaw.entity.water.AdvancedSnowballEntityModel;
import net.fabricmc.eaw.networking.AdvancedFireballEntitySpawnPacket;
import net.fabricmc.eaw.spell.Spell;
import net.fabricmc.eaw.spell.spells.FlameSpell;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LandmineEntity extends PersistentProjectileEntity {

    public static EntityType<LandmineEntity> type;
    private final Map<Entity, Integer> affectedEntities;
    private int entityAge = 0;

    Spell spell;

    public LandmineEntity(World world, Spell spell) {
        super(type, world);
        this.spell = spell;
        this.affectedEntities = Maps.newHashMap();
    }

    public static void registry() {
        type = Registry.register(Registry.ENTITY_TYPE, new Identifier("eaw", "landmine"), FabricEntityTypeBuilder.<LandmineEntity>create(SpawnGroup.MISC,
                (entity, world) -> new LandmineEntity(world, new FlameSpell()))
                .dimensions(EntityDimensions.fixed(1F, 0.1F)).build());
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegistry() {
        ClientPlayNetworking.registerGlobalReceiver(AdvancedFireballEntitySpawnPacket.ID, AdvancedFireballEntitySpawnPacket::onPacket);
        EntityRendererRegistry.register(type, LandmineEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(new Identifier("eaw", "landmine"), "main"), AdvancedSnowballEntityModel::getTexturedModelData);
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return AdvancedFireballEntitySpawnPacket.createPacket(this);
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    public void tick() {
        super.tick();
        if(entityAge < 450) {
            entityAge++;
        } else {
            discard();
        }
        if (!this.world.isClient) {
            List<Entity> list2 = this.world.getNonSpectatingEntities(Entity.class, this.getBoundingBox());
            if (!list2.isEmpty()) {
                Iterator<Entity> var27 = list2.iterator();
                //this.discard();
                while (true) {
                    Entity livingEntity;
                    do {
                        if (!var27.hasNext()) {
                            return;
                        }
                        livingEntity = var27.next();
                        apply(livingEntity);
                    } while (!livingEntity.isInvulnerable());
                }
            }
        }
    }
    public void apply(Entity entity) {
        assert spell != null;
        switch (spell.getType()) {
            case FIRE -> { entity.setOnFireFor(20); }
            case WATER -> {
                entity.addVelocity(0, 1.5, 0);
                entity.velocityModified = true;
            }
        }
    }

}


