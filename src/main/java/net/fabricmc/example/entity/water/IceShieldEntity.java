package net.fabricmc.example.entity.water;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.entity.fire.AdvancedFireballEntityModel;
import net.fabricmc.example.networking.AdvancedFireballEntitySpawnPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class IceShieldEntity extends Entity {

    private static EntityType<IceShieldEntity> type;

    public static void registry() {
        type = Registry.register(Registry.ENTITY_TYPE, new Identifier("eaw", "ice_shield"), FabricEntityTypeBuilder.<IceShieldEntity>create(SpawnGroup.MISC,
                (entity, world) -> new IceShieldEntity(world))
                .dimensions(EntityDimensions.fixed(1F, 1F)).build());
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegistry() {
        ClientPlayNetworking.registerGlobalReceiver(AdvancedFireballEntitySpawnPacket.ID, AdvancedFireballEntitySpawnPacket::onPacket);
        EntityRendererRegistry.register(type, IceShieldEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(new EntityModelLayer(new Identifier("eaw", "ice_shield"), "main"), IceShieldEntityModel::getTexturedModelData);
    }

    public IceShieldEntity(World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
