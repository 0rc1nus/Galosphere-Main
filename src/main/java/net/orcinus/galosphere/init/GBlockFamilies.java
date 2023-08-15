package net.orcinus.galosphere.init;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.stream.Stream;

public class GBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily PINK_SALT = familyBuilder(GBlocks.PINK_SALT.get())
            .slab(GBlocks.PINK_SALT_SLAB.get())
            .stairs(GBlocks.PINK_SALT_STAIRS.get())
            .wall(GBlocks.PINK_SALT_WALL.get())
            .polished(GBlocks.POLISHED_PINK_SALT.get())
            .chiseled(GBlocks.CHISELED_PINK_SALT.get())
            .getFamily();
    public static final BlockFamily ROSE_PINK_SALT = familyBuilder(GBlocks.ROSE_PINK_SALT.get())
            .slab(GBlocks.ROSE_PINK_SALT_SLAB.get())
            .stairs(GBlocks.ROSE_PINK_SALT_STAIRS.get())
            .wall(GBlocks.ROSE_PINK_SALT_WALL.get())
            .polished(GBlocks.POLISHED_ROSE_PINK_SALT.get())
            .chiseled(GBlocks.CHISELED_ROSE_PINK_SALT.get())
            .getFamily();
    public static final BlockFamily PASTEL_PINK_SALT = familyBuilder(GBlocks.PASTEL_PINK_SALT.get())
            .slab(GBlocks.PASTEL_PINK_SALT_SLAB.get())
            .stairs(GBlocks.PASTEL_PINK_SALT_STAIRS.get())
            .wall(GBlocks.PASTEL_PINK_SALT_WALL.get())
            .polished(GBlocks.POLISHED_PASTEL_PINK_SALT.get())
            .chiseled(GBlocks.CHISELED_PASTEL_PINK_SALT.get())
            .getFamily();
    public static final BlockFamily POLISHED_PINK_SALT = familyBuilder(GBlocks.POLISHED_PINK_SALT.get())
            .slab(GBlocks.POLISHED_PINK_SALT_SLAB.get())
            .stairs(GBlocks.POLISHED_PINK_SALT_STAIRS.get())
            .wall(GBlocks.POLISHED_PINK_SALT_WALL.get())
            .getFamily();
    public static final BlockFamily POLISHED_ROSE_PINK_SALT = familyBuilder(GBlocks.POLISHED_ROSE_PINK_SALT.get())
            .slab(GBlocks.POLISHED_ROSE_PINK_SALT_SLAB.get())
            .stairs(GBlocks.POLISHED_ROSE_PINK_SALT_STAIRS.get())
            .wall(GBlocks.POLISHED_ROSE_PINK_SALT_WALL.get())
            .getFamily();
    public static final BlockFamily POLISHED_PASTEL_PINK_SALT = familyBuilder(GBlocks.POLISHED_PASTEL_PINK_SALT.get())
            .slab(GBlocks.POLISHED_PASTEL_PINK_SALT_SLAB.get())
            .stairs(GBlocks.POLISHED_PASTEL_PINK_SALT_STAIRS.get())
            .wall(GBlocks.POLISHED_PASTEL_PINK_SALT_WALL.get())
            .getFamily();
    public static final BlockFamily PINK_SALT_BRICKS = familyBuilder(GBlocks.PINK_SALT_BRICKS.get())
            .slab(GBlocks.PINK_SALT_BRICK_SLAB.get())
            .stairs(GBlocks.PINK_SALT_BRICK_STAIRS.get())
            .wall(GBlocks.PINK_SALT_BRICK_WALL.get())
            .getFamily();
    public static final BlockFamily ROSE_PINK_SALT_BRICKS = familyBuilder(GBlocks.ROSE_PINK_SALT_BRICKS.get())
            .slab(GBlocks.ROSE_PINK_SALT_BRICK_SLAB.get())
            .stairs(GBlocks.ROSE_PINK_SALT_BRICK_STAIRS.get())
            .wall(GBlocks.ROSE_PINK_SALT_BRICK_WALL.get())
            .getFamily();
    public static final BlockFamily PASTEL_PINK_SALT_BRICKS = familyBuilder(GBlocks.PASTEL_PINK_SALT_BRICKS.get())
            .slab(GBlocks.PASTEL_PINK_SALT_BRICK_SLAB.get())
            .stairs(GBlocks.PASTEL_PINK_SALT_BRICK_STAIRS.get())
            .wall(GBlocks.PASTEL_PINK_SALT_BRICK_WALL.get())
            .getFamily();

    private static BlockFamily.Builder familyBuilder(Block block) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(block);
        BlockFamily blockfamily = MAP.put(block, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(block));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }

}
