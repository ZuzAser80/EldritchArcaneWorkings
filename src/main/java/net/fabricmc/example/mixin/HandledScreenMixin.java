package net.fabricmc.example.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {

    @Shadow @Nullable private Slot lastClickedSlot;

    @Inject(at = @At("HEAD"), method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V")
    public void alert(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if(actionType.equals(SlotActionType.THROW)) {
            System.out.println("state: " + slot + " : " + slotId + " : " + button + " : " + lastClickedSlot);
        }
    }
}
