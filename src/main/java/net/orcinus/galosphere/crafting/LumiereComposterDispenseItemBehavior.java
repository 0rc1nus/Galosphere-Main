package net.orcinus.galosphere.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.init.CTBlocks;

public class LumiereComposterDispenseItemBehavior extends OptionalDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        BlockPos blockpos = source.getPos().relative(direction);
        Level world = source.getLevel();
        BlockState state = world.getBlockState(blockpos);
        this.setSuccess(true);
        if (state.is(Blocks.COMPOSTER)) {
            if (state.getValue(ComposterBlock.LEVEL) > 0) {
                world.playSound(null, blockpos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.setBlock(blockpos, CTBlocks.LUMIERE_COMPOSTER.get().defaultBlockState().setValue(ComposterBlock.LEVEL, state.getValue(ComposterBlock.LEVEL)), 2);
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
