package net.fabricmc.eaw.mixin.rune_support;

import net.fabricmc.eaw.entity.ground.RunePebbleEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ItemStack.class)
public class ItemInject {

    ItemStack stack = ((ItemStack)(Object) this);

    @Inject(at = @At("HEAD"), method = "inventoryTick")
    public void applyRiptide(World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity user = (PlayerEntity) entity;
        for(int i = 0; i <= 4; i++) {
            NbtCompound nbt = (NbtCompound)stack.getOrCreateSubNbt("runes").get("rune_" + i);
            if(mc.mouse.wasRightButtonClicked() && selected && stack.isIn(TagKey.of(Registry.ITEM_KEY, new Identifier("eaw", "runeable"))) && nbt != null && !world.isClient) {
                if (nbt.getString("name").contains("Pocket Riptide") && user.isOnGround()) {
                    float f = user.getYaw();
                    float g = user.getPitch();
                    float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                    float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                    user.addVelocity((double) h * 0.75 * nbt.getInt("strength"), 0.75 * nbt.getInt("strength"), (double) l * 0.75 * nbt.getInt("strength"));
                    user.velocityModified = true;
                }
                if(nbt.getString("name").contains("Rocky Storm")) {
                    int runeLevel = nbt.getInt("strength");
                    for(int k = 0; k < runeLevel * 2; k++) {
                        Random r = new Random();
                        RunePebbleEntity runePebbleEntity = new RunePebbleEntity(world, r.nextInt(4));
                        runePebbleEntity.setPosition(user.getX(), user.getY() + 0.5, user.getZ());
                        runePebbleEntity.setVelocity(user, user.getPitch() + r.nextInt(0, 10), user.getYaw() + r.nextInt(0, 10), 0, 1, 1);
                        runePebbleEntity.setNoGravity(true);
                        world.spawnEntity(runePebbleEntity);
                    }
                }
            }
        }
    }
}
