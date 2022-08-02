package net.fabricmc.example.spell.spells;

import net.fabricmc.example.entity.water.IceShieldEntity;
import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class IceShieldSpell extends Spell {

    public IceShieldSpell() {
        super(SpellType.WATER, SpellRank.AVERAGE, 20, 10);
    }

    @Override
    public void cast(PlayerEntity user, World world) {
        float f = user.getYaw();
        float g = user.getPitch();
        float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
        float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
        BlockPos blockPos = new BlockPos((double)h, 1.5, (double)l);
        world.setBlockState(blockPos, Blocks.ICE.getDefaultState());
        world.setBlockState(blockPos.up(), Blocks.ICE.getDefaultState());

    }
}
