package net.fabricmc.example.mixin;

import net.fabricmc.example.rune.RuneItem;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgingScreenHandler.class)
public class ForgingScreenHandlerMixin {

    @Shadow @Final protected CraftingResultInventory output;
    @Shadow @Final protected Inventory input;

    @Inject(at = @At("TAIL"), method = "onContentChanged")
    public void modifyNbt(CallbackInfo ci) {
        if(this.input.containsAny(stack -> stack.getItem() instanceof RuneItem)) {
            ItemStack stack = this.input.getStack(1);
            if(stack.getItem() instanceof RuneItem) {
                for(int i = 0; i <= 4; i++) {
                    ItemStack outputStack = this.output.getStack(0);
                    if(outputStack.getOrCreateSubNbt("runes").get("rune_" + i) == null) {
                        outputStack.getOrCreateSubNbt("runes").put("rune_" + i, stack.getOrCreateNbt().get("rune"));
                        break;
                    } else if (outputStack.getOrCreateSubNbt("runes").get("rune_4") != null) {
                        outputStack = ItemStack.EMPTY;
                        break;
                    }
                }
            }
        }
    }
}
