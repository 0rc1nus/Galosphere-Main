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
            .put(GBlocks.PINK_SALT.get(), GBlocks.ROSE_PINK_SALT.get())
            .put(GBlocks.ROSE_PINK_SALT.get(), GBlocks.PASTEL_PINK_SALT.get())
            .put(GBlocks.POLISHED_PINK_SALT.get(), GBlocks.POLISHED_ROSE_PINK_SALT.get())
            .put(GBlocks.POLISHED_ROSE_PINK_SALT.get(), GBlocks.POLISHED_PASTEL_PINK_SALT.get())
            .put(GBlocks.PINK_SALT_BRICKS.get(), GBlocks.ROSE_PINK_SALT_BRICKS.get())
            .put(GBlocks.ROSE_PINK_SALT_BRICKS.get(), GBlocks.PASTEL_PINK_SALT_BRICKS.get())
            .put(GBlocks.PINK_SALT_SLAB.get(), GBlocks.ROSE_PINK_SALT_SLAB.get())
            .put(GBlocks.ROSE_PINK_SALT_SLAB.get(), GBlocks.PASTEL_PINK_SALT_SLAB.get())
            .put(GBlocks.POLISHED_PINK_SALT_SLAB.get(), GBlocks.POLISHED_ROSE_PINK_SALT_SLAB.get())
            .put(GBlocks.POLISHED_ROSE_PINK_SALT_SLAB.get(), GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB.get())
            .put(GBlocks.PINK_SALT_BRICK_SLAB.get(), GBlocks.ROSE_PINK_SALT_BRICK_SLAB.get())
            .put(GBlocks.ROSE_PINK_SALT_BRICK_SLAB.get(), GBlocks.PASTEL_PINK_SALT_BRICK_SLAB.get())
            .put(GBlocks.PINK_SALT_STAIRS.get(), GBlocks.ROSE_PINK_SALT_STAIRS.get())
            .put(GBlocks.ROSE_PINK_SALT_STAIRS.get(), GBlocks.PASTEL_PINK_SALT_STAIRS.get())
            .put(GBlocks.POLISHED_PINK_SALT_STAIRS.get(), GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS.get())
            .put(GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS.get(), GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS.get())
            .put(GBlocks.PINK_SALT_BRICK_STAIRS.get(), GBlocks.ROSE_PINK_SALT_BRICK_STAIRS.get())
            .put(GBlocks.ROSE_PINK_SALT_BRICK_STAIRS.get(), GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS.get())
            .put(GBlocks.PINK_SALT_WALL.get(), GBlocks.ROSE_PINK_SALT_WALL.get())
            .put(GBlocks.ROSE_PINK_SALT_WALL.get(), GBlocks.PASTEL_PINK_SALT_WALL.get())
            .put(GBlocks.POLISHED_PINK_SALT_WALL.get(), GBlocks.POLISHED_ROSE_PINK_SALT_WALL.get())
            .put(GBlocks.POLISHED_ROSE_PINK_SALT_WALL.get(), GBlocks.POLISHED_PASTEL_PINK_SALT_WALL.get())
            .put(GBlocks.PINK_SALT_BRICK_WALL.get(), GBlocks.ROSE_PINK_SALT_BRICK_WALL.get())
            .put(GBlocks.ROSE_PINK_SALT_BRICK_WALL.get(), GBlocks.PASTEL_PINK_SALT_BRICK_WALL.get())
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