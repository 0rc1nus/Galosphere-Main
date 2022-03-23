package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class MimicLightBlock extends Block {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public MimicLightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos blockPos, Random random) {
        int level = state.getValue(LEVEL);
        if (level > 0) {
            world.setBlock(blockPos, state.setValue(LEVEL, level - 1), 3);
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos blockPos, BlockState p_60569_, boolean p_60570_) {
        world.scheduleTick(new BlockPos(blockPos), state.getBlock(), 20);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
