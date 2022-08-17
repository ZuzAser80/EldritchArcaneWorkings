package net.fabricmc.example.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.item.AbstractMagicRodItem;
import net.fabricmc.example.spell.Spell;
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
import org.w3c.dom.css.RGBColor;

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
                if(fromNbtSpell != null && !fromNbtSpell.getName().equals("None")) {
                    getTextRenderer().draw(matrices, fromNbtSpell.getName(), 10, scaledHeight - 10, new Color(0, 255, 157).getRGB());
                } else if(fromNbtSpell.getName().equals("None")) {
                    getTextRenderer().draw(matrices, "None", 10, scaledHeight - 10, new Color(255, 0, 0).getRGB());
                } else {
                    getTextRenderer().draw(matrices, "Null / Void", 10, scaledHeight - 10, new Color(187, 0, 255).getRGB());
                }
            }
        }
    }
}
