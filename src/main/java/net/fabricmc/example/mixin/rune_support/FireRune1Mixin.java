package net.fabricmc.example.mixin.rune_support;

import net.minecraft.data.server.RecipeProvider;
import net.minecraft.datafixer.fix.FurnaceRecipesFix;
import net.minecraft.datafixer.fix.RecipeRenamingFix;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
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
            NbtCompound nbt = ((NbtCompound)livingEntity.getMainHandStack().getOrCreateSubNbt("runes").get("rune_" + i));
            if (nbt != null && nbt.getString("name").contains("Fiery Touch")) {
                int seconds = nbt.getInt("strength");
                target.setOnFireFor(6 * seconds);
                //System.out.println("Smelting:" + RecipeProvider.getSmeltingItemPath(Items.PUFFERFISH));
                break;
            }
        }
    }
}
