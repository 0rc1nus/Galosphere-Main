package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CuredMembraneBlock extends Block {
    public static final BooleanProperty KINETIC = BooleanProperty.create("kinetic");

    public CuredMembraneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(KINETIC, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(KINETIC);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(KINETIC)) {
            serverLevel.setBlock(blockPos, blockState.setValue(KINETIC, false), 2);
        }
    }

    public void setKinetic(BlockState state, BlockPos blockPos, Level level) {
        level.setBlockAndUpdate(blockPos, state.setValue(KINETIC, true));
        level.scheduleTick(blockPos, this, 20);
    }

}
