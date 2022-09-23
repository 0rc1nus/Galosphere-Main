package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChandelierBlock extends LanternBlock {
    protected static final VoxelShape SHAPE = box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);
    protected static final VoxelShape HANGING_SHAPE = box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public ChandelierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return blockState.getValue(HANGING) ? HANGING_SHAPE : SHAPE;
    }

}
