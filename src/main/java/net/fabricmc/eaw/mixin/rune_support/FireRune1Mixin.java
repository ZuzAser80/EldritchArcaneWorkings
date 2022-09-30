package net.fabricmc.eaw.mixin.rune_support;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class FireRune1Mixin {

    PlayerEntity livingEntity = ((PlayerEntity)(Object) this);

    @Inject(at = @At("HEAD"), method = "attack")
    public void applyFlame(Entity target, CallbackInfo ci) {
        for(int i = 0; i <= 4; i++) {
            NbtCompound nbt = ((NbtCompound) livingEntity.getMainHandStack().getOrCreateSubNbt("runes").get("rune_" + i));
            if(nbt != null && !target.world.isClient) {
                int seconds = nbt.getInt("strength");
                if (nbt.getString("name").contains("Fiery Touch")) {
                    target.setOnFireFor(6 * seconds);
                    break;
                }
                if(nbt.getString("name").contains("Light Punch")) {
                    if(target instanceof LivingEntity livingEntity) {
                        livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.LEVITATION, 30 * seconds, 1)));
                    }
                }
            }
        }
    }
}
