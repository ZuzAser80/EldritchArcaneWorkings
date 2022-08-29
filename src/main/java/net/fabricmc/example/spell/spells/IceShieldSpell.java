package net.fabricmc.example.spell.spells;

import net.fabricmc.example.spell.Spell;
import net.fabricmc.example.spell.SpellRank;
import net.fabricmc.example.spell.SpellType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class IceShieldSpell extends Spell {

    public IceShieldSpell() {
        super(SpellType.WATER, SpellRank.average, 20, 10, "Ice Shield");
    }

    @Override
    public void cast(PlayerEntity user, World world) {
        if(!world.isClient) {
            BlockPos pos = user.getBlockPos();
            for (BlockPos blockPos2 : BlockPos.iterate(pos.north(10).east(10).up(10), pos.south(10).west(10).down(10))) {
                Random rnd = new Random();
                int random = rnd.nextInt(0, 3);
                if(blockPos2.isWithinDistance(pos, 3)) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    continue;
                }
                if(blockPos2.isWithinDistance(pos, 4)) {
                    if(world.getBlockState(blockPos2).isOf(Blocks.WATER)) {
                        world.setBlockState(blockPos2, Blocks.BLUE_ICE.getDefaultState());
                    }
                    if ((world.getBlockState(blockPos2).isOf(Blocks.AIR) || world.getBlockState(blockPos2).isOf(Blocks.CAVE_AIR) || world.getBlockState(blockPos2).isOf(Blocks.VOID_AIR) || world.getBlockState(pos).isOf(Blocks.FIRE) || world.getBlockState(pos).isOf(Blocks.SOUL_FIRE))) {
                        if(random <= 1) {
                            world.setBlockState(blockPos2, Blocks.ICE.getDefaultState());
                        } else {
                            world.setBlockState(blockPos2, Blocks.BLUE_ICE.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
