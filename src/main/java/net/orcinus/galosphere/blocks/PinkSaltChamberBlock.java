package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.orcinus.galosphere.blocks.blockentities.PinkSaltChamberBlockEntity;
import net.orcinus.galosphere.blocks.blockentities.PotpourriBlockEntity;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

public class PinkSaltChamberBlock extends BaseEntityBlock {
    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");

    public PinkSaltChamberBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CHARGED, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PinkSaltChamberBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : PinkSaltChamberBlock.createTickerHelper(blockEntityType, GBlockEntityTypes.PINK_SALT_CHAMBER, PinkSaltChamberBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
