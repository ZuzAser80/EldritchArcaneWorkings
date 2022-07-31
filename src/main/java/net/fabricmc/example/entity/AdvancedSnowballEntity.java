package net.fabricmc.example.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.networking.AdvancedFireballEntitySpawnPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Iterator;
import java.util.Random;

public class AdvancedSnowballEntity extends PersistentProjectileEntity {
    private static EntityType<AdvancedSnowballEntity> type;

    public AdvancedSnowballEntity(World world) {
        super(type, world);
    }

    public static void registry() {
        type = Registry.register(Registry.ENTITY_TYPE, new Identifier("eaw", "advanced_snowball"), FabricEntityTypeBuilder.<AdvancedSnowballEntity>create(SpawnGroup.MISC,
                (entity, world) -> new AdvancedSnowballEntity(world))
                .dimensions(EntityDimensions.fixed(1F, 1F)).build());
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegistry() {
        ClientPlayNetworking.registerGlobalReceiver(AdvancedFireballEntitySpawnPacket.ID, AdvancedFireballEntitySpawnPacket::onPacket);
        EntityRendererRegistry.register(type, AdvancedSnowballEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(new Identifier("eaw", "advanced_snowball"), "main"), AdvancedSnowballEntityModel::getTexturedModelData);
    }

    @Override
    public void tick() {
        super.tick();
        if(!world.isClient) {
            ServerWorld serverWorld = (ServerWorld)world;
            Random rnd = new Random();
            serverWorld.spawnParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 1, rnd.nextDouble(-0.1, 0.1), rnd.nextDouble(-0.1, 0.1), rnd.nextDouble(-0.1, 0.1), 0.1);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if(!world.isClient) {
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult bHT = (BlockHitResult) hitResult;
                Block b = world.getBlockState(bHT.getBlockPos()).getBlock();
                if (b != Blocks.AIR && b != Blocks.CAVE_AIR) {
                    setSnow(world, new BlockPos(this.getX(), this.getY(), this.getZ()));
                    this.discard();
                }
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                setSnow(world, new BlockPos(this.getX(), this.getY(), this.getZ()));
                this.discard();
            }
        }
    }

    protected void setSnow(World world, BlockPos pos) {
        for (BlockPos blockPos2 : BlockPos.iterate(pos.north(4).east(4).up(4), pos.south(4).west(4).down(4))) {
            if ((world.getBlockState(blockPos2).isOf(Blocks.AIR) || world.getBlockState(blockPos2).isOf(Blocks.CAVE_AIR) || world.getBlockState(blockPos2).isOf(Blocks.VOID_AIR)) && blockPos2.isWithinDistance(pos, 4) && world.getBlockState(pos).isOf(Blocks.FIRE) && world.getBlockState(pos).isOf(Blocks.SOUL_FIRE)) {
                world.setBlockState(blockPos2, Blocks.POWDER_SNOW.getDefaultState());
            } else if(blockPos2.isWithinDistance(pos, 4) && world.getBlockState(pos).isOf(Blocks.WATER)) {
                world.setBlockState(blockPos2, Blocks.ICE.getDefaultState());
            }
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }
}
