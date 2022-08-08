package net.fabricmc.example.mixin;

import net.fabricmc.example.item.AbstractMagicRodItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

    @Inject(at = @At("HEAD"), method = "updateResult", cancellable = true)
    private static void commence(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory, CallbackInfo ci) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional = Objects.requireNonNull(world.getServer()).getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
            if (optional.isPresent()) {
                CraftingRecipe craftingRecipe = optional.get();
                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
                    itemStack = craftingRecipe.craft(craftingInventory);
                }
            }

            if(itemStack.getItem() instanceof AbstractMagicRodItem) {
                String crystalName = "";
                String rodName = "";
                for(int i = 0; i <= 9; i++) {
                    if(craftingInventory.getStack(i).isIn(TagKey.of(Registry.ITEM_KEY, new Identifier("eaw", "crystals")))) {
                        crystalName = craftingInventory.getStack(i).getTranslationKey().replaceFirst("item.eaw.", "");
                        crystalName = crystalName.replaceFirst("_crystal", "");
                    } else if(craftingInventory.getStack(i).isIn(TagKey.of(Registry.ITEM_KEY, new Identifier("eaw", "rods")))) {
                        rodName = craftingInventory.getStack(i).getTranslationKey().replaceFirst("item.eaw.", "");
                    }
                }
                itemStack.getOrCreateNbt().putString("crystal", crystalName);
                itemStack.getOrCreateNbt().putString("rod", rodName);
                resultInventory.setStack(0, itemStack);
                handler.setPreviousTrackedSlot(0, itemStack);
                serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
                ci.cancel();
            }
        }
    }
}
