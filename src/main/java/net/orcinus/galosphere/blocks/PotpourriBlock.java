package net.orcinus.galosphere.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.blocks.blockentities.PotpourriBlockEntity;
import net.orcinus.galosphere.init.GBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

public class PotpourriBlock extends BaseEntityBlock {

    public PotpourriBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PotpourriBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : PotpourriBlock.createTickerHelper(blockEntityType, GBlockEntityTypes.POTPOURRI, PotpourriBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
