package net.fabricmc.eaw.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.eaw.item.AbstractMagicRodItem;
import net.fabricmc.eaw.spell.Spell;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class GameHudMixin extends DrawableHelper {
    @Shadow @Final private MinecraftClient client;
    @Shadow private int scaledWidth;

    @Shadow public abstract TextRenderer getTextRenderer();

    @Shadow private int scaledHeight;

    @Inject(at = @At("HEAD"), method = "render")
    public void renderInject(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        MinecraftClient mc = this.client;
        if(mc.player != null && mc.player.getMainHandStack().getItem() instanceof AbstractMagicRodItem) {
            RenderSystem.setShaderTexture(0, new Identifier("eaw","textures/gui/mana_icon.png"));

            int l = 30;
            drawTexture(matrices, this.scaledWidth - 50, l, 16, 0, 16, 122, 32, 122);

            if(mc.player.getMainHandStack().getOrCreateNbt().getFloat("manaCount") > 0) {
                float multiplier = 122.0f / mc.player.getMainHandStack().getOrCreateNbt().getFloat("maxManaCap");

                float k = mc.player.getMainHandStack().getOrCreateNbt().getFloat("manaCount") * multiplier;
                if (k > 0) {
                    drawTexture(matrices, this.scaledWidth - 50, l, 0, 0, 16, 122 - (int)k, 32, 122);
                }
            }
            if(mc.player.getMainHandStack().getNbt() != null) {
                ItemStack mainHand = mc.player.getMainHandStack();
                Spell fromNbtSpell = Spell.fromNbt((NbtCompound)mainHand.getOrCreateSubNbt("spells").get("currentSpell"));
                int spellIndex = mainHand.getOrCreateNbt().getInt("currentSpellIndex");
                if(fromNbtSpell != null) {
                    getTextRenderer().draw(matrices, fromNbtSpell.getName() + " " + spellIndex, 10, scaledHeight - 10, new Color(0, 255, 157).getRGB());
                } else {
                    getTextRenderer().draw(matrices, "None " + spellIndex, 10, scaledHeight - 10, new Color(0, 255, 157).getRGB());
                }
            }
        }
    }
}
