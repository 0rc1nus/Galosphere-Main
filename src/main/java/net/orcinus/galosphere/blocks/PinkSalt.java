package net.orcinus.galosphere.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.init.GBlockTags;
import net.orcinus.galosphere.init.GBlocks;

import java.util.Optional;
import java.util.function.Supplier;

public interface PinkSalt {

    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(GBlocks.PINK_SALT, GBlocks.ROSE_PINK_SALT)
            .put(GBlocks.ROSE_PINK_SALT, GBlocks.PASTEL_PINK_SALT)
            .put(GBlocks.POLISHED_PINK_SALT, GBlocks.POLISHED_ROSE_PINK_SALT)
            .put(GBlocks.POLISHED_ROSE_PINK_SALT, GBlocks.POLISHED_PASTEL_PINK_SALT)
            .put(GBlocks.PINK_SALT_BRICKS, GBlocks.ROSE_PINK_SALT_BRICKS)
            .put(GBlocks.ROSE_PINK_SALT_BRICKS, GBlocks.PASTEL_PINK_SALT_BRICKS)
            .put(GBlocks.PINK_SALT_SLAB, GBlocks.ROSE_PINK_SALT_SLAB)
            .put(GBlocks.ROSE_PINK_SALT_SLAB, GBlocks.PASTEL_PINK_SALT_SLAB)
            .put(GBlocks.POLISHED_PINK_SALT_SLAB, GBlocks.POLISHED_ROSE_PINK_SALT_SLAB)
            .put(GBlocks.POLISHED_ROSE_PINK_SALT_SLAB, GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB)
            .put(GBlocks.PINK_SALT_BRICK_SLAB, GBlocks.ROSE_PINK_SALT_BRICK_SLAB)
            .put(GBlocks.ROSE_PINK_SALT_BRICK_SLAB, GBlocks.PASTEL_PINK_SALT_BRICK_SLAB)
            .put(GBlocks.PINK_SALT_STAIRS, GBlocks.ROSE_PINK_SALT_STAIRS)
            .put(GBlocks.ROSE_PINK_SALT_STAIRS, GBlocks.PASTEL_PINK_SALT_STAIRS)
            .put(GBlocks.POLISHED_PINK_SALT_STAIRS, GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS)
            .put(GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS, GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS)
            .put(GBlocks.PINK_SALT_BRICK_STAIRS, GBlocks.ROSE_PINK_SALT_BRICK_STAIRS)
            .put(GBlocks.ROSE_PINK_SALT_BRICK_STAIRS, GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS)
            .put(GBlocks.PINK_SALT_WALL, GBlocks.ROSE_PINK_SALT_WALL)
            .put(GBlocks.ROSE_PINK_SALT_WALL, GBlocks.PASTEL_PINK_SALT_WALL)
            .put(GBlocks.POLISHED_PINK_SALT_WALL, GBlocks.POLISHED_ROSE_PINK_SALT_WALL)
            .put(GBlocks.POLISHED_ROSE_PINK_SALT_WALL, GBlocks.POLISHED_PASTEL_PINK_SALT_WALL)
            .put(GBlocks.PINK_SALT_BRICK_WALL, GBlocks.ROSE_PINK_SALT_BRICK_WALL)
            .put(GBlocks.ROSE_PINK_SALT_BRICK_WALL, GBlocks.PASTEL_PINK_SALT_BRICK_WALL)
            .build());

    static Optional<Block> getNext(Block block) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(block));
    }

    default void burningRandomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos) {
        if (serverLevel.getBlockState(blockPos.below()).is(GBlockTags.PINK_SALT_HEATED_BLOCKS)) {
            for (Block block : PinkSalt.NEXT_BY_BLOCK.get().keySet()) {
                if (!blockState.is(block)) {
                    continue;
                }
                serverLevel.setBlock(blockPos, PinkSalt.NEXT_BY_BLOCK.get().get(block).withPropertiesOf(blockState), 2);
            }
        }
    }

}
