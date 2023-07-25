package net.orcinus.galosphere.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.init.GBlockEntityTypes;

public class GildedBeadsBlockEntity extends BlockEntity {

    public GildedBeadsBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(GBlockEntityTypes.GILDED_BEADS, blockPos, blockState);
    }

}
