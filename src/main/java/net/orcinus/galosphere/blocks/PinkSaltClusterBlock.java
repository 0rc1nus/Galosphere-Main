package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PinkSaltClusterBlock extends PinkSaltLampBlock {
    protected final VoxelShape northAabb;
    protected final VoxelShape southAabb;
    protected final VoxelShape eastAabb;
    protected final VoxelShape westAabb;
    protected final VoxelShape upAabb;
    protected final VoxelShape downAabb;

    public PinkSaltClusterBlock(Properties properties) {
        super(properties);
        this.upAabb = Block.box(3, 0.0, 3, 13, 13, 13);
        this.downAabb = Block.box(3, 3, 3, 13, 16.0, 13);
        this.northAabb = Block.box(3, 3, 3, 13, 13, 16.0);
        this.southAabb = Block.box(3, 3, 0.0, 13, 13, 13);
        this.eastAabb = Block.box(0.0, 3, 3, 13, 13, 13);
        this.westAabb = Block.box(3, 3, 3, 16.0, 13, 13);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);
        switch (direction) {
            case NORTH -> {
                return this.northAabb;
            }
            case SOUTH -> {
                return this.southAabb;
            }
            case EAST -> {
                return this.eastAabb;
            }
            case WEST -> {
                return this.westAabb;
            }
            case DOWN -> {
                return this.downAabb;
            }
        }
        return this.upAabb;
    }

}