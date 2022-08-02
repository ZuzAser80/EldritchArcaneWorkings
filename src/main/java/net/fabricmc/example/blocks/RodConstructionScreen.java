package net.fabricmc.example.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RodConstructionScreen extends ForgingScreen<RodConstructionScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("eaw", "textures/gui/rod_construction_table.png");

    public RodConstructionScreen(RodConstructionScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title, TEXTURE);
        this.titleX = 54;
        this.titleY = 4;
    }

    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.drawForeground(matrices, mouseX, mouseY);
    }
}
