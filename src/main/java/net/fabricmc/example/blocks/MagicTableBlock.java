package net.fabricmc.example.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MagicTableBlock extends BlockWithEntity {

    public MagicTableBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MagicTableBlockEntity) {
                player.openHandledScreen((MagicTableBlockEntity) blockEntity);
            }

            return ActionResult.CONSUME;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.75, 0.0625, 0.9375, 0.8125, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.6875, 0, 1, 0.75, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.625, 0.125, 0.875, 0.6875, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.0625, 0.25, 0.75, 0.625, 0.75));

        return shape;
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MagicTableBlockEntity(pos, state);
    }
}
