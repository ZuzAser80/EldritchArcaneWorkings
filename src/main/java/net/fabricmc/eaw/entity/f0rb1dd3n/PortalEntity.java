package net.fabricmc.eaw.entity.f0rb1dd3n;

import net.fabricmc.eaw.networking.AdvancedFireballEntitySpawnPacket;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PortalEntity extends Entity {

    private TrackedData<BlockPos> pos;
    private BlockPos position;

    public PortalEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(pos, new BlockPos(0, 0, 0));
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        position = new BlockPos(nbt.getFloat("posX"), nbt.getFloat("posY"), nbt.getFloat("posZ"));
    }

    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.IGNORE;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("posX", position.getX());
        nbt.putFloat("posY", position.getY());
        nbt.putFloat("posZ", position.getZ());
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return AdvancedFireballEntitySpawnPacket.createPacket(this);
    }
}
