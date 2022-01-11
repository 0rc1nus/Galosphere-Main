package net.orcinus.cavesandtrenches.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LumiereLampBlock extends Block {
    private static final IntegerProperty POWER = BlockStateProperties.POWER;

    public LumiereLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(POWER);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos blockPos, boolean moving) {
        if (!world.isClientSide) {
            int i = world.getBestNeighborSignal(pos);
            if (world.hasNeighborSignal(pos) && i > 0) {
                world.setBlock(pos, state.setValue(POWER, i), 2);
            } else {
                world.scheduleTick(pos, this, 2);
            }

        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(POWER) > 0 && !world.hasNeighborSignal(pos)) {
            world.setBlock(pos, state.setValue(POWER, 0), 2);
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        int i = world.getBestNeighborSignal(pos);
        if (world.hasNeighborSignal(pos) && i > 0) {
            world.setBlock(pos, state.setValue(POWER, i), 2);
        }
        world.scheduleTick(pos, this, 2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWER);
    }
}
