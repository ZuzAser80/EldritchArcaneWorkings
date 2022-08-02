package net.fabricmc.example.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RodConstructionTable extends CraftingTableBlock {

    public RodConstructionTable(Settings settings) {
        super(settings);
    }
    private static final Text SCREEN_TITLE = Text.translatable("container.magic_staff_crafting");

    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
                new RodConstructionScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), SCREEN_TITLE);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    public VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.0625, 0.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.0625, 0.3125, 0.6875, 0.125, 0.6875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.125, 0.375, 0.625, 0.625, 0.625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.625, 0.125, 0.875, 0.6875, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.6875, 0.0625, 0.9375, 0.75, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.75, 0.25, 0.75, 0.8125, 0.75));

        return shape;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            return ActionResult.CONSUME;
        }
    }
}
