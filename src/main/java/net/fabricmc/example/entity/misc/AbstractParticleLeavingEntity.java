package net.fabricmc.example.entity.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.entity.fire.AdvancedFireballEntityModel;
import net.fabricmc.example.networking.AdvancedFireballEntitySpawnPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Random;

public class AbstractParticleLeavingEntity extends PersistentProjectileEntity {

    private static EntityType<AbstractParticleLeavingEntity> type;
    DefaultParticleType particle;
    boolean setOnFire;

    public static void registry() {
        type = Registry.register(Registry.ENTITY_TYPE, new Identifier("eaw", "abstract_particle_leaving"), FabricEntityTypeBuilder.<AbstractParticleLeavingEntity>create(SpawnGroup.MISC,
                (entity, world) -> new AbstractParticleLeavingEntity(world, ParticleTypes.FLAME, true))
                .dimensions(EntityDimensions.fixed(0.1F, 0.1F)).build());
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegistry() {
        ClientPlayNetworking.registerGlobalReceiver(AdvancedFireballEntitySpawnPacket.ID, AdvancedFireballEntitySpawnPacket::onPacket);
        EntityRendererRegistry.register(type, AbstractParticleLeavingEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(new Identifier("eaw", "abstract_particle_leaving_entity"), "main"), AdvancedFireballEntityModel::getTexturedModelData);
    }

    public AbstractParticleLeavingEntity(World world, DefaultParticleType particle, boolean fire) {
        super(type, world);
        this.particle = particle;
        setOnFire = fire;
    }

    @Override
    public void tick() {
        super.tick();
        if(!world.isClient) {
            ServerWorld serverWorld = (ServerWorld)world;
            Random rnd = new Random();
            serverWorld.spawnParticles(particle, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0.01);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if(!world.isClient) {
            if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entity = (EntityHitResult) hitResult;
                Entity entity1 = entity.getEntity();
                if(setOnFire) {
                    entity1.setOnFireFor(10);
                }
                this.discard();
            } else if (hitResult.getType() == HitResult.Type.BLOCK && (world.getBlockState(((BlockHitResult)hitResult).getBlockPos().up()).isOf(Blocks.AIR) || world.getBlockState(((BlockHitResult)hitResult).getBlockPos().up()).isOf(Blocks.CAVE_AIR))) {
                BlockPos pos = ((BlockHitResult)hitResult).getBlockPos().up();
                if(setOnFire) {
                    world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                }
                this.discard();
            }
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }
}
