package net.fabricmc.example.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Formatter;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class TooltipDisplayMixin {

    ItemStack stack = ((ItemStack)(Object) this);

    @Inject(at = @At("TAIL"), method = "getTooltip", cancellable = true)
    public void displayRuneTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        List<Text> list = cir.getReturnValue();
        if(stack.getSubNbt("runes") != null) {
            for(int k = 0; k <= 4; k++) {
                if(stack.getSubNbt("runes").get("rune_" + k) != null) {
                    Formatting formatting;
                    switch (((NbtCompound) stack.getSubNbt("runes").get("rune_" + k)).getString("type")) {
                        case "Fire" -> formatting = Formatting.RED;
                        case "Water" -> formatting = Formatting.AQUA;
                        case "Ground" -> formatting = Formatting.GREEN;
                        case "Air" -> formatting = Formatting.WHITE;
                        default -> formatting = Formatting.BOLD;
                    }
                    list.add(Text.translatable("Rune: " + ((NbtCompound) stack.getSubNbt("runes").get("rune_" + k)).getString("name")).formatted(formatting));
                }
            }
            cir.setReturnValue(list);
        }
    }
}