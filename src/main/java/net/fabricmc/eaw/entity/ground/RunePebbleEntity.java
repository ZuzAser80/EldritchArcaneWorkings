package net.fabricmc.eaw.entity.ground;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.eaw.entity.fire.AdvancedFireballEntityModel;
import net.fabricmc.eaw.networking.AdvancedFireballEntitySpawnPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Random;

public class RunePebbleEntity extends PersistentProjectileEntity {

    private static EntityType<RunePebbleEntity> type;
    public int size;

    public static void registry() {
        type = Registry.register(Registry.ENTITY_TYPE, new Identifier("eaw", "rune_pebble"), FabricEntityTypeBuilder.<RunePebbleEntity>create(SpawnGroup.MISC,
                (entity, world) -> new RunePebbleEntity(world, 2))
                .dimensions(EntityDimensions.fixed(1F, 1F)).build());
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegistry() {
        ClientPlayNetworking.registerGlobalReceiver(AdvancedFireballEntitySpawnPacket.ID, AdvancedFireballEntitySpawnPacket::onPacket);
        EntityRendererRegistry.register(type, RunePebbleEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(new Identifier("eaw", "rune_pebble"), "main"), AdvancedFireballEntityModel::getTexturedModelData);
    }

    public RunePebbleEntity(World world, int size) {
        super(type, world);
        this.size = size;
    }

    @Override
    public void tick() {
        super.tick();
        if(!world.isClient) {
            ServerWorld serverWorld = (ServerWorld)world;
            Random rnd = new Random();
            serverWorld.spawnParticles(ParticleTypes.ASH, this.getX(), this.getY(), this.getZ(), 1, rnd.nextDouble(-0.1, 0.1), rnd.nextDouble(-0.1, 0.1), rnd.nextDouble(-0.1, 0.1), 0.1);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if(hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult)hitResult;
            Entity entity = entityHitResult.getEntity();
            entity.damage(DamageSource.GENERIC, size * 5);
        } else if(hitResult.getType() == HitResult.Type.BLOCK && !world.isClient) {
            BlockHitResult bHT = (BlockHitResult) hitResult;
            Block b = world.getBlockState(bHT.getBlockPos()).getBlock();
            if (b != Blocks.AIR && b != Blocks.CAVE_AIR) {
                this.discard();
            }
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }
}
