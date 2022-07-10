package net.orcinus.galosphere.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.blocks.AuraRingerBlock;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;
import net.orcinus.galosphere.init.GBlocks;

public class WarpedAnchorDispenseItemBehavior extends OptionalDispenseItemBehavior {

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        BlockPos blockpos = source.getPos().above();
        Level world = source.getLevel();
        BlockState state = world.getBlockState(blockpos);
        WarpedAnchorBlock block = (WarpedAnchorBlock) GBlocks.WARPED_ANCHOR;
        this.setSuccess(true);
        if (state.getBlock() == block) {
            if (state.getValue(WarpedAnchorBlock.WARPED_CHARGE) < 4) {
                block.incrementCharge(state, world, blockpos, state.getValue(WarpedAnchorBlock.WARPED_CHARGE));
                stack.shrink(1);
            } else {
                this.setSuccess(false);
            }

            return stack;
        } else {
            return super.execute(source, stack);
        }
    }
}
