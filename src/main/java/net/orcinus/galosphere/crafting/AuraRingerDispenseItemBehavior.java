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
import net.orcinus.galosphere.init.GBlocks;

public class AuraRingerDispenseItemBehavior extends OptionalDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        BlockPos blockpos = source.getPos().relative(direction);
        Level world = source.getLevel();
        BlockState state = world.getBlockState(blockpos);
        AuraRingerBlock block = (AuraRingerBlock) GBlocks.AURA_RINGER.get();
        this.setSuccess(true);
        if (state.getBlock() == block) {
            if (!state.getValue(AuraRingerBlock.RINGING)) {
                block.activate(state, world, blockpos);
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
