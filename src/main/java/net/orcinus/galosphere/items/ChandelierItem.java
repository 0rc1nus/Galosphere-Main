package net.orcinus.galosphere.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.blocks.ChandelierBlock;

public class ChandelierItem extends BlockItem {

    public ChandelierItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext blockPlaceContext, BlockState blockState) {
        BlockPos blockPos;
        Level level = blockPlaceContext.getLevel();
        BlockState blockState2 = level.isWaterAt(blockPos = blockPlaceContext.getClickedPos().relative(blockState.getValue(ChandelierBlock.VERTICAL_DIRECTION))) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
        level.setBlock(blockPos, blockState2, 27);
        return super.placeBlock(blockPlaceContext, blockState);
    }
}
